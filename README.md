# Busca de Ciclos em Grafos
  Num grafo ciclos são caminhos, com no mínimo 3 vértices, que iniciam e terminam no mesmo vértice, e estão presentes na solução de diversos problemas como o do Caixeiro Viajante. Neste trabalho foram implementados dois algoritmos que listam todos os ciclos presente em um grafo. Essas soluções apresentam alta complexidade computacional e seu tempo de processamento escala rapidamente com o tamanho dos grafos, e achar uma solução de baixa complexidade é um dos maiores desafios atuais da computação.
  
  A primeira solução pode ser chamada de força bruta, nela são geradas todas as possíveis permutações de vértices que podem gerar ciclos, variando de tamanho 3 a N-1 (sendo N o número de vértices). Após geradas as permutações todas são checadas no intuito de verificar aquelas que realmente formam ciclos de acordo com as arestas presentes no grafo. E por fim, dentre as permutações selecionadas são removidas as repetições, restando apenas uma versão de cada ciclo.
  
  Na segunda implementação foi utilizada uma proposta mais inteligente, nela se usa da premissa que todos os ciclos passam por pelo menos uma aresta de retorno ou cruzamento. Então inicialmente é feito o caminhamento em profundidade e a classificação de todas as arestas, a partir disso as arestas de cruzamento e retorno são exploradas por meio de um caminhamento em largura e todos os ciclos encontrados. Por fim só são removidas as repetições e todos os ciclos são listados de forma única.

## Estudo prático
Na aplicação prática do estudo foram utilizados 4 grafos diferentes variando tanto o número de arestas quanto o de vértices. Assim possibilitando a avaliação de desempenho dos diferentes algoritmos em situações diversas. O primeiro grafo observado na figura abaixo apresenta 7 vértices e 9 arestas, o segundo 6 vértices e 11 arestas, o terceiro 10 vértices e 45 arestas e o último 4 vértices e 5 arestas.

![grafos estudados](https://i.imgur.com/AvfstCO.png)

A partir da aplicação dos algoritmos nos diversos grafos representados na figura acima foram obtidos os seguintes resultados:

|Algoritmo|Grafo 1|Grafo 2|Grafo 3|Grafo 4|
| --- | --- | --- | --- | --- |
|Força Bruta|14174300 ns|10253200 ns|659882428300 ns|988100 ns|
|Caminhamento|2730700 ns|9873500 ns|+30 min|2021300 ns|
