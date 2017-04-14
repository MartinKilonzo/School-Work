from subprocess import call


if __name__ == '__main__':
    FILE_PATH = '../../InputOutput/'
    trainingText = 'hugeTrain.txt'
    proofingText = 'textCheck.txt'
    dictionary = 'dictionary.txt'

    N = [2, 2, 2]
    deltas = [1, 0.1, 0.01]
    threshold = str(3)
    model = str(1)
    for n, delta in zip(N, deltas):
        print 'P8 {:s}'.format(' '.join([trainingText, proofingText, dictionary, str(n), threshold, str(delta), model]))
        call(['../Release/P8.exe', FILE_PATH + trainingText, FILE_PATH + proofingText, FILE_PATH + dictionary, str(n), threshold, str(delta), model])
