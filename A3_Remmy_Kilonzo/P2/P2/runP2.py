import sys
import os.path
from subprocess import call


if __name__ == '__main__':
    print " ".join(sys.argv)
    n = int(sys.argv[1])

    FILE_PATH = '../../Texts/Novels/'

    texts = [('DostoevskyPart1.txt', 'DostoevskyPart2.txt'), ('Dickens.txt', 'KafkaTrial.txt'), ('MarxEngelsManifest.txt','SmithWealthNations.txt')]

    # Check that the files exist before running
    for text1, text2 in texts:
        file1 = os.path.join(FILE_PATH, text1)
        file2 = os.path.join(FILE_PATH, text2)
        if not os.path.isfile(file1):
            print 'File {:s} does not exist.'.format(file1)
            sys.exit()
        if not os.path.isfile(file2):
            print 'File {:s} does not exist.'.format(file2)
            sys.exit()

    # Run the routine
    for text1, text2 in texts:
        print '{:s} and {:s}'.format(text1, text2)
        call(['../../P2a/Debug/P2a.exe', FILE_PATH + text1, FILE_PATH + text2, str(n), '1'])
