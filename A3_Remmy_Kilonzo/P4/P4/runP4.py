import sys
import os.path
from subprocess import call


if __name__ == '__main__':
    print " ".join(sys.argv)
    FILE_PATH = '../../Texts/Novels/'

    n = [1, 2, 2, 3]
    deltas = [1, 1, 0.001, 0.001]

    # Check that the files exist before running
    file1 = os.path.join(FILE_PATH, sys.argv[1])
    file2 = os.path.join(FILE_PATH, sys.argv[2])
    if not os.path.isfile(file1):
        print 'File {:s} does not exist.'.format(file1)
        sys.exit()
    if not os.path.isfile(file2):
        print 'File {:s} does not exist.'.format(file2)
        sys.exit()

    for i, delta in enumerate(deltas):
        call(['../Debug/P4.exe', FILE_PATH + sys.argv[1],
              FILE_PATH + sys.argv[2], str(n[i]), str(delta)])
