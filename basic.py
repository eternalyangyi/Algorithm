## import modules here
import math
import re
import copy

################# Question 0 #################

def add(a, b):
    return a + b


################# Question 1 #################

def nsqrt(x):
    Low = 0
    High = x
    mid = x // 2
    while True:
        if int(mid ** 2) == x:
            return int(mid)    
        elif mid ** 2 > x:
            High = mid
            mid = (Low + High) / 2
        else:
            Low = mid
            mid = (Low + High) / 2   


################# Question 2 #################

'''
x_0: initial guess
EPSILON: stop when abs(x - x_new) < EPSILON
MAX_ITER: maximum number of iterations

NOTE: you must use the default values of the above parameters, do not change them
'''
def find_root(f, fprime, x_0=1.0, EPSILON = 1E-7, MAX_ITER = 1000):
    x_new = x_0 - f(x_0) / fprime(x_0)
    Num_Iter = 1
    while Num_Iter < MAX_ITER:
        x = x_new - f(x_new) / fprime(x_new)
        Num_Iter += 1
        if abs(x - x_new) < EPSILON:
            break
        x_new = x
    return x


################# Question 3 #################

class Tree(object):
    def __init__(self, name='ROOT', children=None):
        self.name = name
        self.children = []
        if children is not None:
            for child in children:
                self.add_child(child)
    def __repr__(self):
        return self.name
    def add_child(self, node):
        assert isinstance(node, Tree)
        self.children.append(node)


def make_tree(tokens):
    mark = {']':'['}
    stack = []
    child_node_list = []
    for token in tokens:
        if token not in mark:
            if token != '[':
                node = Tree(token)
                stack.append(node)
            else:
                stack.append(token)
        else:
            while len(stack) > 0:
                child = stack.pop()
                if child == '[':
                    break
                child_node_list.append(child)
            father_tree = stack.pop()
            while len(child_node_list) > 0:
                father_tree.add_child(child_node_list.pop())
            stack.append(father_tree)
    return stack.pop()   

def max_depth(root):
    queue = [[root]]
    paths = []
    Depth = []
    depth = 0
    while queue:
        path = []
        child = queue.pop(0)
        path.append(child)
        if child[-1].children:
            for childs in child[-1].children:
                path2 = copy.deepcopy(path)
                path2[0].append(childs)
                queue.append(path2[0])
    for el in path[-1]:
        depth += 1
    return depth
    pass

