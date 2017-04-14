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



unordered_map<vector<string>, int> hashCorpus (vector<string> tokens, int n) {
  unordered_map<vector<string>, int> corpus;
  for (auto itr = tokens.begin(); itr != tokens.end() - (n - 1); itr++) {
    vector<string> nGram;

    for (auto jtr = itr; jtr != itr + n; jtr++)
      nGram.push_back(*jtr);

    if (corpus.count(nGram) == 0) {
      corpus[nGram] = 1;
    } else{
      corpus[nGram] = corpus[nGram] + 1;
    }
  }

  return corpus;
}



int main(int argc, char const *argv[]) {
  // If there are not enough args, return -1
  if (argc < 3) {
    std::cerr << "Usage: P3.exe <corpus> <n>" << '\n';
    return -1;
  }

  // Otherwise, collect the function parameters
  string fileName = argv[1];
  unsigned int n = atoi(argv[2]);
  srand(time(NULL));



  // Capture all tokens
  vector<string> tokens;
  read_tokens(fileName, tokens, true);

  if (tokens.size() < n) {
    cerr << "\nInput file '" << fileName << "' is too small to create any nGrams of size " << n;
    return -1;
  }

  unordered_map <string, int> dictionary;

  for (auto &word : tokens) {
    if (dictionary.count(word) == 0)
      dictionary[word] = 1;
    else
      dictionary[word] += 1;
  }

  vector<string> phrase = {""};

  int m = 1;
  bool reCorpus = true;
  double N = tokens.size();
  unordered_map<vector<string>, int> corpusA;
  unordered_map<vector<string>, int> corpusB;
  vector<double> probs;
  vector<string> vocabulary;

  while (phrase.back() != EOS) {
    if (n == 1) {
      if (reCorpus) {
        probs.clear();
        vocabulary.clear();
        for (auto &itr : dictionary) {
          probs.push_back(itr.second / N);
          vocabulary.push_back(itr.first);
        }
      }
      phrase.push_back(vocabulary[drawIndex(probs)]);
    }
    else if (n > 1) {
      if (reCorpus) {
        corpusA = hashCorpus(tokens, max(m, 2));
        corpusB = hashCorpus(tokens, max(m - 1, 1));
      }

      probs.clear();
      vocabulary.clear();

      vector<string> nGram;
      if (m == 1)
        nGram.push_back(EOS);
      else {
        for (auto jtr = phrase.end() - (m - 1); jtr != phrase.end(); jtr++)
          nGram.push_back(*jtr);
      }

      double probsB = corpusB[nGram];

      for (auto &itr : dictionary) {
        nGram.push_back(itr.first);
        vocabulary.push_back(itr.first);
        probs.push_back(corpusA[nGram] / probsB);
        nGram.pop_back();
      }
      phrase.push_back(vocabulary[drawIndex(probs)]);
    }

    if (m < n) {
      m++;
      reCorpus = true;
    } else {
      reCorpus = false;
    }
  }

  for (auto &itr : phrase)
    std::cout << itr << ' ';
  std::cout << '\n';

  return 0;
}
