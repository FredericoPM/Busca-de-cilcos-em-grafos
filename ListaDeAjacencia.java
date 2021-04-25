public class ListaDeAjacencia{
    int numArestas;
    int numVertices;
    boolean direcionado;
    int[] vertices;
    int[] adjacentes;

    private int[] formatData(String data){
        int[] attechedEdges = new int[2];
        String[] attechedEdgesStr;
        attechedEdgesStr = data.split(",");
        attechedEdgesStr[0] = attechedEdgesStr[0].replaceAll("[{}() ]", "");
        attechedEdgesStr[1] = attechedEdgesStr[1].replaceAll("[{}() ]", "");
        attechedEdges[0] = Integer.parseInt(attechedEdgesStr[0]);
        attechedEdges[1] = Integer.parseInt(attechedEdgesStr[1]);
        return attechedEdges;
    }
    private void addAresta(int origem, int destino){
        for(int i = this.direcionado ? this.numArestas-1 : (this.numArestas*2)-1; i >= vertices[origem] && i > 0; i--)
            adjacentes[i] = adjacentes[i-1];

        adjacentes[vertices[origem]] = destino;

        for(int i = origem; i < this.numVertices; i++)
            vertices[i]++;
    }
    public ListaDeAjacencia(int numArestas, int numVertices, String[] arestas, boolean direcionado){
        this.direcionado = direcionado;
        this.numVertices = numVertices;
        this.numArestas = numArestas;
        this.vertices = new int[numVertices];
        this.adjacentes = this.direcionado ? new int[this.numArestas] : new int[2*this.numArestas];

        int[] attechedEdges;
        for(String aresta : arestas){
            attechedEdges = formatData(aresta);
            addAresta(attechedEdges[0], attechedEdges[1]);
            if(!this.direcionado){
                addAresta(attechedEdges[1], attechedEdges[0]);
            }
        }
    }
    public void print(){
        for(int v : this.vertices)
            System.out.print(v + " ");
        System.out.println();
        for(int a : this.adjacentes)
            System.out.print(a + " ");
    }
    public int[] adjacentesTo(int origem){
        int[] resultado = null;
        if(origem < (direcionado ? numArestas : 2*numArestas)){
            int fim = this.vertices[origem];
            int inicio = origem == 0 ? 0 : this.vertices[origem-1];
            resultado = new int[fim-inicio];
            for(int i = inicio, j = 0; i < fim; i++, j++){
                resultado[j] = this.adjacentes[i];
            }
        }
        return resultado;
    }
}