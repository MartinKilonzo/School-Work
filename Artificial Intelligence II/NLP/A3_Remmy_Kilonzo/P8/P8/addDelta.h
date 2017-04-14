#ifndef ADD_DELTA_H
#define ADD_DELTA_H


#include <math.h>
#include <unordered_map>
using namespace std;

#include "../../Ngrams/VectorHash.h"

typedef vector<string> Tokens;
typedef unordered_map<Tokens, int> Corpus;


double getProbAD(vector<Corpus> corpus, Tokens sentence, unsigned int n, double delta, double N, double V) {
  int nGramSize = 1;
  vector<double> probs;
  for (auto itr = sentence.begin(); itr != sentence.end() - (n - 1); ) {
    Tokens nGramA;
    Tokens nGramB;
    for (auto jtr = itr; jtr != itr + nGramSize; jtr++) {
      nGramA.push_back(*jtr);
    }

    if (nGramSize != 1)
      for (auto jtr = itr; jtr != itr + (nGramSize - 1); jtr++)
        nGramB.push_back(*jtr);

    double prob;
    double aB;
    double bB;
    if (nGramSize == 1) {
      // P(a) = (C(a) + d) / (N + d * B)
      aB = pow(V, nGramA.size());
      prob = ((corpus[nGramSize - 1])[nGramA] + delta) / (N + delta * aB);
    }
    else {
      // P(a | B) = P(A) / P(B), P(A) = P(Ba)
      aB = pow(V, nGramA.size());
      bB = pow(V, nGramB.size());
      double probA = ((corpus[nGramSize - 1])[nGramA] + delta) / (N + delta * aB);
      double probB = ((corpus[nGramSize - 2])[nGramB] + delta) / (N + delta * bB);
      prob = probA / probB;
    }
    if (prob == 0)
      prob = -DBL_MAX;
    probs.push_back(log(prob));

    if (nGramSize < n)
      nGramSize++;
    else
      itr++;
  }
  double probability = 0;
  for (auto &prob : probs)
    probability += prob;
  return probability;
}


#endif
