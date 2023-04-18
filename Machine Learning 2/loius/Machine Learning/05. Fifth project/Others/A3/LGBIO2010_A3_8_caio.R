library("LiblineaR")

#centering and normalizing data
mean_vector <- c()
stds <- c() 
for (i in (1:(ncol(train_filtered)-1))) {
  mean_vector <- append(mean_vector, mean(train_filtered[,i]))
  stds <- append(stds, sd(train_filtered[,i]))
}

train_norm <- data.frame(train_filtered)
for (i in (1:nrow(train_norm))) {
  for (j in (1:(ncol(train_norm)-1))) {
    train_norm[i,j] <- (train_norm[i,j] - mean_vector[j]) / stds[j]
  }
}

#build model
model = LiblineaR(data=train_norm[,-ncol(train_norm)], target=train_norm[,"labels"], type=2)

#access 10 most relevant features in the model
features_10 = sort(abs(model$W[1,]), decreasing=TRUE)[2:11] #first element is the bias

#get Ranking accordingly to the p-values
##modified A3_3 function
rank_p_values <- function(data, target, alpha) {
  
  notumor_index <- unlist(which(data[target] == "No tumor"))
  glioblastoma_index <- unlist(which(data[target] == "Glioblastoma"))
  selected <- c()
  for (i in (1:(ncol(data)-1))){
    t1 = data[notumor_index, i]
    t2 = data[glioblastoma_index, i]
    test = t.test(t1, t2, alternative="two.sided", var.equal=FALSE)$p.value
    selected = append(selected, test)
  }
  names(selected) = colnames(data[-ncol(data)])
  sort(selected)
}

ranked_p_values = rank_p_values(train_filtered, "labels", 0.05)

##get ranking
rank = c()
for (i in (1:10)){
  rank = append(rank, which(names(ranked_p_values)==names(features_10[i])))
}

#build required data.frame
SVM_weights_ranks <- data.frame(matrix(c(features_10,rank),ncol = 2, nrow = 10))
colnames(SVM_weights_ranks) <- c("Weight", "Rank")
rownames(SVM_weights_ranks) <- names(features_10)
SVM_weights_ranks

saveRDS(SVM_weights_ranks, "SVM_weights_ranks.rds")
