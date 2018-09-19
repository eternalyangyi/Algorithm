
import math

def lmat(l,z0):
    for i in range(len(l) + 1):
        if (i == len(l)):
            l.append([])
        if (l[i] != []):
            temp = l[i]
            temp.extend(z0)
            z0 = temp
            l[i] = []
        else:
            l[i] = sorted(z0)
            break
    return l

def Logarithmic_merge(index, cut_off, buffer_size):
    z0 = []
    L = [[]]
    result = []
    count = 0
    while count < cut_off:
        token = index[count]
        z0.append(token)
        if(len(z0) == buffer_size):
            L= lmat(L,z0)
            z0 = []
        count += 1;
    return [z0] + L
