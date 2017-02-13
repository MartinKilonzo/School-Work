function [net, err] = p10a(X_train, Y_train, H, regularizerWeight)

net = patternnet(H);

net.divideParam.testRatio = 0;
net.divideParam.valRatio = 0.3;
net.divideParam.trainRatio = 0.7;
net.trainParam.showWindow = 0;

net.performParam.regularization = regularizerWeight;

X = transpose(X_train);

Y = zeros(max(Y_train), size(Y_train, 1));
Y(sub2ind(size(Y), Y_train, transpose(1:size(Y, 2)))) = 1;

[net, tr] = train(net, X, Y);

[mx, imx] = max(net(X(:, tr.valInd)));
[my, imy] = max(Y(:, tr.valInd));

mistakes = transpose(imx) ~= transpose(imy);

err = sum(mistakes, 1)/size(mistakes, 1);

