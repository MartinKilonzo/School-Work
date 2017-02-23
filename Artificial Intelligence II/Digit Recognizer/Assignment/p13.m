function C = p13(X_test)
load('net.mat');
[m, predictions] = max(net(transpose(X_test)));
C = transpose(predictions);