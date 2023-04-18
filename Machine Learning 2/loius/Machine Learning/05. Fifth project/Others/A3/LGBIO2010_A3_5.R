library('gplots')

analysis <- ns_filtering(df, 50/ncol(df)) #vai buscar as 50 colunas com maior variância
to_get_indexes <- train[c(names(analysis),"labels")]

#cria o vetor das cores que irá dizer se tem glioblastoma ou não
classification_labelled <- c()
for (i in (1:nrow(to_get_indexes))) {
  if (to_get_indexes[i,"labels"] == "No tumor") {
    classification_labelled <- append(classification_labelled, "blue")
  }
  else {
    classification_labelled <- append(classification_labelled, "gray")
  }
}

analysis_filtered <- train[c(names(analysis))]

#normalização
mean_vector <- c()
stds <- c() 
for (i in (1:(ncol(analysis_filtered)))) {
  mean_vector <- append(mean_vector,mean(analysis_filtered[,i]))
  stds <- append(stds, sd(analysis_filtered[,i]))
}

matrix_analysis <- data.matrix(analysis_filtered)
for (i in (1:nrow(matrix_analysis))) {
  for (j in (1:ncol(matrix_analysis))) {
    matrix_analysis[i,j] <- (matrix_analysis[i,j] - mean_vector[j]) / stds[j]
  }
}

#define um dégradé de cores, de vermelho a verde, com 500 cores intermédias
colors <- colorRampPalette(c("red","black","green"))(n = 500)

windows()

heatmap.2(matrix_analysis, main = "Variances of the 50 most \ndifferentially expressed features", xlab = "Probes", 
          ylab = "Patients", trace = "none", col = colors, margins = c(10,10), density.info = "none",
          RowSideColors = classification_labelled)

legend("topright",      # location of the legend on the heatmap plot
       legend = c("No tumor", "Glioblastoma"), # category labels
       col = c("blue", "gray"),  # color key
       lty= 1,             # line style
       lwd = 5,            # line width
       cex=0.55
)

#quando voltas a rodar o código para obter o heatmap caso já o tenhas feito, o R dá erro: roda esta
#função: dev.off()

dev.off()
