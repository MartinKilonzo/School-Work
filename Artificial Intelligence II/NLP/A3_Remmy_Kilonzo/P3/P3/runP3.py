import sys
from subprocess import call


if __name__ == '__main__':
    print " ".join(sys.argv[1:])
    FILE_PATH = '../../Texts/'

    for n in xrange(6):
        call(['../Debug/P3.exe', FILE_PATH + sys.argv[1], str(n + 1)])
