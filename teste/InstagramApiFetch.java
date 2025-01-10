package teste;
import java.io.*;
import java.net.http.*;
import java.net.URI;
import java.util.*;
import org.json.*; // Use uma biblioteca JSON, como org.json

public class InstagramApiFetch {

    private static final String ACCESS_TOKEN = "seu_token_de_acesso"; // Substitua pelo seu token
    private static final String BASE_URL = "https://graph.instagram.com/";

    public static void main(String[] args) {
        // Arquivos de saída para seguidores e seguindo
        String followersFile = "file1.txt";
        String followingFile = "file2.txt";

        try {
            // Obter seguidores e seguindo pela API
            List<String> followers = fetchFollowers();
            List<String> following = fetchFollowing();

            // Escrever dados nos arquivos
            writeToFile(followersFile, followers);
            writeToFile(followingFile, following);

            System.out.println("Dados salvos com sucesso nos arquivos:");
            System.out.println("Seguidores: " + followersFile);
            System.out.println("Seguindo: " + followingFile);
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao acessar a API ou salvar os dados: " + e.getMessage());
        }
    }

    // Método para obter seguidores
    private static List<String> fetchFollowers() throws IOException, InterruptedException {
        // Exemplo de endpoint fictício (substitua pelo endpoint correto da API)
        String url = BASE_URL + "me/followed_by?access_token=" + ACCESS_TOKEN;
        return fetchFromApi(url);
    }

    // Método para obter seguindo
    private static List<String> fetchFollowing() throws IOException, InterruptedException {
        // Exemplo de endpoint fictício (substitua pelo endpoint correto da API)
        String url = BASE_URL + "me/following?access_token=" + ACCESS_TOKEN;
        return fetchFromApi(url);
    }

    // Método genérico para buscar dados da API e convertê-los em lista
    private static List<String> fetchFromApi(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new IOException("Falha ao acessar API: " + response.body());
        }

        // Parse JSON de resposta
        JSONObject jsonResponse = new JSONObject(response.body());
        JSONArray dataArray = jsonResponse.getJSONArray("data");

        List<String> names = new ArrayList<>();
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject user = dataArray.getJSONObject(i);
            names.add(user.getString("username"));
        }

        return names;
    }

    // Método para salvar a lista em um arquivo
    private static void writeToFile(String filePath, List<String> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String entry : data) {
                writer.write(entry);
                writer.newLine();
            }
        }
    }
}
