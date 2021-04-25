import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main{
    public static void main(String []args){
        try {
            File file = new File("teste.txt");
            Scanner scanner = new Scanner(file);
            String numVertices = scanner.nextLine();
            String numArestas = scanner.nextLine();
            String[] arestas = new String[Integer.parseInt(numArestas)];

            for(int i = 0; i < Integer.parseInt(numArestas); i++)
                arestas[i] = scanner.nextLine();

            scanner.close();

            Grafo grafo = new Grafo(Integer.parseInt(numArestas),Integer.parseInt(numVertices), arestas, false);
            grafo.ciclos();
            System.out.println("=====================");
            grafo.ciclosPermutacao();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}