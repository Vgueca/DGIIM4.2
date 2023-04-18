less_discriminative <- correct(train_filtered, "labels", 100, "fdr")[2499:2500]

less_discriminative_df <- (train[,c(names(less_discriminative),"labels")])

#centering and normalizing data
mean_vector <- c()
stds <- c() 
for (i in (1:(ncol(less_discriminative_df)-1))) {
  mean_vector <- append(mean_vector, mean(less_discriminative_df[,i]))
  stds <- append(stds, sd(less_discriminative_df[,i]))
}

for (i in (1:nrow(less_discriminative_df))) {
  for (j in (1:(ncol(less_discriminative_df)-1))) {
    less_discriminative_df[i,j] <- (less_discriminative_df[i,j] - mean_vector[j]) / stds[j]
  }
}

colors <- c()
for (i in (1:nrow(less_discriminative_df))) {
  if (less_discriminative_df[i,3] == "No tumor") {
    colors <- append(colors, "blue")
  }
  else{
    colors <- append(colors, "red")
  }
}

windows()

plot(less_discriminative_df[,2], less_discriminative_df[,1], type = 'p', xlab = colnames(less_discriminative_df)[2],
     ylab = colnames(less_discriminative_df)[1], mar=c(1,1,1,1), col = colors, main = "2 least discriminative features")


legend("bottomright",      
       legend = c("No tumor", "Glioblastoma"), # category labels
       col = c("blue", "red"),  # color key
       lty= 1,             # line style
       lwd = 5,            # line width
       cex=0.55
)

dev.off() 

#comparing gene expression level 
less_discriminatives <- (train[,c(names(less_discriminative),"labels")])

mean_gliob <- c()
mean_notum <- c()
for (i in c(1,2)) {
  mean_gliob <- append(mean_gliob, mean(less_discriminatives[which(less_discriminatives["labels"]== "Glioblastoma"),i]))
  mean_notum <- append(mean_notum, mean(less_discriminatives[which(less_discriminatives["labels"]== "No tumor"),i]))
}
# gene X_46218: mean Glioblastoma=3.14  /  mean No tumor=3.14
# gene X_47612: mean Glioblastoma=3.48  /  mean No tumor=3.48
expression_level_less = c(0,0)
names(expression_level_less) = c('X_47612', 'X_46218')
saveRDS(expression_level_less, "expression_level_less.rds")
