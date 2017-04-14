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
  unsigned int n = atoi("3");
  double threshold = atof("3");


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

  for (auto &word : corpusTokens) {
    if (dictionary.count(word) == 0)
      dictionary[word] = 1;
  }

  unordered_map <vector<string>, int> corpus;

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

  int N = corpusTokens.size();

  vector<double> frequencies(corpusTokens.size());

  for (auto &itr : frequencies)
    itr = 0;

  for (auto &itr : corpus)
    frequencies[itr.second]++;

  // Smooth out frequencies
  for (int i = frequencies.size() - 2; i >= 0; i--)
    frequencies[i] += frequencies[i + 1];

  while (frequencies[threshold] == 0)
    threshold--;

  vector<double> probs;

  for (auto itr = sentenceTokens.begin(); itr != sentenceTokens.end() - (n - 1); itr++) {
    vector<string> nGram;

    for (auto jtr = itr; jtr != itr + n; jtr++)
      nGram.push_back(*jtr);

    double rate = 0;
    if (corpus.count(nGram) != 0)
      rate += corpus[nGram];

    double prob;
    if (rate < threshold)
      prob = (rate + 1) * frequencies[rate + 1] / (N * frequencies[rate]);
    else
      prob = rate / N;

    for (auto &itr : nGram)
      std::cout << itr << ' ';
    std::cout << "(" << rate << " | " << prob << ")\n";

    if (prob <= 0) {
      std::cout << "Invalid threshold selected" << '\n';
      return -1;
    }

    probs.push_back(prob);
  }

  double probability = 0;

  for (auto &prob : probs)
    probability += log(prob);

  std::cout << probability << '\n';

  return 0;
}
