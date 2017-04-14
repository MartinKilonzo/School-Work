
function [M,P] = seamV_DP(E)
  M = E(1, :);
  P = ones(1, size(E, 2)) * -1;
    for iRow=1:size(E, 1) - 1
      row = M(iRow, :);
      p = [];
      for iCol=1:size(E, 2)
        s = max(1, iCol - 1);
        e = min(size(row, 2), iCol + 1);
        r = row(s:e);
        m = find(r==min(r)) + s - 1;
        p = [p m(1)];
      end
      P = [P; p];
      M = [M; M(iRow, p) + E(iRow + 1, :)];
  end
end
