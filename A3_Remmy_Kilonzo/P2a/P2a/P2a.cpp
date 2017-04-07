// Take from command line: input filename, k-most-frequent words

#include <stdlib.h>
#include <iostream>
#include <algorithm>
#include <unordered_map>
using namespace std;

#include "../../Ngrams/fileRead.h"
#include "../../Ngrams/VectorHash.h"



int main(int argc, char const *argv[]) {
  // If there are not enough args, return -1
  if (argc < 5)
    return -1;

  // Otherwise, collect the function parameters
  string fileName1 = argv[1];
  string fileName2 = argv[2];
  int n = atoi(argv[3]);
  bool isCommonNGrams = (bool) atoi(argv[4]);


  // Capture all tokens
  vector<string> tokens1;
  vector<string> tokens2;
  read_tokens(fileName1, tokens1, false);
  read_tokens(fileName2, tokens2, false);

  if (tokens1.size() < n) {
    cout << "\nInput file '" << fileName1 << "' is too small to create any nGrams of size " << n;
    return -1;
  } else if (tokens2.size() < n) {
    cout << "\nInput file '" << fileName2 << "' is too small to create any nGrams of size " << n;
    return -1;
  } else {

    bool printed = false;
    for (int N = n; N > 0; N--) {
      unordered_map <vector<string>, int> database1;
      unordered_map <vector<string>, int> database2;


      for (auto itr = tokens1.begin(); itr != tokens1.end() - N; itr++) {
        vector<string> nGram(n);

        for(auto jtr = itr; jtr != itr + N; jtr++) {
          nGram.push_back(*jtr);
        }

        if (database1.count(nGram) == 0) {
          database1[nGram] = 1;
        } else{
          database1[nGram] = database1[nGram] + 1;
        }
      }

      int numGrams2 = 0;
      for (auto itr = tokens2.begin(); itr != tokens2.end() - N; itr++, numGrams2++) {
        vector<string> nGram(n);

        for(auto jtr = itr; jtr != itr + N; jtr++) {
          nGram.push_back(*jtr);
        }

        // Record only the N_Grams present in the first text file
        if ((database1.count(nGram) != 0)) {
          if (database2.count(nGram) == 0) {
            database2[nGram] = 1;
          } else {
            database2[nGram] = database2[nGram] + 1;
          }
        }
      }


      if (isCommonNGrams) {
        if (!printed) {
          for (auto &itdb : database2) {
            printed = true;
            for (auto &itng : itdb.first)
              std::cout << itng << " ";
            std::cout << '\n';
          }
        }
        std::cout << "For N-Grams of size: " << N << " --> " << (1 - database2.size() / float(numGrams2)) * 100 << '\n';
      } else {
        std::cout << "For N-Grams of size: " << N << " --> " << (database2.size() / float(numGrams2)) * 100 << '\n';
      }
    }
  }

  return 0;
}
