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
  // if (argc < 5)
  //   return -1;
	//
  // // Otherwise, collect the function parameters
  // string fileName1 = argv[1];
  // string fileName2 = argv[2];
  // int n = atoi(argv[3]);
  // bool isCommonNGrams = (bool) atoi(argv[4]);

  string fileName1 = "../../../InputOutput/p2_a.txt";
  string fileName2 = "../../../InputOutput/p2_b.txt";
  unsigned int n = atoi("5");
  bool isCommonNGrams = (bool) atoi("0");

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

    unordered_map <vector<string>, int> database1;
    unordered_map <vector<string>, int> database2;


    for (auto itr = tokens1.begin(); itr != tokens1.end() - n; itr++) {
      vector<string> nGram(n);

      for(auto jtr = itr; jtr != itr + n + 1; jtr++) {
        nGram.push_back(*jtr);
      }

      if (database1.count(nGram) == 0 ) {
        database1[nGram] = 1;
      } else{
        database1[nGram] = database1[nGram] + 1;
      }
    }

    for (auto itr = tokens2.begin(); itr != tokens2.end() - n; itr++) {
      vector<string> nGram(n);

      for(auto jtr = itr; jtr != itr + n + 1; jtr++) {
        nGram.push_back(*jtr);
      }

			// Record only the relevant nGrams
      if ((database1.count(nGram) == 0) == isCommonNGrams) {
        if (database2.count(nGram) == 0) {
          database2[nGram] = 1;
        } else {
          database2[nGram] = database2[nGram] + 1;
        }
      }
    }

		for (auto &itdb : database2) {
			for (auto &itng : itdb.first)
				std::cout << itng;
			std::cout << ", " << itdb.second <<  '\n';
		}
		std::cout << database1.size() << " " << database2.size() << " "<< '\n';
  }

  return 0;
}
