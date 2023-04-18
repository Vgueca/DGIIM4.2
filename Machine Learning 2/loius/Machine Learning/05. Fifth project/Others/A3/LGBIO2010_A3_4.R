correct <- function(data, target, alpha, method) {
  
  notumor_index <- unlist(which(data[target] == "No tumor"))
  glioblastoma_index <- unlist(which(data[target] == "Glioblastoma"))
  p_value <- c()
  for (i in (1:(ncol(data)-1))){
    t1 = data[notumor_index, i]
    t2 = data[glioblastoma_index, i]
    p_value = append(p_value, t.test(t1, t2, alternative="two.sided", var.equal=FALSE)$p.value)
    names(p_value)[i] = colnames(data)[i]
  }
  
  p_value = p.adjust(p_value, method=method)
  p_value = sort(p_value)
  p_value = p_value[which(p_value <= alpha)]
  p_value
}

bonf <- correct(train_filtered, "labels", 0.05, "bonferroni")
fdr <- correct(train_filtered, "labels", 0.05, "fdr")

#uncorrected <- correct(train_filtered, "labels", 0.05, "xxx")
#saveRDS(uncorrected, "uncorrected.rds")

saveRDS(bonf, "bonf.rds")
saveRDS(fdr, "fdr.rds")