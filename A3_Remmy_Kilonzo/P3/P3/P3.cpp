// Take from command line: input filename, k-most-frequent words

#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <math.h>
#include <algorithm>
#include <unordered_map>
using namespace std;

#include "../../Ngrams/fileRead.h"
#include "../../Ngrams/VectorHash.h"
#include "../../Ngrams/utilsToStudents.h"



int countNGrams(unordered_map<vector<string>, int> database) {
  int count = 0;
  for (auto &itr : database)
    count += itr.second;
  return count;
}



int main(int argc, char const *argv[]) {
  // If there are not enough args, return -1
  if (argc < 3)
    return -1;

  // Otherwise, collect the function parameters
  string fileName = argv[1];
  unsigned int n = atoi(argv[2]);
  srand(time(NULL));


  // Capture all tokens
  vector<string> tokens;
  read_tokens(fileName, tokens, true);

  if (tokens.size() < n) {
    cout << "\nInput file '" << fileName << "' is too small to create any nGrams of size " << n;
    return -1;
  }


  unordered_map <string, int> database;

  for (auto &itr : tokens) {
    if (database.count(itr) == 0) {
      database[itr] = 1;
    } else{
      database[itr] = database[itr] + 1;
    }
  }

  vector<string> phrase;

  if (n == 1) {
    vector<string> words(database.size());
    vector<double> probs(database.size());

    for (auto &itr : database) {
      words.push_back(itr.first);
      probs.push_back(float(itr.second) / tokens.size());
    }

    while (phrase.size() == 0 || phrase.back().compare(EOS) != 0)
      phrase.push_back(words[drawIndex(probs)]);
  }

  else if (n > 1) {
    phrase.push_back(EOS);
    int in = 0;
    unordered_map <vector<string>, int> aDatabase;
    unordered_map <vector<string>, int> bDatabase;
    do {
      int N = phrase.size();
      if (N < n) {
        aDatabase.clear();
        bDatabase.clear();
        for (auto itr = tokens.begin(); itr != tokens.end() - (N + 1) + 1; itr++) {
          vector<string> nGram;
          std::cout << nGram.size() << ' ' << n << '\n';
          for (auto jtr = itr; jtr != itr + (N + 1); jtr++)
            nGram.push_back(*jtr);

          if (aDatabase.count(nGram) == 0) {
            aDatabase[nGram] = 1;
          } else{
            aDatabase[nGram] = aDatabase[nGram] + 1;
          }
        }

        for (auto itr = tokens.begin(); itr != tokens.end() - N + 1; itr++) {
          vector<string> nGram;
          std::cout << nGram.size() << ' ' << n << '\n';
          for (auto jtr = itr; jtr != itr + N; jtr++)
            nGram.push_back(*jtr);

          if (bDatabase.count(nGram) == 0) {
            bDatabase[nGram] = 1;
          } else{
            bDatabase[nGram] = bDatabase[nGram] + 1;
          }
        }
      }


      vector<string> words(database.size());
      vector<double> probs(database.size());


      vector<string> nGram;
      for (int i = in; i < phrase.size(); i++)
        nGram.push_back(phrase[i]);


      float aCount = 1 / (pow(database.size(), (phrase.size() - in)) + aDatabase.size());
      float bCount = 1 / (pow(database.size(), (phrase.size() - in) - 1) + bDatabase.size());

      if (bDatabase.count(nGram) != 0)
        bCount += bDatabase[nGram];

      for (auto &itr : database) {
        nGram.push_back(itr.first);

        if (aDatabase.count(nGram) != 0)
          aCount += aDatabase[nGram];

        nGram.pop_back();
        words.push_back(itr.first);
        probs.push_back(aCount / bCount);
      }

      // Normalize
      float sum = 0;
      for (auto &itr : probs)
        sum += itr;

      for (int i = 0; i < probs.size(); i++)
        probs[i] = probs[i] / sum;

      string t = words[drawIndex(probs)];
      phrase.push_back(t);

      if (phrase.size() > n)
        in++;

    } while(phrase.back().compare(EOS) != 0);
  }

  for (auto &itr : phrase)
    std::cout << itr << ' ';
  std::cout << '\n';

  return 0;
}
