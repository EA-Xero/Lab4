import java.util.*;
import java.io.*;

public class GenerateData {

    private static final String[] WORDS = {"Dragon", "Empire", "Quest", "Galaxy", "Legends", "Warrior", "Shadow", "Blade", "Chronicles", "Revenge"};
    private static final String[] CATEGORIES = {"Acción", "Aventura", "Estrategia", "RPG", "Deportes", "Simulación"};
    private static final Random random = new Random();

    public static ArrayList<Main.Game> generateGames(int n) {
        ArrayList<Main.Game> games = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String name = generateRandomName();
            String category = randomCategory();
            int price = random.nextInt(70_001);  // 0 to 70,000
            int quality = random.nextInt(101);   // 0 to 100

            // Aquí supongo que Game es una clase estática dentro de Main
            Main.Game game = new Main.Game(name, category, quality, price);
            games.add(game);
        }

        return games;
    }

    private static String generateRandomName() {
        String word1 = WORDS[random.nextInt(WORDS.length)];
        String word2 = WORDS[random.nextInt(WORDS.length)];
        return word1 + word2;
    }

    private static String randomCategory() {
        return CATEGORIES[random.nextInt(CATEGORIES.length)];
    }

    public static void saveGamesToFile(ArrayList<Main.Game> games, String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("nombre,categoria,calidad,precio");
            for (Main.Game game : games) {
                writer.printf("%s,%s,%d,%d%n",
                        game.getNombre(),
                        game.getCategoria(),
                        game.getCalidad(),
                        game.getPrecio());
            }
            System.out.println("Archivo guardado: " + filename);
        } catch (IOException e) {
            System.err.println("Error al guardar archivo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int[] sizes = {100, 10_000, 1_000_000};
        String[] filenames = {"games_100.csv", "games_10000.csv", "games_1000000.csv"};

        for (int i = 0; i < sizes.length; i++) {
            System.out.println("Generando conjunto de tamaño " + sizes[i] + "...");
            ArrayList<Main.Game> games = generateGames(sizes[i]);
            saveGamesToFile(games, filenames[i]);
        }

        System.out.println("¡Todos los conjuntos generados y guardados!");
    }
}
