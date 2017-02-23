function [w, err] = p6(X_train, Y_train, iterNum, wInt, alpha)

w = wInt;
Y_train(Y_train == 2) = -1;
A = [ones(size(X_train, 1), 1) X_train];

for iter = 1:iterNum
    prediction = Y_train .* (A * w);
    mistakes = sign(prediction) == -1;
    loss = sum(mistakes .* prediction * -1);
    w = w + alpha * sum(transpose((Y_train .* mistakes) .* A), 2);
    err = sum(prediction < 0)/size(Y_train, 1);
end