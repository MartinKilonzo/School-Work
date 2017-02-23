function W = p8(X_train, Y_train, iterNum, wInit, alpha)
W = wInit;
X_train = [ones(size(X_train, 1), 1) X_train];
for iter = 1:iterNum
    for iSample = 1:size(X_train, 1)
        X = X_train(iSample, :);
        Y = Y_train(iSample, :);
        Wx = sum(W .* X, 2);

        iMax = find(Wx == max(Wx));
        if iMax == Y
            L = 0;
        else
            L = zeros(size(W, 1), size(W, 2));
            L(Y, :) = X;
            L(iMax, :) = -1 * X;
        end
        
        W = W + alpha * L;
        
        loss = Wx(iMax) - Wx(Y);
    end
end
