from subprocess import call


if __name__ == '__main__':
    N = [1, 2, 3, 1, 2, 3, 3, 3, 3, 2, 2, 2]
    deltas = [0, 0, 0, 0.05, 0.05, 0.05, 0.05, 0.005, 0.0005, 0.05, 0.05, 0.05]
    sentenceLengths = [50, 50, 50, 50, 50, 50, 50, 50, 50, 10, 50, 100]


    for n, delta, sentenceLength in zip(N, deltas, sentenceLengths):
        print 'P6 {:d} {:.4f} {:d}'.format(n, delta, sentenceLength)
        call(['../Release/P6.exe', str(n), str(delta), str(sentenceLength)])
