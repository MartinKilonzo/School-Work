function C = p12(X_test)
load('25010net.mat');
[m, predictions] = max(net(transpose(X_test)));
C = transpose(predictions);
