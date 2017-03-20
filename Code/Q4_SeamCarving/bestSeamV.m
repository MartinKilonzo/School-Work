function [seam,c] = bestSeamV(M,P)
  m = find(M(end, :)==min(M(end, :)));
  seam = m(1);
  c = M(end, m);
  c = c(1);
  for iRow=2:size(M, 1)
    row = M(end - iRow + 1, :);
    s = max(1, seam(1, 1) - 1);
    e = min(size(row, 2), seam(1, 1) + 1);
    r = row(s:e);
    m = find(r==min(r)) + s - 1;
    seam = [m(1); seam];
  end
end
