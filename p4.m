function C = p4(w, X)
C = w(1) + X * w(2:end)
cond = C > 0
C = cond.*1 + ~cond.*2