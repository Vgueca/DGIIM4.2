install.packages("LiblineaR")
library("LiblineaR")

mean_vector <- c()
stds <- c() 
for (i in (1:(ncol(train_filtered)-1))) {
  mean_vector <- append(mean_vector,mean(train_filtered[,i]))
  stds <- append(stds, sd(train_filtered[,i]))
}

#matriz com as medições centradas e normalizadas
normalized_train <- matrix(train_filtered[,1:2500] , ncol = ncol(train_filtered)-1, nrow = 
                             nrow(train_filtered))
for (i in (1:nrow(train_filtered))) {
  for (j in (1:(ncol(train_filtered)-1))) {
    normalized_train[i,j] <- (train_filtered[i,j] - mean_vector[j]) / stds[j]
    rownames(normalized_train) <- rownames(train_filtered)
    colnames(normalized_train) <- colnames(train_filtered[1:(ncol(train_filtered)-1)])
  }
}
#vetor com os estados
ex <- data.frame(nrow = 82)
for (i in (1:82)) {
  ex[i,1] <- train_filtered[i,"labels"]
  colnames(ex) <- "labels"
  rownames(ex)[i] <- row.names(train_filtered)[i]
}

#criação do SVM
SVM <- LiblineaR(normalized_train, ex, type = 2)
weights <- SVM$W[1,] #não entendo porque é que há 2501 weights quando o dataset só tem 2500 colunas

#definição das features com maior peso
biggest_weights <- sort(weights, decreasing = TRUE)[1:10]

#recuperar os índices correspondentes às features com maior peso
ind_vector <- c()
for (i in (1:length(weights))) {
  for (j in (1:length(biggest_weights))) {
    if (weights[i] == biggest_weights[j]) {
      ind_vector <- append(ind_vector, i)
    }
  }
}

colnames(normalized_train)[ind_vector]

#função para devolver uma lista com os p-values de todas as features
p_value <- function(data, target, alpha) {
  notumor_index <- unlist(which(data[target] == "No tumor"))
  glioblastoma_index <- unlist(which(data[target] == "Glioblastoma"))
  p_value_list <- c()
  for (i in (1:(ncol(data)-1))){
    t1 = data[notumor_index, i]
    t2 = data[glioblastoma_index, i]
    test = t.test(t1, t2, alternative="two.sided", var.equal=FALSE)$p.value
    p_value_list <- append(p_value_list,test) 
  }
  p_value_list
}

p_value(train_filtered, "labels", 0.05)

#faz o rank de todos os p-values, devolvendo uma lista com os ranks correspondentes
#aos índices que obtivemos em cima
ranks <- rank(p_value(train_filtered, "labels", 0.05))[ind_vector]

#constrói o data.frame pedido
SVM_weights_ranks <- data.frame(matrix(c(biggest_weights,ranks),ncol = 2, nrow = 10))
colnames(SVM_weights_ranks) <- c("Weight", "Rank")
rownames(SVM_weights_ranks) <- colnames(normalized_train)[ind_vector]
