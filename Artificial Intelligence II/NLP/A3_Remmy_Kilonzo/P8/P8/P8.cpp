#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <math.h>
#include <unordered_map>
using namespace std;

#include "addDelta.h"
#include "utils.h"
#include "../../Ngrams/fileRead.h"
#include "../../Ngrams/VectorHash.h"
#include "../../Ngrams/utilsToStudents.h"

typedef vector<string> Tokens;
typedef unordered_map <Tokens, int> Corpus;



double getProbGT(vector<Corpus> corpus, Tokens nGram, unsigned int n, double delta, double N, double V, unsigned int threshold) {
  std::cerr << "Good Turing is not implemented" << '\n';
  exit(-1);
  return 0;
}



double getProb(vector<Corpus> corpus, Tokens nGram, unsigned int n, double delta, double N, double V, unsigned int threshold, bool model) {
  if (model == 0)
    return getProbGT(corpus, nGram, n, delta, N, V, threshold);

  else if (model == 1)
    return getProbAD(corpus, nGram, n, delta, N, V);

  else return 0;
}



int main(int argc, char const *argv[]) {
  // If there are not enough args, return -1
  if (argc < 5) {
    std::cerr << "Usage: P7 <corpus> <sentence> <dictionary> <n> <threshold> <delta> <model>" << '\n';
    return -1;
  }

  // Otherwise, collect the function parameters
  string corpusFileName = argv[1];
  string sentenceFileName = argv[2];
  string dictionaryFileName = argv[3];
  unsigned int n = stoi(argv[4]);
  unsigned int threshold = stoi(argv[5]);
  double delta = stod(argv[6]);
  bool model = stoi(argv[7]);



  // Capture all tokens
  Tokens corpusTokens;
  Tokens sentenceTokens;
  Tokens dictionaryTokens;
  read_tokens(corpusFileName, corpusTokens, false);
  read_tokens(sentenceFileName, sentenceTokens, true);
  read_tokens(dictionaryFileName, dictionaryTokens, false);


  if (corpusTokens.size() < n) {
    std::cerr << "\nInput file '" << corpusFileName << "' is too small to create any nGrams of size " << n;
    return -1;
  }

  if (sentenceTokens.size() < n) {
    std::cerr << "\nInput file '" << sentenceFileName << "' is too small to create any nGrams of size " << n;
    return -1;
  }


  unordered_map <string, int> vocabulary;
  unordered_map <string, int> dictionary;
  vector<Corpus> corpus = getCorpusList(corpusTokens, n);

  for (auto &word : corpusTokens) {
    if (vocabulary.count(word) == 0)
      vocabulary[word] = 1;
  }

  for (auto &word : dictionaryTokens) {
    if (dictionary.count(word) == 0)
      dictionary[word] = 1;
  }

  vector<double> probs;

  int V = vocabulary.size() + 1;
  double N = corpusTokens.size();

  // Collect sentences
  vector<Tokens> sentences;
  Tokens sentence;
  for (auto &word : sentenceTokens) {
    if (word == EOS) {
      sentences.push_back(sentence);
      sentence.clear();
    } else {
      sentence.push_back(word);
    }
  }

  // Proof sentences
  for (auto &sentence : sentences) {
    std::cout << "Sentence:\t";
    for (auto &word : sentence)
      std::cout << word << ' ';
    std::cout << '\n';
    // Check against all words within reasonable distance
    vector<Tokens> candidateWords;
    for (auto &word : sentence) {
      Tokens candidates;
      for (auto &candidate : dictionary)
        if (uiDamerauLevenshteinDistance(word, candidate.first) <= 1)
          candidates.push_back(candidate.first);

      candidateWords.push_back(candidates);
    }

    // Check that the produced sentences from the candidate words makes semantic sense
    vector<Tokens> candidateSentences;

    // for (auto &words : candidateWords) {
    //   for (auto &word : words) {
    //     Tokens temp = sentence;
    //     temp
    //     candidateSentences.push_back(temp)
    //   }
    // }

    for (int i = 0; i < candidateWords.size(); i++) {
      for (auto &word : candidateWords[i]) {
        Tokens temp = sentence;
        temp[i] = word;
        candidateSentences.push_back(temp);
      }
    }

    double bestProb = -DBL_MAX;
    Tokens bestSentence;

    for (auto &sentence : candidateSentences) {
      double prob = getProb(corpus, sentence, n, delta, N, V, threshold, model);
      if (prob > bestProb) {
        bestProb = prob;
        bestSentence = sentence;
      }
    }
    std::cout << "Suggestion:\t";
    for (auto &word : bestSentence)
      std::cout << word << " ";
    std::cout << "\n";
  }
  return 0;
}
