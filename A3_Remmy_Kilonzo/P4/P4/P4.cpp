// Take from command line: input filename, k-most-frequent words

#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <math.h>
#include <unordered_map>
using namespace std;

#include "../../Ngrams/fileRead.h"
#include "../../Ngrams/VectorHash.h"
#include "../../Ngrams/utilsToStudents.h"



int main(int argc, char const *argv[]) {
  // // If there are not enough args, return -1
  // if (argc < 5)
  //   return -1;
  //
  // // Otherwise, collect the function parameters
  // string corpusFileName = argv[1];
  // string sentenceFileName = argv[2];
  // unsigned int n = atoi(argv[3]);
  // double delta = atoi(argv[4]);
  string corpusFileName = "../../InputOutput/p4_textModel.txt";
  string sentenceFileName = "../../InputOutput/p4_sentence_b.txt";
  unsigned int n = atoi("2");
  double delta = atof("1");


  // Capture all tokens
  vector<string> corpusTokens;
  vector<string> sentenceTokens;
  read_tokens(corpusFileName, corpusTokens, false);
  read_tokens(sentenceFileName, sentenceTokens, false);


  if (corpusTokens.size() < n) {
    cout << "\nInput file '" << corpusFileName << "' is too small to create any nGrams of size " << n;
    return -1;
  }

  if (sentenceTokens.size() < n) {
    cout << "\nInput file '" << sentenceFileName << "' is too small to create any nGrams of size " << n;
    return -1;
  }


  unordered_map <string, int> dictionary;
  unordered_map <vector<string>, int> corpus;

  for (auto &word : corpusTokens) {
    if (dictionary.count(word) == 0)
      dictionary[word] = 1;
  }

  for (auto itr = corpusTokens.begin(); itr != corpusTokens.end() - (n - 1); itr++) {
    vector<string> nGram;

    for (auto jtr = itr; jtr != itr + n; jtr++)
      nGram.push_back(*jtr);

    if (corpus.count(nGram) == 0) {
      corpus[nGram] = 1;
    } else{
      corpus[nGram] = corpus[nGram] + 1;
    }
  }

  int V = dictionary.size();
  int N = corpusTokens.size();
  double B = pow(V, n);

  vector<double> probs;

  for (auto itr = sentenceTokens.begin(); itr != sentenceTokens.end() - (n - 1); itr++) {
    vector<string> nGram;

    for (auto jtr = itr; jtr != itr + n; jtr++)
      nGram.push_back(*jtr);

    double prob = delta;
    if (corpus.count(nGram) != 0)
      prob += corpus[nGram];

    prob /= (N + delta * B);

    for (auto &itr : nGram)
      std::cout << itr << ' ';
    std::cout << "(" << corpus[nGram] << " | " << prob << ")\n";

    probs.push_back(prob);
  }

  double probability = 0;

  for (auto &prob : probs)
    probability += log(prob);

  std::cout << probability << '\n';

  return 0;
}
