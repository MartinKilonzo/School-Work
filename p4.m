function C = p4(w, X)
trueClasses = w(1) + X * w(2:end)
cond = trueClasses > 0
C = cond.*1 + ~cond.*2