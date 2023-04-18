train_filtered <- (train[c(names(filter_25), "labels")])

differential_selection <- function(data, target, alpha) {
  
  notumor_index <- unlist(which(data[target] == "No tumor"))
  glioblastoma_index <- unlist(which(data[target] == "Glioblastoma"))
  selected <- c()
  for (i in (1:(ncol(data)-1))){
    t1 = data[notumor_index, i]
    t2 = data[glioblastoma_index, i]
    test = t.test(t1, t2, alternative="two.sided", var.equal=FALSE)$p.value
    if (test <= alpha){
      selected = append(selected, i)
    }
  }
  length(selected)
}

J = differential_selection(train_filtered, "labels", 0.05)

