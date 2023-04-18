import pandas as pd
import numpy as np
from sklearn.model_selection import KFold
from sklearn.ensemble import AdaBoostClassifier
from sklearn.metrics import balanced_accuracy_score
from sklearn.ensemble import GradientBoostingClassifier
from imblearn.over_sampling import SMOTE 
import lightgbm as lgb
from sklearn.ensemble import VotingClassifier
from collections import Counter


train_df = pd.read_csv("ML-A5-2022_train.csv", index_col=0)
test_df = pd.read_csv("ML-A5-2022_test.csv", index_col=0)

# TRAIN file

K = 1200
labels = train_df['label']
train_df['label'][labels == -1] = 0
train_modif_df = train_df.copy()
train_modif_df = train_modif_df.drop("label", axis=1)
train_modif_df = train_modif_df.replace({"low": 0, "medium":1, "high": 2})
train_modif_df.fillna(train_modif_df.median(), inplace=True)
X_df=train_modif_df.to_numpy()
variances = sorted([(np.var(X_df[:, i]), i) for i in range(X_df.shape[1])], reverse=True)
k_bests = [j for i,j in variances[:K]]
X_df = X_df[:, k_bests]

# TEST file

testdf = test_df.copy()
testdf = testdf.replace({"low": 0, "medium":1, "high": 2})
testdf.fillna(testdf.median(), inplace=True)
testtab=testdf.to_numpy()
testtab = testtab[:, k_bests]

# Choose the model 

kf = KFold(n_splits=5)
X, y = X_df, labels.values
i = 0
scores = []
max_score = 0
for train_idx, test_idx in kf.split(X, y):
    X_tra, y_tra = X[train_idx], y[train_idx]
    X_test, y_test = X[test_idx], y[test_idx]
    sm=SMOTE(k_neighbors=15)
    X_train, y_train = sm.fit_resample(X_tra, y_tra)
    print('Resampled dataset shape %s' % Counter(y_train))
    model = lgb.LGBMClassifier(boosting_type='dart' ,num_iterations=400,learning_rate=0.05,num_leaves=127,n_estimators=50)
    clf=AdaBoostClassifier(n_estimators=202,learning_rate=0.9)
    clf2 = GradientBoostingClassifier(n_estimators=202,loss='exponential',min_samples_split=3,min_samples_leaf=5,max_features='auto',learning_rate=0.9)
    eclf1 = VotingClassifier(estimators=[('ab', clf), ('gb', clf2),('lgb',model)], voting='soft').fit(X_train, y_train)
    y_pred = eclf1.predict(X_test)
    score = balanced_accuracy_score(y_test, y_pred)    
    scores.append(score)
    print(f"Fold {i}: BCR boost={score}")
    i+=1
    
print(f"Average BCR boost:{np.mean(scores)}")
print(f"std :{np.std(scores)}")

#FINAL prediction

X_train, y_train = X_df, labels.values
model = lgb.LGBMClassifier(boosting_type='dart' ,num_iterations=400,learning_rate=0.05,num_leaves=127,n_estimators=50)
clf=AdaBoostClassifier(n_estimators=202,learning_rate=0.9)
clf2 = GradientBoostingClassifier(n_estimators=202,loss='exponential',min_samples_split=3,min_samples_leaf=5,max_features='auto',learning_rate=0.9)
eclf1 = VotingClassifier(estimators=[('ab', clf), ('gb', clf2),('lgb',model)], voting='soft').fit(X_train, y_train)
y_pred = eclf1.predict(testtab)
for i in range(len(y_pred)):
    if (y_pred[i]==0):
        y_pred[i]=-1
print(y_pred)
print(len(y_pred))

#TO CSV

df_pred = pd.DataFrame(data=y_pred,index=index)
df_pred.to_csv('test_pred.csv')

