package teste;
import java.io.*;
import java.util.*;

public class CompareTxtFiles {
    public static void main(String[] args) {
        // Caminhos dos arquivos
        String file1Path = "./infos/seguidores.txt";
        String file2Path = "./infos/seguindo.txt";

        try {
            // Ler os nomes dos dois arquivos em conjuntos
            Set<String> namesInFile1 = readFileLines(file1Path);
            Set<String> namesInFile2 = readFileLines(file2Path);

            // Encontra nomes que estão apenas no primeiro arquivo
            Set<String> onlyInFile1 = new HashSet<>(namesInFile1);
            onlyInFile1.removeAll(namesInFile2);

            // Encontra nomes que estão apenas no segundo arquivo
            Set<String> onlyInFile2 = new HashSet<>(namesInFile2);
            onlyInFile2.removeAll(namesInFile1);

            // Exibe os resultados
            System.out.println("Nomes apenas no primeiro arquivo:");
            onlyInFile1.forEach(System.out::println);

            System.out.println("\nNomes apenas no segundo arquivo:");
            onlyInFile2.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Erro ao ler os arquivos: " + e.getMessage());
        }
    }

    // Método para ler as linhas de um arquivo para um conjunto
    private static Set<String> readFileLines(String filePath) throws IOException {
        Set<String> lines = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim()); // Remove espaços extras
            }
        }
        return lines;
    }
}
