#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <unordered_map>
#include <math.h>
using namespace std;

#include "../../Ngrams/fileRead.h"
#include "../../Ngrams/VectorHash.h"
#include "../../Ngrams/utilsToStudents.h"

#define LATINONLY true
#define VOCABULARY 256


vector<unordered_map<vector<char>, int>> calcDatabases(unsigned int n, vector<char> &tokens1) {
	vector<unordered_map<vector<char>, int>> databases;
	for (int i = 1; i <= n; i++) {
		unordered_map<vector<char>, int> database;
		for (int k = 0; k <= tokens1.size() - i; k++)
		{
			vector<char> nGram(i);

			for (unsigned int j = 0; j < i; j++)
				nGram[j] = tokens1[k + j];

			if (database.count(nGram) == 0)
				database[nGram] = 1;
			else
				database[nGram] = database[nGram] + 1;
		}
		databases.push_back(database);
	}
	return databases;
}

double calcProb(unsigned int n, double delta, vector<unordered_map<vector<char>, int>> &databases, vector<char> &tokens1, vector<char> &tokens2) {
	vector<double> probabilities;
	int k, V = VOCABULARY;
	double N, prob, prob1, prob2, ret;

	if (tokens1.size() < n || tokens2.size() < n)
		cout << "\nInput file is too small to create any nGrams of size " << n;
	else
	{
		N = tokens1.size();
		k = 1;

		for (int i = 0; i < tokens2.size() - (n - 1);) {
			vector<char> nGram1;
			vector<char> nGram2;
			for (int j = i; j < i + k; j++) {
				nGram1.push_back(tokens2[j]);
				if ((j < i + k - 1) && k != 1)
					nGram2.push_back(tokens2[j]);
			}
			if (k == 1) {
				prob = ((databases[k - 1])[nGram1] + delta) / (N + delta * pow(V, nGram1.size()));
			}
			else {
				prob1 = ((databases[k - 1])[nGram1] + delta) / (N + delta * pow(V, nGram1.size()));
				prob2 = ((databases[k - 2])[nGram2] + delta) / (N + delta * pow(V, nGram2.size()));
				prob = prob1 / prob2;
			}
			// P(a | B) = P(A)/P(B)
			probabilities.push_back(log(prob));

			// Increases n in Ngrams (as used in the Chain Rule
			// Starts with 1, and goes up till n
			if (k < n)
				k++;
			else
				i++;
		}

		ret = 0;
		for (int i = 0; i < probabilities.size(); i++)
			ret += probabilities[i];
		if (isinf(ret))
			ret = -DBL_MAX;
		//cout << ret << '\n';
		return ret;
	}
	return 0;
}

int main(int argc, char const *argv[]) {
	unsigned int n = atoi(argv[1]);
	double delta = stod(argv[2]);
	int senLength = atoi(argv[3]);
	vector<char> tokens1a, tokens2a, tokens3a, tokens4a, tokens5a, tokens6a;
	vector<char> tokens1b, tokens2b, tokens3b, tokens4b, tokens5b, tokens6b;
	vector<vector<char>> listFirsts;		// A list of all the first texts (danish 1, english 1, etc)
	vector<vector<char>> listSeconds;		// A list of all the second sentences (danish2, english2, etc)
	vector<vector<unordered_map<vector<char>, int>>> setsOfDatabases;
	vector<vector<int>> confusionMatrix;

	read_tokens("danish1.txt", tokens1a, LATINONLY);
	setsOfDatabases.push_back(calcDatabases(n,tokens1a));
	listFirsts.push_back(tokens1a);
	read_tokens("english1.txt", tokens2a, LATINONLY);
	setsOfDatabases.push_back(calcDatabases(n, tokens2a));
	listFirsts.push_back(tokens2a);
	read_tokens("french1.txt", tokens3a, LATINONLY);
	setsOfDatabases.push_back(calcDatabases(n, tokens3a));
	listFirsts.push_back(tokens3a);
	read_tokens("italian1.txt", tokens4a, LATINONLY);
	setsOfDatabases.push_back(calcDatabases(n, tokens4a));
	listFirsts.push_back(tokens4a);
	read_tokens("latin1.txt", tokens5a, LATINONLY);
	setsOfDatabases.push_back(calcDatabases(n, tokens5a));
	listFirsts.push_back(tokens5a);
	read_tokens("sweedish1.txt", tokens6a, LATINONLY);
	setsOfDatabases.push_back(calcDatabases(n, tokens6a));
	listFirsts.push_back(tokens6a);

	read_tokens("danish2.txt", tokens1b, LATINONLY);
	listSeconds.push_back(tokens1b);
	read_tokens("english2.txt", tokens2b, LATINONLY);
	listSeconds.push_back(tokens2b);
	read_tokens("french2.txt", tokens3b, LATINONLY);
	listSeconds.push_back(tokens3b);
	read_tokens("italian2.txt", tokens4b, LATINONLY);
	listSeconds.push_back(tokens4b);
	read_tokens("latin2.txt", tokens5b, LATINONLY);
	listSeconds.push_back(tokens5b);
	read_tokens("sweedish2.txt", tokens6b, LATINONLY);
	listSeconds.push_back(tokens6b);

	for (int h = 0; h < listSeconds.size(); h++) {
		// The following block splits tokens2 into subsentences
		vector<vector<char>> subSentences;
		// For every character in the current sentence
		for (int i = 0; i < (listSeconds[h]).size() - senLength;) {
			vector<char> tempTokens;
			for (int j = 0; j < senLength; j++, i++) {
				tempTokens.push_back((listSeconds[h])[i]);
			}
			subSentences.push_back(tempTokens);
		}

		//Creates row for Confusion matrix and initializes it to 6 0s
		vector<int> confusionRow;
		for (int i = 0; i < listFirsts.size(); i++) {
			confusionRow.push_back(0);
		}

		// This next block populates the row of the confusion matrix
		for (int i = 0; i < subSentences.size(); i++) {
			//Variable to hold the probabilities of all the combinations of dictionaries / this subsentence
			vector<int> probabilities;
			for (int j = 0; j < setsOfDatabases.size(); j++) {
				probabilities.push_back(calcProb(n, delta, setsOfDatabases[j], listFirsts[j], subSentences[i]));
			}

			// Calculates the index of the maximum value in probabilities
			int maxIndex;
			double maxValue = -DBL_MAX;
			for (int j = 0; j < probabilities.size(); j++) {
				if (probabilities[j] > maxValue) {
					maxIndex = j;
					maxValue = probabilities[j];
				}
			}

			confusionRow[maxIndex] += 1;
		}
		confusionMatrix.push_back(confusionRow);
	}
	double numerator = 0;
	double denominator = 0;
	for (int i = 0; i < 6; i++) {
		numerator += confusionMatrix[i][i];
		for (int j = 0; j < 6; j++) {
			denominator += confusionMatrix[i][j];
		}
	}
	cout << (1- numerator / denominator) * 100 << "\n";
	for (int i = 0; i < 6; i++) {
		for (int j = 0; j < 6; j++) {
			cout << confusionMatrix[i][j] << " ";
		}
		cout << "\n";
	}
}
