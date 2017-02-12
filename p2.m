function [err, CONF] = p2(C, T)
m = max(T);

err = sum(C ~= T)/max(size(C, 1), size(T, 1));

[A, ib, ia] = unique([C == T C T], 'rows', 'stable');

CONF = zeros(m);

CONF(sub2ind([m, m], A(:,3), A(:,2))) = transpose(histcounts(ia, size(A, 1)));
