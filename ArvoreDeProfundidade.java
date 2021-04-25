import java.util.*;
public class ArvoreDeProfundidade{
    int numArestas;
    int numVertices;

    int[][] arestas;
    char[] type;

    int[] vertices;
    int[][] tempo;

    private boolean itsPresentVertice(int desejado) {
        boolean result = false;
        for(int vertice : this.vertices)
            result = result || vertice == desejado;
        return result;
    }
    private int searchVertice(int desejado){
        int result = -1;
        for(int i = 0; i < this.numVertices; i++)
            if(this.vertices[i] == desejado)
                result = i;
        return result;
    }
    private boolean itsPresentAresta(int v1, int v2) {
        boolean result = false;
        for(int i = 0; i < this.numArestas; i++)
            result = result || (v1 == arestas[i][0] && v2 == arestas[i][1]) || (v2 == arestas[i][0] && v1 == arestas[i][1]);
        return result;
    }

    public ArvoreDeProfundidade(ListaDeAjacencia la){
        Stack<Integer> pilha = new Stack<Integer>();

        this.numArestas = la.numArestas;
        this.arestas = new int[this.numArestas][2];
        this.type = new char[this.numArestas];

        for(int i = 0; i < this.numArestas; i++){
            this.arestas[i][0] = -1;
            this.arestas[i][1] = -1;
        }

        this.numVertices = la.numVertices;
        this.vertices = new int[this.numVertices];
        this.tempo = new int[this.numVertices][2];

        for(int i=0; i < numVertices; i++){
            this.vertices[i] = -1;
            this.tempo[i][0] = -1;
            this.tempo[i][1] = -1;
        }
        //algoritimo de caminhamento em profundidade
        int tick = 0;
        int vertObs = 0;
        int aresObs = 0;
        boolean done = false;
        char type;

        while(!done){
            //caso não esteja percorrendo nenhum caminho
            if(pilha.empty()){
                //procura por um vertice que ainda não tenha sido checado e então o adiciona na pilha para que comece a ser percorrido
                for(int i = 0; i < numVertices; i++){
                    if(!itsPresentVertice(i)){
                        pilha.push(i);
                        vertices[vertObs] = i;
                        tempo[vertObs][0] = tick;
                        vertObs++;
                        tick++;
                        i += numVertices;
                    }else if(i == numVertices-1){
                        done = true;
                    }
                }
            }else{
                int[] adjacentes = la.adjacentesTo(pilha.peek());
                if(adjacentes == null){
                    tempo[searchVertice(pilha.peek())][1] = tick;
                    pilha.pop();
                    tick++;
                }else{
                    for(int i = 0; i < adjacentes.length; i++){
                        //caso a aresta ainda não tenha sido observada
                        if(!itsPresentAresta(pilha.peek(), adjacentes[i])){
                            //adiciona a aresta a lista de arestas observadas
                            type = 'A';
                            if(itsPresentVertice(adjacentes[i])){
                                type = tempo[searchVertice(adjacentes[i])][1] == -1 ? 'R' : 'C';
                            }
                            this.arestas[aresObs][0] = pilha.peek();
                            this.arestas[aresObs][1] = adjacentes[i];
                            this.type[aresObs] = type;
                            //checa se o vertice ja foi observado caso não tenha o adiciona a pilha/lista de vertices
                            //caso contrario apenas seta o tipo da resta
                            if(!itsPresentVertice(adjacentes[i])){
                                pilha.push(adjacentes[i]);
                                this.vertices[vertObs] = adjacentes[i];
                                this.tempo[vertObs][0] = tick;
                                tick++;
                                vertObs++;
                            }
                            aresObs++;
                            i += adjacentes.length;
                        }else if(i == adjacentes.length-1){
                            //nenhuma aresta ainda não percorrida encontrada
                            tempo[searchVertice(pilha.peek())][1] = tick;
                            pilha.pop();
                            tick++;
                        }
                    }
                }
            }
        }
    }
    public char typeOfAresta(int v1, int v2){
        char result = 'N';
        for(int i = 0; i < numArestas; i++){
            if((arestas[i][0] == v1 && arestas[i][1] == v2) || (arestas[i][0] == v2 && arestas[i][1] == v1))
                result = type[i];
        }
        return result;
    }
    public void print(){
        System.out.println("Arestas:");
        for(int i=0;i<this.numArestas; i++){
            System.out.println("v1: " + this.arestas[i][0] + " | v2: " + this.arestas[i][1] + " | tipo: " + this.type[i]);
        }

        System.out.println("Vertices:");
        for(int i=0;i<this.numVertices; i++){
            System.out.println("v: " + this.vertices[i] + " | entrada: " + this.tempo[i][0] + " | saida: " + this.tempo[i][1]);
        }
    }
}