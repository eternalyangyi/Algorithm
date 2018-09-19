
import math

def decode_gamma(inputs):
    result = []
    while len(inputs) != 0:
        kd = inputs.find("0")
        part1 = 2 ** kd
        part2 = int(inputs[kd+1: 2 * kd + 1],2)
        result.append(part1 + part2)
        inputs = inputs[2*kd + 1:]
    return result

def decode_delta(inputs):
    result = []
    while len(inputs) != 0:
        kdd = inputs.find("0") + 1
        #print(kdd)
        part2 = inputs[kdd: 2 * kdd - 1]
        part2 = "1" + part2
        #print(part2)
        l = int(part2,2) -1
        #print(l)
        final = "1" + inputs[2* kdd -1: 2* kdd + l - 1]
        #print(final)
        result.append(int(final,2))
        inputs = inputs[2 * kdd + l - 1:]
    return result

def decode_rice(inputs, b):
    result = []
    while len(inputs) != 0:
        q = inputs.find("0")
        print(q)
        part1 = q * b
        print(part1)
        count = int(math.log(b,2))
        print(count)
        part2 = int(inputs[q : q + count + 1],2)
        inputs = inputs[q+count+1:]
        result.append(part1 + part2)
    return result
    
