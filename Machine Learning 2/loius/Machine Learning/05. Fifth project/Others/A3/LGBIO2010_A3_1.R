setwd("C:/Users/queirozdesou/Desktop/LGBIO2010/A3")

test <- read.csv("test.csv.bz2")
train <- read.csv("train.csv.bz2")


length(train)

rownames(train) <- train[,1]
train = train[,-1]

rownames(test) <- test[,1]
test = test[,-1]

length(train)

saveRDS(c(82,10001), "length of the train dataset")

saveRDS(train, "train dataset")
saveRDS(test, "test dataset")
