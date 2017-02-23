%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% INPUT 
% 
% Y: is a column vector of labels assigned to each competion image
% fName: name of CVS file to save the labels in
%         This file should be submitted to competition hosted on kaggle
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function fromMatrixToCSV(Y,fName)

[row,col] = size(Y);
numSamples = 1600;

if ( row ~= numSamples || col ~= 1 )
    error('Y should be a column vector with appropriate number of columns');
    return;
end;


y = 1:row;
ID = y';
Prediction = Y;


T = table(ID,Prediction);
writetable(T,fName);
