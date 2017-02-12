function C = p7(W, X)
C = [];
for row = 1:size(X, 1)
    x = X(row, :);
    prediction = W(:, 1) + sum(W(:, 2:end) .* x, 2);
    class = find(prediction == max(prediction));
    C = [C; class];
end