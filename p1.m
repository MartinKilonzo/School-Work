function [X_out, Y_out] = p1(X, Y, label1, label2)
% This function gets all X observations with Y matching label 1 or label 2
% Then replaces the lessor Y with 1 and greater Y with 2

iRow = find(Y==label1 | Y==label2);
X_out = X(iRow, :);

cond = Y(iRow) == min(label1, label2);
Y_out = cond.*1 + ~cond.*2;