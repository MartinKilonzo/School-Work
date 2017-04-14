#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <math.h>
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

double getProb(vector<string> nGram, unordered_map<vector<string>, int> corpus, double delta, double N, double V) {
  double prob = delta;

  if (corpus.count(nGram) != 0)
    prob += corpus[nGram];

  prob /= (N + delta * pow(V, nGram.size()));

  return prob;
}



int main(int argc, char const *argv[]) {
  // If there are not enough args, return -1
  if (argc < 5) {
    std::cerr << "Usage: P4 <corpus> <sentence> <n> <delta>" << '\n';
    return -1;
  }

  // Otherwise, collect the function parameters
  string corpusFileName = argv[1];
  string sentenceFileName = argv[2];
  unsigned int n = stoi(argv[3]);
  double delta = stod(argv[4]);



  // Capture all tokens
  vector<string> corpusTokens;
  vector<string> sentenceTokens;
  read_tokens(corpusFileName, corpusTokens, false);
  read_tokens(sentenceFileName, sentenceTokens, false);


  if (corpusTokens.size() < n) {
    std::cerr << "\nInput file '" << corpusFileName << "' is too small to create any nGrams of size " << n;
    return -1;
  }

  if (sentenceTokens.size() < n) {
    std::cerr << "\nInput file '" << sentenceFileName << "' is too small to create any nGrams of size " << n;
    return -1;
  }


  unordered_map <string, int> dictionary;
  unordered_map <vector<string>, int> corpus;

  for (auto &word : corpusTokens) {
    if (dictionary.count(word) == 0)
      dictionary[word] = 1;
  }

  vector<double> probs;

  int V = dictionary.size() + 1;
  double N = corpusTokens.size();

  int m = 1;
  bool reCorpus = true;
  unordered_map<vector<string>, int> corpusA;
  unordered_map<vector<string>, int> corpusB;

  for (auto sItr = sentenceTokens.begin(); sItr != sentenceTokens.end() - (n - 1); ) {

    if (m == 1) {
      if (reCorpus) {
        corpusA = hashCorpus(corpusTokens, m);
      }

      // Create nGram
      vector<string> nGram;
      nGram.push_back(*sItr);

      // Get probs
      double prob = getProb(nGram, corpusA, delta, N, V);

      probs.push_back(prob);
    }
    else {
      if (reCorpus) {
        corpusA = hashCorpus(corpusTokens, m);
        corpusB = hashCorpus(corpusTokens, m - 1);
      }

      // Create nGrams
      vector<string> nGramA;
      for (auto jtr = sItr; jtr != sItr + m; jtr++)
        nGramA.push_back(*jtr);

      vector<string> nGramB;
      for (auto jtr = sItr; jtr != sItr + (m - 1); jtr++)
        nGramB.push_back(*jtr);

      // Get probs
      // P(a | B) = P(A) / P(B), P(A) = P(Ba)
      // P(A)
      double probA = getProb(nGramA, corpusA, delta, N, V);

      // P(B)
      double probB = getProb(nGramB, corpusB, delta, N, V);

      // P(a | B) = P(A) / P(B), P(A) = P(Ba)
      probs.push_back(probA / probB);
    }

    if (m < n)  {
      m++;
      reCorpus = true;
    }
    else {
      sItr++;
      reCorpus = false;
    }

  }

  double probability = 0;

  for (auto &prob : probs) {
    if (prob == 0 && delta == 0) {
      probability = -DBL_MAX;;
      break;
    }
    probability += log(prob);
  }

  std::cout << probability << '\n';

  return 0;
}
