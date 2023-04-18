#filtering
test_filtered = test[, colnames(train_filtered)]

#scaling data as in the training set
mean_vector <- c()
stds <- c() 
for (i in (1:(ncol(train_filtered)-1))) {
  mean_vector <- append(mean_vector, mean(train_filtered[,i]))
  stds <- append(stds, sd(train_filtered[,i]))
}

test_norm <- data.frame(test_filtered)
for (i in (1:nrow(test_norm))) {
  for (j in (1:(ncol(test_norm)-1))) {
    test_norm[i,j] <- (test_norm[i,j] - mean_vector[j]) / stds[j]
  }
}

#testing model
predictions = predict(model, test_norm[,-ncol(test_norm)])$predictions
true_labels = test_norm[, "labels"]
confusion_matrix = table(predictions, true_labels)


#Classification accuracy is a metric that summarizes the performance of a 
#classification model as the number of correct predictions divided by the total 
#number of predictions.
classification_accuracy <- function(confusion_matrix) {
  correct_predictions <- 0
  for (i in (1:nrow(confusion_matrix))) {
    correct_predictions <- correct_predictions + confusion_matrix[i,i] 
  }
  accuracy <- correct_predictions / sum(confusion_matrix)
  accuracy
}

classification_accuracy(confusion_matrix)

saveRDS(confusion_matrix, "confusion_matrix.rds")
