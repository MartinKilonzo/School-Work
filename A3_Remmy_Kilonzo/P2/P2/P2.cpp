// Take from command line: input filename, k-most-frequent words

#include <stdlib.h>
#include <iostream>
#include <unordered_map>
using namespace std;

#include "../../Ngrams/fileRead.h"
#include "../../Ngrams/VectorHash.h"



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
  if (argc < 5)
    return -1;

  // Otherwise, collect the function parameters
  string fileNameA = argv[1];
  string fileNameB = argv[2];
  int n = stoi(argv[3]);
  bool isCommonNGrams = (bool) atoi(argv[4]);


  // Capture all tokens
  vector<string> tokensA;
  vector<string> tokensB;
  read_tokens(fileNameA, tokensA, false);
  read_tokens(fileNameB, tokensB, false);

  if (tokensA.size() < n) {
    cerr << "\nInput file '" << fileNameA << "' is too small to create any nGrams of size " << n;
    return -1;
  } else if (tokensB.size() < n) {
    cerr << "\nInput file '" << fileNameB << "' is too small to create any nGrams of size " << n;
    return -1;
  } else if (n > 0) {
    unordered_map <vector<string>, int> corpusA = hashCorpus(tokensA, n);
    unordered_map <vector<string>, int> corpusB = hashCorpus(tokensB, n);

    vector <vector<string>> sameWords;

    for (auto &itr : corpusB) {
      if (corpusA.count(itr.first) > 0)
        sameWords.push_back(itr.first);
    }

		if (isCommonNGrams) {
			for (auto &itr : sameWords) {
				for (auto &jtr : itr)
					std::cout << jtr << " ";
				std::cout << '\n';
			}
			std::cout << (1 - sameWords.size() / float(corpusB.size())) * 100 << '\n';
		} else {
			std::cout << (sameWords.size() / float(corpusB.size())) * 100 << '\n';
		}
  }

  return 0;
}
