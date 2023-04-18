df = train[, -which(colnames(train) == "labels")]

ns_filtering <- function(df, kept) {
  variances <- c()
  for (i in (1:ncol(df))) {
    variances <- append(variances, var(df[(1:(nrow(df))), i]))
  }
  names(variances) <- colnames(df)
  variances <- sort(variances, decreasing = TRUE)
  variances <- variances[1:as.integer(length(variances)*kept)]
  variances
}

filter_25 <- ns_filtering(df,0.25)

saveRDS(filter_25, "filtered_variances.rds")
