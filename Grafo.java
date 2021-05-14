import java.util.*;
public class Grafo{
    int numArestas;
    int numVertices;
    ListaDeAjacencia la;
    ArvoreDeProfundidade ap;
    public Grafo(int numArestas, int numVertices, String[] arestas, boolean direcionado){
        this.numArestas = numArestas;
        this.numVertices = numVertices;
        la = new ListaDeAjacencia(numArestas,numVertices, arestas, direcionado);
    }
    // retorna o ultimo valor de um vetor q seja diferente de -1
    private int lastAvailable(int[] vet){
        int result = -1;
        for(int i = vet.length-1; i >= 0; i--)
            if(vet[i] != -1){
                result = vet[i];
                i = -1;
            }
        return result;
    }
    // retorna se um valor esta ou não presente em um vetor
    private boolean itsPresent(int[] vet, int objective){
        boolean result = false;
        for(int i = 0; i < vet.length && !result; i++)
            if(vet[i] == objective)
                result = true;
        return result;
    }
    // recebe dois vetores e retorna se são ou não permutação. Caso aja repetiçoes
    // de valores significativos no mesmo vetor (que não sejam som usados para 
    // indicar espaços vazios como o -1 nesse caso) a função não funcionara corretamente
    private boolean itsPermutation(int[] vet1, int[] vet2){
        int sizeV1 = vet1.length;
        int sizeV2 = vet2.length;
        for(int i = vet1.length-1; i>=0; i--){
            if(vet1[i] == -1)
            sizeV1--;
        }
        for(int i = vet2.length-1; i>=0; i--){
            if(vet2[i] == -1)
            sizeV2--;
        }
        boolean result = sizeV1 == sizeV2;
        if(result){
            boolean aux;
            for(int i = 0; i < vet1.length; i++){
                aux = false;
                for(int j = 0; j < vet1.length; j++){
                    aux = aux || vet1[i] == vet2[j];
                }
                result = result && aux;
            }
        }
        return result;
    }
    public void ciclos(){
        ap = new ArvoreDeProfundidade(la);
        Queue<int[]> aux = new LinkedList<int[]>();
        List<int[]> ciclosF = new ArrayList<int[]>();
        int[] caminho;
        //acha todos os ciclos ligados a cara aresta que nao seja de arvore
        for(int i=0; i<numArestas; i++){
            if(ap.type[i] != 'A'){
                caminho = new int[numVertices];
                for(int j = 0; j<numVertices; j++){
                    caminho[j] = -1;
                }
                caminho[0] = ap.arestas[i][0];
                aux.add(caminho);
                while(!aux.isEmpty()){
                    int[] adjacentes = la.adjacentesTo(lastAvailable(aux.peek()));
                    for(int adjacente : adjacentes){
                        // evita que o caminho va direto para o fim do ciclo ou entre em outro ciclo menor dentro do ciclo maior 
                        // o ciclo menor sera explorado em outro momento
                        if((lastAvailable(aux.peek()) != ap.arestas[i][0] || adjacente != ap.arestas[i][1]) && !itsPresent(aux.peek(), adjacente)){
                            caminho = new int[numVertices];
                            for(int k = 0, added = 0; k < numVertices; k++){
                                if(aux.peek()[k] == -1 && added == 0){
                                    caminho[k] = adjacente;
                                    added++;
                                }else{
                                    caminho[k] = aux.peek()[k];
                                }
                            }
                            if(adjacente == ap.arestas[i][1]){
                                ciclosF.add(caminho);
                            }else{
                                aux.add(caminho);
                            }
                        }
                    }
                    aux.remove();
                }
            }
        }
        //remove permutações do msm ciclo. Essa repetição ocorre quando um 
        //ciclo passa por 2 arestas que não sejam de arvore
        boolean moreThenOneCicle;
        for(int i = 0; i < ciclosF.size(); i++){
            moreThenOneCicle = false;
            caminho = ciclosF.get(i);
            
            for(int j = 1; j<caminho.length; j++)
                moreThenOneCicle = moreThenOneCicle || ap.typeOfAresta(caminho[j], caminho[j-1]) != 'A';

            if(moreThenOneCicle){
                for(int j = i+1; j < ciclosF.size(); j++){
                    if(itsPermutation(caminho, ciclosF.get(j))){
                        ciclosF.remove(j);
                    }
                }
            }
        }
        //imprime todos os ciclos na tela
        while(!ciclosF.isEmpty()){
            caminho = ciclosF.remove(0);
            for(int i : caminho)
                if(i != -1)
                    System.out.print(i + " ");
            System.out.println("");
        }
    }
    private void permutacao(int[] vet, int[] options, int i, List<int[]> permutacoes){
        int[] auxVet = new int[vet.length];

        for(int j = 0; j < vet.length; j++)
            auxVet[j] = vet[j];
                
        if(i < vet.length){
            int[] auxOptions = new int[options.length];
            for(int j = 0; j < options.length; j++)
                auxOptions[j] = options[j];

            for(int j = 0; j < options.length; j++){
                if(options[j] != -1){
                    auxVet[i] = auxOptions[j];
                    auxOptions[j] = -1;
                    permutacao(auxVet, auxOptions, i+1, permutacoes);
                    auxOptions[j] = auxVet[i];
                }
            }
        }else{
            permutacoes.add(auxVet);
        }
    }
    public void ciclosPermutacao(){
        List<int[]> permutacoes = new ArrayList<int[]>();

        int[] options = new int[this.numVertices];
        for(int i=0; i<this.numVertices; i++)
            options[i] = i;

        int[] vet;
        //gera todas as permutaçoes possiveis
        for(int i = 3; i <= numVertices; i++){
            vet = new int[i];
            permutacao(vet, options, 0, permutacoes);
        }

        int[] aux;
        boolean flag;
        //remove aquelas que não sao ciclos
        for(int i = 0; i < permutacoes.size(); i++){
            vet = permutacoes.get(i);
            for(int j = 0; j < vet.length; j++){

                aux = la.adjacentesTo(vet[j]);
                flag = false;

                for(int adj : aux){
                    flag = flag || adj == vet[(j+1)%vet.length];
                }

                if(!flag){
                    permutacoes.remove(i);
                    i--;
                    j += vet.length;
                }

            }
        }
        //remove permutaçoes que represemtam o mesmo ciclo
        for(int i = 0; i < permutacoes.size(); i++){
            vet = permutacoes.get(i);
            for(int j = i+1; j < permutacoes.size(); j++){
                flag = itsPermutation(vet, permutacoes.get(j));
                if(flag){
                    permutacoes.remove(j);
                    j--;
                }
            }
        }

        while(!permutacoes.isEmpty()){
            vet = permutacoes.remove(0);
            for(int i : vet)
                if(i != -1)
                    System.out.print(i + " ");
            System.out.println("");
        }
    }
}