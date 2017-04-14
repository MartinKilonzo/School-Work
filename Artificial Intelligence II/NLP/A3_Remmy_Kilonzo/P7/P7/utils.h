#ifndef UTILS_H
#define UTILS_H


#include <unordered_map>
using namespace std;

#include "../../Ngrams/VectorHash.h"


typedef vector<string> Tokens;
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

size_t uiDamerauLevenshteinDistance(const std::string &s1, const std::string &s2)
{
  const size_t m(s1.size());
  const size_t n(s2.size());

  if( m==0 ) return n;
  if( n==0 ) return m;

  size_t *costs = new size_t[n + 1];

  for( size_t k=0; k<=n; k++ ) costs[k] = k;

  size_t i = 0;
  for ( std::string::const_iterator it1 = s1.begin(); it1 != s1.end(); ++it1, ++i )
  {
    costs[0] = i+1;
    size_t corner = i;

    size_t j = 0;
    for ( std::string::const_iterator it2 = s2.begin(); it2 != s2.end(); ++it2, ++j )
    {
      size_t upper = costs[j+1];
      if( *it1 == *it2 )
      {
		  costs[j+1] = corner;
	  }
      else
	  {
		size_t t(upper<corner?upper:corner);
        costs[j+1] = (costs[j]<t?costs[j]:t)+1;
	  }

    if (i > 1 && j > 1 && *it1 == *it2-1 && *it1-1 == *it2)
      costs[i, j] = min(costs[i, j], costs[i-2, j-2] + costs[j]);

      corner = upper;
    }
  }

  size_t result = costs[n];
  delete [] costs;

  return result;
}


#endif
