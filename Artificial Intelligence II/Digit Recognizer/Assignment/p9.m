function W = p9(X_train, Y_train, iterNum, wInit, alpha)
W = wInit;
X_train = [ones(size(X_train, 1), 1) X_train];
for iter = 1:iterNum
    iter
    for iSample = 1:size(X_train, 1)
        X = X_train(iSample, :);
        Y = Y_train(iSample, :);
        Wx = sum(W .* X, 2);
        
        Yi = zeros(max(Y_train), 1);
        Yi(Y) = 1;
        sm = softmax(Wx);

        W = W + alpha * (Yi - sm) * X;
        
        loss = -log(sm(Y));
    end
end
