// Take from command line: input filename, k-most-frequent words

#include <stdlib.h>
#include <iostream>
#include <algorithm>
#include <unordered_map>
using namespace std;

#include "../../NgramsToStudents/Ngrams/fileRead.h"
#include "../../NgramsToStudents/Ngrams/VectorHash.h"



int main(int argc, char const *argv[]) {
	// If there are not enough args, return -1
	if (argc < 5)
		return -1;

	// Otherwise, collect the function parameters
	string fileName1 = argv[1];
	string fileName2 = argv[2];
	int n = atoi(argv[3]);
	bool isCommonNGrams = (bool) atoi(argv[4]);

	// Capture all tokens
	vector<string> tokens1;
	vector<string> tokens2;
	read_tokens(fileName1, tokens1, false);
	read_tokens(fileName2, tokens2, false);

	// Do some N-Gram

	// Count token frequency
	unordered_map<string, int> dictionary;
	for (auto &token : tokens) {
		if (dictionary.count(token)) {
			dictionary[token] = dictionary[token] + 1;
		}
		else {
			dictionary[token] = 1;
		}
	}

	// Pass token, frequency pairs into a vector for sorting
	vector<pair<string, int>> wordCounts;
	wordCounts.reserve(dictionary.size());
	for (auto &word : dictionary) {
		wordCounts.push_back(word);
	}

	// Sort the vector by frequency
	std::sort(wordCounts.begin(), wordCounts.end(),
		[](pair<string, int> const &t1, pair<string, int> const &t2) {
		return t1.second > t2.second; // or use a custom compare function
	}
	);

	// Print the k-most frequent words
	int n = 0;
	float sum = 0;
	for (auto word = wordCounts.begin(); word != wordCounts.end() && n < k; word++, n++) {
		std::cout << word->first << ", " << word->second << '\n';
		sum += word->second;
	}

	// Print the k-most frequent words as a ratio of the total number of words
	std::cout << sum / tokens.size() * 100 << " %" << '\n';
	// std::cout << n << " words are necessary for understanding " << k * 100 << "% of the text, representing " << float(n) / dictionary.size() * 100 << "% of the language." << '\n';

	return 0;
}
