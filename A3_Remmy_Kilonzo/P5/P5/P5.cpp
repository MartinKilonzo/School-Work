#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <math.h>
#include <unordered_map>
using namespace std;

#include "../../Ngrams/fileRead.h"
#include "../../Ngrams/VectorHash.h"
#include "../../Ngrams/utilsToStudents.h"

typedef vector<string> Tokens;
typedef unordered_map<Tokens, int> Corpus;

Corpus hashCorpus (Tokens tokens, int n) {
  Corpus corpus;
  for (auto itr = tokens.begin(); itr != tokens.end() - (n - 1); itr++) {
    Tokens nGram;

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

vector<double> generateFreqCounts (Corpus corpus, int size, double B) {
  vector<double> frequencies(size);

  for (auto &itr : frequencies)
    itr = 0;

  for (auto &itr : corpus)
    frequencies[itr.second]++;

  frequencies[0] = B - corpus.size();

  // Smooth out frequencies
  // for (int i = frequencies.size() - 2; i >= 0; i--)
  //   frequencies[i] += frequencies[i + 1];

  return frequencies;
}


double getProb(Tokens nGram, Corpus corpus, vector<double> frequencies, int threshold, int N) {
  double rate = 0;
  if (corpus.count(nGram) != 0)
    rate += corpus[nGram];

  double prob;
  if (rate < threshold) {
    // GT prob
    prob = (rate + 1) * frequencies[rate + 1] / (N * frequencies[rate]);
  }
  else {
    // Use MLE
    prob = rate / N;
  }
  return prob;
}


int main(int argc, char const *argv[]) {
  // If there are not enough args, return -1
  if (argc < 5)
    return -1;

  // Otherwise, collect the function parameters
  string corpusFileName = argv[1];
  string sentenceFileName = argv[2];
  unsigned int n = atoi(argv[3]);
  double threshold = atoi(argv[4]);


  // Capture all tokens
  Tokens corpusTokens;
  Tokens sentenceTokens;
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

  for (auto &word : corpusTokens) {
    if (dictionary.count(word) == 0)
      dictionary[word] = 1;
  }

  vector<double> probs;

  unsigned int m = 1;
  bool reCorpus = true;
  int N;
  double aB;
  double bB;
  double normFactA;
  double normFactB;
  Corpus corpusA;
  Corpus corpusB;
  vector<double> frequenciesA;
  vector<double> frequenciesB;

  for (auto sItr = sentenceTokens.begin(); sItr != sentenceTokens.end() - (n - 1); ) {

    // Use MLE for nGrams of size 1
    if (m == 1) {
      // If this is the first time running this for nGrams of size 1,
      if (reCorpus) {
        // Populate the GT count
        N = corpusTokens.size();
        aB = dictionary.size() + 1;
        // Hash Tokens
        corpusA = hashCorpus(corpusTokens, m);
        // Count nGram rates
        frequenciesA = generateFreqCounts(corpusA, N, aB);
        // Adjust threshold in case it is too high
        double t = 0;
        while (frequenciesA[t + 1] != 0)
          t++;
        if (t < threshold)
          threshold = t;
      }

      Tokens nGram;
      nGram.push_back(*sItr);

      double rate = 0;
      if (corpusA.count(nGram) != 0)
        rate += corpusA[nGram];

      double prob;
      if (rate < threshold)
        prob = (rate + 1) * frequenciesA[rate + 1] / (N * frequenciesA[rate]);
      else
        prob = rate / N;

      probs.push_back(prob);
    } else {
      if (reCorpus) {
        // Populate the GT count
        N = corpusTokens.size();
        aB = pow(dictionary.size() + 1, m);
        bB = pow(dictionary.size() + 1, m - 1);
        // Hash Tokens
        corpusA = hashCorpus(corpusTokens, m);
        corpusB = hashCorpus(corpusTokens, m - 1);
        // Count nGram rates
        frequenciesA = generateFreqCounts(corpusA, N, aB);
        frequenciesB = generateFreqCounts(corpusB, N, bB);
        // Adjust threshold in case it is too high
        double t = 0;
        while (frequenciesA[t] != 0 && frequenciesB[t] != 0)
          t++;
        if (t < threshold)
          threshold = t;

        // vector<pair<Tokens, double>> obsA;
        // vector<pair<Tokens, double>> obsB;

        // for (auto &itr : corpusA)
        //   obsA.push_back(make_pair(itr.first, getProb(itr.first, corpusA, frequenciesA, threshold, N)));
        // double sum = 0;
        // for (auto &itr : obsA) {
        //   std::cout << "P'( ";
        //   for (auto &jtr : itr.first)
        //     std::cout << jtr << ' ';
        //   std::cout << ") = " << itr.second << '\n';
        //   sum += itr.second;
        // }
        double sum = 0;
        int i = 0;
        for (auto &itr : corpusA)
          sum += getProb(itr.first, corpusA, frequenciesA, threshold, N);
        normFactA = (1 - frequenciesA[1] / N) / sum;
        sum = 0;
        for (auto &itr : corpusB)
          sum += getProb(itr.first, corpusB, frequenciesB, threshold, N);
        normFactB = (1 - frequenciesB[1] / N) / sum;
      }
      std::cout << "..." << '\n';
      // Get P(a|B) = P(A) / P(B), P(A) = P(Ba)
      // Get P(A) = P(Ba)
      Tokens nGramA;
      for (auto itr = sItr; itr != sItr + m; itr++)
        nGramA.push_back(*itr);

      double probA = getProb(nGramA, corpusA, frequenciesA, threshold, N);

      // Normalize probA
      probA *= normFactA;

      // Get P(B)
      Tokens nGramB;
      for (auto itr = sItr; itr != sItr + (m - 1); itr++)
        nGramB.push_back(*itr);

      double probB = getProb(nGramB, corpusB, frequenciesB, threshold, N);

      // Normalize probB
      probB *= normFactB;

      // P(a|B) = P(A) / P(B), P(A) = P(Ba)
      probs.push_back(probA / probB);
    }

    if (m < n) {
      m++;
      reCorpus = true;
    } else {
      reCorpus = false;
      sItr++;
    }
  }

  // Print the resultant probability
  double probability = 0;

  for (auto &prob : probs)
    probability += log(prob);

  std::cout << probability << '\n';

  return 0;
}
