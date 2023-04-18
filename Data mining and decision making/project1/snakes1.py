import numpy as np
import matplotlib.pyplot as plt
import pprint
import math
import random


"""
Define a map and the trap on it 

• Type 1 – Restart: Immediately teleports the player back to the first square.
• Type 2 – Penalty: Immediately teleports the player 3 squares backwards.
• Type 3 – Prison: The player must wait one turn before playing again.                  ----   NOT YET IMPLEMENTED   ----
• Type 4 – Gamble: Randomly teleports the player anywhere on the board1, with equal,
uniform, probability.
"""
def Map():
    trap = np.zeros(15)
    trap[7]=1
    trap[8]=4
    trap[9]=2
    trap[1]=3
    return trap


"""
Here it's a code that modify the transition matrix for the dice (0-1-2-3)

    # INPUT : trap = layout of the map 
            : proba = transition matrix of the map without traps 

    # OUTPUT : modify the probabilities to take into acount the traps
"""
def addtraptrois(trap,proba):
    for i in range(len(trap)):
        if trap[i]!=0:
            # We go through the column to check if it's possible to reach the trap on a position i from a state j
            for j in range(len(proba)):
                if proba[j][i]!=0:

                    if trap[i]==1: # return to 0
                        proba[j][0]+=proba[j][i]
                        proba[j][i]=0

                    if trap[i]==2: # -3 position
                        if i-3<0: # below 0 we go to pos 0
                            proba[j][0]+=proba[j][i]
                        elif i>=10 and i<13: # if we are on the fast lane 
                            proba[j][i-10]+=proba[j][i]
                        else: # clasic -3 position
                            proba[j][i-3]+=proba[j][i]
                        proba[j][i]=0
    """
    We check the trap 4 after to avoid dual punition (tp on a position and then get the malus of the case we tp on)
    """
    for i in range(15):
        if trap[i]==4:
            # We go through the column to check if it's possible to reach the trap on a position i from a state j
            for j in range(15):
                if proba[j][i]!=0:
                    var=proba[j][i]
                    proba[j][i]=0
                    for k in range (15):
                        proba[j][k]+=(var)/15 # distribute the proba of the position with the tp on all the square 
    return proba

"""
Here it's a code that modify the transition matrix for the dice (0-1-2) 

    # INPUT : trap = layout of the map 
            : proba = transition matrix of the map without traps 

    # OUTPUT : modify the probabilities to take into acount the traps
"""
def addtrapdeux(trap,proba):
    for i in range(len(trap)):
        if trap[i]!=0:
            # We go through the column to check if it's possible to reach the trap on a position i from a state j
            for j in range(len(proba)):
                if proba[j][i]!=0:

                    if trap[i]==1: # return to 0
                        proba[j][0]+=proba[j][i]/2
                        proba[j][i]=proba[j][i]/2

                    if trap[i]==2: # -3 position
                        if i-3<0: # below 0 we go to pos 0
                            proba[j][0]+=proba[j][i]/2
                        elif i>=10 and i<13: # if we are on the fast lane 
                            proba[j][i-10]+=proba[j][i]/2
                        else: # clasic -3 position
                            proba[j][i-3]+=proba[j][i]/2
                        proba[j][i]=proba[j][i]/2
    """
    We check the trap 4 after to avoid dual punition (tp on a position and then get the malus of the case we tp on)
    """               
    for i in range(15):
        if trap[i]==4:
            # We go through the column to check if it's possible to reach the trap on a position i from a state j
            for j in range(15):
                if proba[j][i]!=0:
                    var=proba[j][i]/2
                    proba[j][i]=proba[j][i]/2
                    for k in range (15):
                        proba[j][k]+=(var)/15 # distribute the proba of the position with the tp on all the square 
    return proba

