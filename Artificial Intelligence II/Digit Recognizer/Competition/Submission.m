load('competitionDataX.mat')

C = p12(X);
fromMatrixToCSV(C, 'Submission.csv');
