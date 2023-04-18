most_discriminative <- correct(train_filtered, "labels", 0.05, "fdr")[1:2]

most_discriminative_df <- (train[,c(names(most_discriminative),"labels")])

#centering and normalizing data
mean_vector <- c()
stds <- c() 
for (i in (1:(ncol(most_discriminative_df)-1))) {
  mean_vector <- append(mean_vector, mean(most_discriminative_df[,i]))
  stds <- append(stds, sd(most_discriminative_df[,i]))
}

for (i in (1:nrow(most_discriminative_df))) {
  for (j in (1:(ncol(most_discriminative_df)-1))) {
    most_discriminative_df[i,j] <- (most_discriminative_df[i,j] - mean_vector[j]) / stds[j]
  }
}

colors <- c()
for (i in (1:nrow(most_discriminative_df))) {
  if (most_discriminative_df[i,3] == "No tumor") {
    colors <- append(colors, "blue")
  }
  else{
    colors <- append(colors, "red")
  }
}

windows()

plot(most_discriminative_df[,1], most_discriminative_df[,2], type = 'p', xlab = colnames(most_discriminative_df)[1],
     ylab = colnames(most_discriminative_df)[2], mar=c(1,1,1,1), col = colors, main = "2 most discriminative features")


legend("bottomright",      
       legend = c("No tumor", "Glioblastoma"), # category labels
       col = c("blue", "red"),  # color key
       lty= 1,             # line style
       lwd = 5,            # line width
       cex=0.55
      )

dev.off() 

#comparing gene expression level 
most_discriminatives <- (train[,c(names(most_discriminative),"labels")])

mean_gliob <- c()
mean_notum <- c()
for (i in c(1,2)) {
  mean_gliob <- append(mean_gliob, mean(most_discriminatives[which(most_discriminatives["labels"]== "Glioblastoma"),i]))
  mean_notum <- append(mean_notum, mean(most_discriminatives[which(most_discriminatives["labels"]== "No tumor"),i]))
}
# gene X_27963: mean Glioblastoma=8.69  /  mean No tumor=6.57
# gene X_37364: mean Glioblastoma=5.89  /  mean No tumor=7.82
expression_level = c(1,-1)
names(expression_level) = c('X_27963', 'X_37364')
saveRDS(expression_level, "expression_level.rds")
