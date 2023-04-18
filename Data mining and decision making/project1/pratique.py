import random
import numpy as np


def Dice (dice):
    
    if dice==1:
        return random.randint(0,1)
    elif dice==2:
        return random.randint(0,2)
    else:
        return random.randint(0,3)
def Trap(trap, i):
    
    
    #return id_trap, newPos
    if trap[i] == 1:
        return 1, 0
    elif trap[i] == 2:
        if (i-3<0):
            return 2,0
        elif i>=10 and i<13: # if we are on the fast lane 
            return 2, i-10
        else:
            return 2,i-3
    elif  trap[i]==3:
        return 3,i
    elif trap[i]==4:
        return 4, random.randint(0,14)
    else:
        #not trap
        return 0, i

def NextCase(i, step, trap, circle, dice ):
    
    nextStep = i
    if step==0:
        nextStep = i
    elif i==2:
        b=random.randint(0,1)
        if b==1:
            nextStep+=step
        else:
            nextStep=9+step
            
    elif i==9 and step!=0:
        if circle and step==2:
            nextStep=0
        elif circle and step==3:
            nextStep=1
        else:
            nextStep=14
            
    elif i==8 and step==2:
        nextStep=14
    elif i==8 and step==3:
        if circle:
            nextStep=0
        else:
            nextStep=14
            
    elif i==7 and step==3:
        nextStep=14
        
    elif i==13 and step!=0:
        if circle and step==2:
            nextStep=0
        elif circle and step==3:
            nextStep=1
        else:
            nextStep=14
            
    elif i==12 and step==3:
        if circle:
            nextStep=0
        else:
            nextStep=14
        
    else:
        nextStep+=step
    counter = 0
    if dice==3:
        tr, newStep = Trap(trap, nextStep)
        if tr==3:
            counter = 1
            
    if dice==2:
        trapOrNot = random.randint(0,1)
        if (trapOrNot==1): #•on prend la trappe
            tr, newStep = Trap(trap, nextStep)
            if(tr==3):
                counter = 1
        else:  #on prend pas la trape
            newStep = nextStep
    if dice==1:
        newStep=nextStep
            
    return newStep, counter

def Map():
    trap = np.zeros(15)
    trap[7]=1
    trap[8]=4
    trap[9]=2
    trap[1]=3
    return trap

def empi1 (trap,simu, circle ):
    count=np.zeros(14)
    optiDice = [2., 1., 3., 3., 2., 3. ,2., 1. ,1., 1. ,3., 3., 3., 3.]
    for j in range(14): 
        countk=0
        for _ in range(simu):
            k=j
            while(k<14):
                """
                k = i
                step = Dice(dice)
                trap = trap
                circle = circle
                dice = dice
                """
                dice = optiDice[k]
                #dice = 3
                #dice = 2
                #dice = 3
                #dice = random.randint(1,3)
                step = Dice(dice)
                
                k, countNext = NextCase(k, step, trap, circle, dice)
                

                countk += countNext + 1
        count[j]=countk/simu
    return count

def empi2(trap,simu,circle):
    count=np.zeros(14)
    for j in range(14): 
        countk=0
        for _ in range(simu):
            k=j
            while(k<14):
                a=random.randint(0,2)
                if k==9 and a!=0:
                    if circle and a==2:
                        k=0
                    else:
                        k=14
                elif k==8 and a==2:
                    k=14
                elif k==2 and a!=0:
                    b=random.randint(0,1)
                    if b==1:
                        k+=a
                    else:
                        k=9+a
                if k==13 and a!=0:
                    if circle and a==2:
                        k=0
                    else:
                        k=14
                else:
                    k+=a
                countk+=1
        count[j]=countk/simu
    return count



trap = Map()
print(empi1(trap,100000, False))
#print(empi2(trap,100000,False))