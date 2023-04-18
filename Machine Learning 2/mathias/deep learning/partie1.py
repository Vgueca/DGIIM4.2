# -*- coding: utf-8 -*-
"""
Created on Wed Apr 20 17:31:45 2022

@author: mathm
"""
from itertools import cycle
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np

target_xor = np.array(
    [
        [0],
        [1],
        [1],
        [0]])

W=[[0.83,1.28],[0.91,1.11]]
B=[0.7,-1.1]
wy=[0.7,1.96]
by=-0.25
xi=[0,0]
learningrate=0.25
regu=0
