import pandas as pd
from sklearn import tree
train_df = pd.read_csv("WineTrain.csv", index_col=0)
test_df = pd.read_csv("WineTest.csv", index_col=0)
test_df_in=test_df.drop(columns=['Quality'])
true_res_test=test_df.drop(columns=['FixedAcidity', 'VolatileAcidity', 'CitricAcid', 'Sulphates', 'Alcohol', 'ResidualSugar', 'Chlorides', 'FreeSulfurDioxide', 'Density', 'pH', 'TotalSulfurDioxide'])
frame = pd.DataFrame(columns = ["min_samples_split", "NodeCount", "TrainAcc", "TestAcc"])
classwine=tree.DecisionTreeClassifier(random_state=0)
y_train=train_df.copy()
ta=train_df.drop(columns=['Quality'])
y_train.drop(columns=['FixedAcidity', 'VolatileAcidity', 'CitricAcid', 'Sulphates', 'Alcohol', 'ResidualSugar', 'Chlorides', 'FreeSulfurDioxide', 'Density', 'pH', 'TotalSulfurDioxide'], inplace=True)

classwine=tree.DecisionTreeClassifier(random_state=0,min_samples_leaf=40,min_samples_split=100)

classwine.fit(ta,y_train)
y_test_train = classwine.predict(ta)
y_test_pred = classwine.predict(test_df_in)
a=0
y_traintab=y_train.to_numpy()
for i in range (len(y_test_train)):
    if y_test_train[i]== y_traintab[i]:
            a=a+1
true_res_testtab=true_res_test.to_numpy()
b=0
for j in range (len(true_res_testtab)):
    if true_res_testtab[j]== y_test_pred[j]:
            b=b+1
frame.loc[0]=[2,classwine.tree_.node_count,a/1000,b/599]



classwine2=tree.DecisionTreeClassifier(random_state=0,min_samples_leaf=40,min_samples_split=110)


classwine2.fit(ta,y_train)
y_test_train = classwine2.predict(ta)
y_test_pred = classwine2.predict(test_df_in)

a=0
y_traintab=y_train.to_numpy()
for i in range (len(y_test_train)):
        if y_test_train[i]== y_traintab[i]:
            a=a+1
true_res_testtab=true_res_test.to_numpy()
b=0
for j in range (len(true_res_testtab)):
        if true_res_testtab[j]== y_test_pred[j]:
            b=b+1
frame.loc[1]=[10,classwine2.tree_.node_count,a/1000,b/599]
    #120

classwine3=tree.DecisionTreeClassifier(random_state=0,max_depth=2)

classwine3.fit(ta,y_train)
y_test_train = classwine3.predict(ta)
y_test_pred = classwine3.predict(test_df_in)

a=0
y_traintab=y_train.to_numpy()
for i in range (len(y_test_train)):
        if y_test_train[i]== y_traintab[i]:
            a=a+1
true_res_testtab=true_res_test.to_numpy()
b=0
for j in range (len(true_res_testtab)):
        if true_res_testtab[j]== y_test_pred[j]:
            b=b+1
frame.loc[2]=[50,classwine3.tree_.node_count,a/1000,b/599]
importance = classwine2.feature_importances_
print(importance)
print(["FixedAcidity,VolatileAcidity,CitricAcid,ResidualSugar,Chlorides,FreeSulfurDioxide,TotalSulfurDioxide,Density,pH,Sulphates,Alcohol,Quality"])

print(frame)

