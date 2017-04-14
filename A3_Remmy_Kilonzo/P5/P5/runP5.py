import sys
import os.path
from subprocess import call


if __name__ == '__main__':
    FILE_PATH = '../../Texts/Novels/'
    file1 = FILE_PATH + sys.argv[1]
    file2 = FILE_PATH + sys.argv[2]
    N = [1, 2, 3]
    thresholds = [1, 5, 5]

    if not os.path.isfile(file1):
        print 'File {:s} does not exist.'.format(file1)
        sys.exit()

    if not os.path.isfile(file2):
        print 'File {:s} does not exist.'.format(file2)
        sys.exit()


    for n, threshold in zip(N, thresholds):
        print 'P5 {:s} {:s} {:d} {:d}'.format(file1, file2, n, threshold)
        call(['../Release/P5.exe', file1, file2, str(n), str(threshold)])
