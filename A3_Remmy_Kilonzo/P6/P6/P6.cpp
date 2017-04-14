#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <unordered_map>
#include <math.h>
using namespace std;

#include "../../Ngrams/fileRead.h"
#include "../../Ngrams/VectorHash.h"
#include "../../Ngrams/utilsToStudents.h"

#define VOCABULARY_SIZE 26
#define LATIN_ONLY true
typedef vector<char> Tokens;
typedef unordered_map<Tokens, int> Corpus;




Corpus hashCorpus(Tokens tokens, int n) {
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



vector<Corpus> getCorpusList(Tokens tokens, int n) {
  vector<Corpus> ret;
  for (int N = 1; N <= n; N++) {
    Corpus corpus = hashCorpus(tokens, N);
    ret.push_back(corpus);
  }

  return ret;
}



vector<pair<vector<Corpus>, Tokens> > loadLanguages(vector<string> languages, int iType, int n) {
  vector<pair<vector<Corpus>, Tokens> > ret;
  string type = to_string(iType);
  for (auto &itr : languages) {
    string fileName = itr + type + ".txt";
    Tokens tokens;
    read_tokens(fileName, tokens, LATIN_ONLY);
    if (tokens.size() < n) {
      std::cerr << "\nInput file '" << fileName << "' is too small to create any nGrams of size " << n << '\n';
      exit(-1);
    }
    vector<Corpus> corpus = getCorpusList(tokens, n);
    ret.push_back(make_pair(corpus, tokens));
  }

  return ret;
}



int getMaxIndex(vector<double> values) {
  int maxIndex = 0;
  for (int i = 0; i < values.size(); i++)
    if (values[i] > values[maxIndex])
      maxIndex = i;

  return maxIndex;
}

void printConfMatrix (vector<vector<int>> confusionMatrix) {
  double correct = 0;
  double total = 0;

  for (int i = 0; i < confusionMatrix.size(); i++) {
    correct += confusionMatrix[i][i];
    for (int j = 0; j < confusionMatrix[i].size(); j++)
      total += confusionMatrix[i][j];
  }
  cout << (1 - correct / total) * 100 << "\n";

  for (auto &itr : confusionMatrix) {
    for (auto &jtr : itr)
      cout << jtr << " ";
    cout << "\n";
  }
}



double calcProb(vector<Corpus> languageCorpus,Tokens sentence, unsigned int n, double delta, double N) {
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
      aB = pow(VOCABULARY_SIZE, nGramA.size());
      prob = ((languageCorpus[nGramSize - 1])[nGramA] + delta) / (N + delta * aB);
    }
    else {
      // P(a | B) = P(A) / P(B), P(A) = P(Ba)
      aB = pow(VOCABULARY_SIZE, nGramA.size());
      bB = pow(VOCABULARY_SIZE, nGramB.size());
      double probA = ((languageCorpus[nGramSize - 1])[nGramA] + delta) / (N + delta * aB);
      double probB = ((languageCorpus[nGramSize - 2])[nGramB] + delta) / (N + delta * bB);
      prob = probA / probB;
    }
    probs.push_back(log(prob));

    if (nGramSize < n)
      nGramSize++;
    else
      itr++;
  }

  double ret = 0;
  for (int i = 0; i < probs.size(); i++)
    ret += probs[i];
  if (isinf(ret))
    ret = -DBL_MAX;
  //cout << ret << '\n';
  return ret;
}

int main(int argc, char const *argv[]) {
  vector<string> languages {"danish", "english", "french", "italian", "latin", "sweedish"};
  unsigned int n = atoi(argv[1]);
  double delta = stod(argv[2]);
  int senLength = atoi(argv[3]);


  vector<pair<vector<Corpus>, Tokens> > languageDB = loadLanguages(languages, 1, n);
  vector<pair<vector<Corpus>, Tokens> > testDB = loadLanguages(languages, 2, n);
  vector<vector<int>> confusionMatrix;


  for (auto &tlanguage : testDB) {
    // Build the sentences
    vector<Tokens> sentences;
    for (auto itr = tlanguage.second.begin(); itr != tlanguage.second.end(); ) {
      Tokens sentence;
      for (int j = 0; j < senLength && itr != tlanguage.second.end(); j++, itr++)
        sentence.push_back(*itr);
      if (sentence.size() == senLength)
        sentences.push_back(sentence);
    }

    // Initialize the row of the confusion matrix
    vector<int> confusionRow;
    for (auto &itr : languageDB)
      confusionRow.push_back(0);

    // Populate row
    for (auto &sentence : sentences) {
      vector<double> probs;
      for (auto &language : languageDB)
        probs.push_back(calcProb(language.first, sentence, n, delta, language.second.size()));

      confusionRow[getMaxIndex(probs)] += 1;
    }
    confusionMatrix.push_back(confusionRow);
  }

  printConfMatrix(confusionMatrix);

  return 0;
}