"""
take the dice and the circle board argument 
And do the transition matrix of the game for a certain dice 
"""
def proba(de, trap,circle):
    probaList = np.zeros((15,15))
    if de==1:
        for i in range(len(probaList)):
            if i==14:
                probaList[i]=np.zeros(15)
                
            elif i ==2:
                probaList[i][2] = 0.5
                probaList[i][3]=0.5*0.5
                probaList[i][10]=0.5*0.5
            elif i==9:
                probaList[i][14]=0.5
                probaList[i][9]=0.5
            else:
                for j in range(i,i+2):          
                    probaList[i][j] = 0.5
        return probaList
    
    if de==2:
        for i in range(len(probaList)):
            if i==14:
                probaList[i]=np.zeros(15)
            elif i ==2:
                probaList[i][2] = 1/3
                probaList[i][3]=1/3*0.5
                probaList[i][10]=1/3*0.5
                probaList[i][4]=1/3*0.5
                probaList[i][11]=1/3*0.5
            elif i==8:
                probaList[i][14]=1/3
                probaList[i][9]=1/3
                probaList[i][8]=1/3
            elif i==9:
                # if circle !!!!!!
                if circle:
                    probaList[i][14]=1/3
                    probaList[i][0]=1/3
                else:
                    probaList[i][14]=2/3
                probaList[i][9]=1/3
            elif i==13:
                if circle:
                    probaList[i][14]=1/3
                    probaList[i][0]=1/3
                else:
                    probaList[i][14]=2/3
                probaList[i][13]=1/3
            else:
                for j in range(i,i+3):          
                    probaList[i][j] = 1/3
        return addtrapdeux(trap,probaList)

    if de==3:
        for i in range(len(probaList)):
            if i==14:
                probaList[i]=np.zeros(15)
            elif i ==2:
                probaList[i][2] = 1/4
                probaList[i][3]=1/4*0.5
                probaList[i][10]=1/4*0.5
                probaList[i][4]=1/4*0.5
                probaList[i][11]=1/4*0.5
                probaList[i][5]=1/4*0.5
                probaList[i][12]=1/4*0.5
            elif i==7:
                probaList[i][14]=1/4
                probaList[i][9]=1/4
                probaList[i][8]=1/4
                probaList[i][7]=1/4
            elif i==8:
                if circle:
                    probaList[i][14]=1/4
                    probaList[i][0]=1/4
                else:
                    probaList[i][14]=2/4
                probaList[i][9]=1/4
                probaList[i][8]=1/4
            elif i==9:
                if circle:
                    probaList[i][14]=1/4
                    probaList[i][0]=1/4
                    probaList[i][1]=1/4
                else:
                    probaList[i][14]=3/4
                probaList[i][9]=1/4
            
            elif i==12:
                if circle:
                    probaList[i][14]=1/4
                    probaList[i][0]=1/4
                else:
                    probaList[i][14]=2/4
                probaList[i][12]=1/4
                probaList[i][13]=1/4
            elif i==13:
                if circle:
                    probaList[i][14]=1/4
                    probaList[i][0]=1/4
                    probaList[i][1]=1/4
                else:
                    probaList[i][14]=3/4
                probaList[i][13]=1/4
            else:
                for j in range(i,i+4):
                    probaList[i][j] = 1/4
        newprob=addtraptrois(trap,probaList)
        return newprob
            
def getreward (trap):
    reward = np.zeros((3,15))
    for i in range(len(trap)):
        if (trap[i]==3):
            reward[1][i] = 0.5
            reward[2][i] = 1
    return reward

"""
Do the bellman recurence V function to find and return the number of expected turns and the more useful dice in a position
"""
def bellman(k, U,reward,trap,de):
    n_states = 15
    v = np.zeros(n_states)
    action = np.zeros(n_states)
    epsilon = 1e-6  # convergence threshold
    delta = 2 * epsilon  # initialize delta to be greater than epsilon
    prisons = np.where(trap == 3)[0]
    while delta > epsilon:
        delta = 0
        for s in range(n_states-1):
            v_old = v[s]
            v_action = np.zeros(len(de))
            for a in de: # Test all the action (3 dices)
                cost = 1+np.sum(U[a][s][prisons] * reward[a-1][s])
                v_action[a-1] = cost +  U[a][s] @ v
            v[s] = np.min(v_action) # Take the best expected value
            temp = np.min(v_action)
            action[s] = np.where(v_action==temp)[0][0]+1 # Save the dice withe the best expected value
            delta = max(delta, np.abs(v_old - v[s]))
    return v,action


def markovDecision(trap,circle):
    de= [1,2,3]
    carte = {}
    for i in de:
        carte[i] = proba(i, trap,circle)
    # Create a reward matrix for the trap 3
    reward = getreward(trap)
    Expec,Dice = bellman(0, carte, reward,trap,de) 
    print("bellman", Expec[0:14],Dice[0:14])
    print(len(Expec[0:14]))
    return [Expec[0:14],Dice[0:14]]
        
trap = Map()
markovDecision(trap,False)