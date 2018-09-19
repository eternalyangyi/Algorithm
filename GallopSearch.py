
import math

def binary_search(x,begin,end,a):
    mid = (begin + end) // 2
    if(end >= begin):
        if (x > a.data[a.cur + mid]):
            return binary_search(x, mid + 1, end,a)
        elif(x < a.data[a.cur +mid]):
            return binary_search(x,begin, mid - 1,a)
        else:
            return  a.cur + mid
    else:
        return  a.cur + end + 1

        
def gallop_to(a, val):
    delta = 1;
    while(a.cur + delta < len(a.data) and a.data[min((len(a.data) - 1),a.cur + delta)] <  val):
        delta *= 2;
    if(a.cur+delta >= len(a.data)):
        theta = delta // 2;
        a.cur = binary_search(val, theta, (len(a.data)- 1 - a.cur) ,a)
        return
    else:
        theta = delta // 2;
        a.cur = binary_search(val, theta ,  delta, a)
        return
