function [err, CONF] = p2(C, T)
[m, i] = max(C(:));
[n, i] = max(T(:));
m = max(m, n);

err = sum(C ~= T)/max(size(C, 1), size(T, 1));

[A, ib, ic] = unique([C == T C T], 'rows', 'stable');

CONF = zeros(m);
CONF(sub2ind([m, m], A(:,3), A(:,2))) = transpose(histcounts(ic));
