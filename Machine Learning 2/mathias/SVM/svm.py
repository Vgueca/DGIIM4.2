# -*- coding: utf-8 -*-
"""
Created on Wed Mar  2 17:08:44 2022

@author: mathm
"""

import pandas as pd
from sklearn.svm import SVC
from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
from sklearn.feature_selection import VarianceThreshold
from sklearn.preprocessing import StandardScaler

train_df = pd.read_csv("HeartTrain.csv", index_col=0)
test_df = pd.read_csv("HeartTest.csv", index_col=0) 
Y=train_df['labels']
X=train_df.drop(['labels'],axis=1)
Y_test=test_df['labels']
X_test=test_df.drop(['labels'],axis=1)
thresholder = VarianceThreshold(threshold=30)
thresholder.fit(X)
print(sum(thresholder.get_support()))
coltodrop=[col for col in X.columns
           if col not in X.columns[thresholder.get_support()]]
print(coltodrop)
X.drop(coltodrop, axis=1, inplace=True)
print(X)

SVMmodel = SVC( kernel='sigmoid',C=1)

cv=KFold(10)
accu=cross_val_score(SVMmodel,X,Y,cv=cv)
cv_acc=0
for i in accu:
    cv_acc+=i
cv_acc=cv_acc/10
print(cv_acc)
SVMmodel.fit(X,Y)
X_test.drop(coltodrop, axis=1, inplace=True)
print(SVMmodel.score(X_test,Y_test))