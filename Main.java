import java.util.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Cargar datos desde archivo
        ArrayList<Game> games = loadGamesFromFile("games_1000000.csv");
        // Crear dataset
        Dataset dataset = new Dataset(games, "none");

        long startTime = System.nanoTime();
        dataset.getGamesByCategory("Accion");
        long elapsed = System.nanoTime() - startTime;
        System.out.println("getting games by linear search took " + elapsed / 100000  + " ms");

        ArrayList<Game> copy = new ArrayList<>(games);
        Dataset ds = new Dataset(copy, "none");
        ds.sortByAlgorithm("", "categoria");
        long startTime1 = System.nanoTime();
        ds.getGamesByCategory("Accion");
        long elapsed1 = System.nanoTime() - startTime1;

        System.out.println("getting games by binary search took "+ elapsed1 / 1000000 + " ms");
        //String[] algorithms = {"bubbleSort", "insertionSort", "selectionSort", "mergeSort", "quickSort","collectionSort"};
        //String[] algorithms = {"mergeSort","collectionSort","quickSort"};
        /*for (String algo : algorithms) {
            ArrayList<Game> copy = new ArrayList<>(games);
            Dataset ds = new Dataset(copy, "none");

            long startTime = System.nanoTime();
            ds.sortByAlgorithm(algo, "categoria");
            long elapsed = System.nanoTime() - startTime;

            System.out.println(algo + " took " + elapsed / 1000000 + " ms");
        }*/

    }
    //Metodo adicional para cargar los juegos
    public static ArrayList<Game> loadGamesFromFile(String filename) {
        ArrayList<Game> games = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            scanner.nextLine(); // saltar encabezado
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                String name = parts[0];
                String category = parts[1];
                int quality = Integer.parseInt(parts[2]);
                int price = Integer.parseInt(parts[3]);
                games.add(new Game(name, category, quality, price));
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
        return games;
    }

    protected static class Game {
        private String nombre, categoria;
        private int calidad, precio;

        public Game(String nombre, String categoria, int calidad, int precio) {
            this.nombre = nombre;
            this.categoria = categoria;
            this.calidad = calidad;
            this.precio = precio;
        }

        public String toString() {
            return "Nombre: " + nombre + ", Categoria: " + categoria + ", Calidad: " + calidad + ", Precio: " + precio;
        }

        public String getNombre() {
            return nombre;
        }

        public String getCategoria() {
            return categoria;
        }

        public int getCalidad() {
            return calidad;
        }

        public int getPrecio() {
            return precio;
        }
    }

    protected static class Dataset {
        private ArrayList<Game> data;
        private String sortedByAttribute;

        public Dataset(ArrayList<Game> data, String sortedBy) {
            this.data = data;
            this.sortedByAttribute = sortedBy;
        }

        public ArrayList<Game> getData() {
            return data;
        }

        public String getSortedByAttribute() {
            return sortedByAttribute;
        }

        public ArrayList<Game> getGamesByPrice(int price) {
            ArrayList<Game> result = new ArrayList<>();
            if (sortedByAttribute.equals("precio")) {
                int left = 0, right = data.size() - 1;
                while (left <= right) {
                    int mid = (left + right) / 2;
                    int midPrice = data.get(mid).getPrecio();
                    if (midPrice == price) {
                        int i = mid - 1;
                        while (i >= 0 && data.get(i).getPrecio() == price) {
                            result.add(data.get(i));
                            i--;
                        }
                        result.add(data.get(mid));
                        i = mid + 1;
                        while (i < data.size() && data.get(i).getPrecio() == price) {
                            result.add(data.get(i));
                            i++;
                        }
                        break;
                    } else if (midPrice < price) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            } else {
                for (Game g : data) {
                    if (g.getPrecio() == price) {
                        result.add(g);
                    }
                }
            }
            return result;
        }

        public ArrayList<Game> getGamesByPriceRange(int min, int max) {
            ArrayList<Game> result = new ArrayList<>();
            if (sortedByAttribute.equals("precio")) {
                int left = 0, right = data.size() - 1;
                int startIndex = -1;

                while (left <= right) {
                    int mid = (left + right) / 2;
                    int midPrice = data.get(mid).getPrecio();

                    if (midPrice >= min) {
                        startIndex = mid;
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }

                if (startIndex != -1) {
                    for (int i = startIndex; i < data.size(); i++) {
                        int price = data.get(i).getPrecio();
                        if (price > max) break;
                        result.add(data.get(i));
                    }
                }
            } else {
                for (Game g : data) {
                    if (g.getPrecio() >= min && g.getPrecio() <= max) {
                        result.add(g);
                    }
                }
            }
            return result;
        }

        public ArrayList<Game> getGamesByCategory(String categoria) {
            ArrayList<Game> result = new ArrayList<>();

            if (sortedByAttribute.equals("categoria")) {
                int left = 0, right = data.size() - 1;
                int startIndex = -1;

                while (left <= right) {
                    int mid = (left + right) / 2;
                    String midCat = data.get(mid).getCategoria();

                    int comparison = midCat.compareTo(categoria);
                    if (comparison == 0) {
                        startIndex = mid;
                        right = mid - 1;
                    } else if (comparison < 0) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }

                if (startIndex != -1) {
                    for (int i = startIndex; i < data.size(); i++) {
                        String cat = data.get(i).getCategoria();
                        if (!cat.equals(categoria)) {
                            break;
                        } else {
                            result.add(data.get(i));
                        }
                    }
                }
            } else {
                for (Game g : data) {
                    if (g.getCategoria().equals(categoria)) {
                        result.add(g);
                    }
                }
            }
            return result;
        }

        public ArrayList<Game> getGamesByQuality(int calidad) {
            ArrayList<Game> result = new ArrayList<>();
            if (sortedByAttribute.equals("calidad")) {
                int left = 0, right = data.size() - 1;
                while (left <= right) {
                    int mid = (left + right) / 2;
                    int midCalidad = data.get(mid).getCalidad();
                    if (midCalidad == calidad) {
                        int i = mid - 1;
                        while (i >= 0 && data.get(i).getCalidad() == calidad) {
                            result.add(data.get(i));
                            i--;
                        }
                        result.add(data.get(mid));
                        i = mid + 1;
                        while (i < data.size() && data.get(i).getCalidad() == calidad) {
                            result.add(data.get(i));
                            i++;
                        }
                        break;
                    } else if (midCalidad < calidad) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            } else {
                for (Game g : data) {
                    if (g.getCalidad() == (calidad)) {
                        result.add(g);
                    }
                }
            }
            return result;
        }

        public void sortByAlgorithm(String algorithm, String attribute) {
            Comparator<Game> comparator;

            // Determinar el comparador según el atributo
            switch (attribute.toLowerCase()) {
                case "categoria":
                    comparator = Comparator.comparing(Game::getCategoria);
                    break;
                case "calidad":
                    comparator = Comparator.comparingInt(Game::getCalidad);
                    break;
                case "precio":
                default:
                    comparator = Comparator.comparingInt(Game::getPrecio);
                    attribute = "precio";
                    break;
            }


            // Aplicar el algoritmo de ordenamiento elegido
            switch (algorithm.toLowerCase()) {
                case "bubblesort":
                    bubbleSort(comparator);
                    System.out.println("Sorted by: Bubble Sort, successful");
                    break;
                case "insertionsort":
                    insertionSort(comparator);
                    System.out.println("Sorted by: Insertion Sort, successful");
                    break;
                case "selectionsort":
                    selectionSort(comparator);
                    System.out.println("Sorted by: Selection Sort, successful");
                    break;
                case "mergesort":
                    data = mergeSort(data, comparator);
                    System.out.println("Sorted by: Merge Sort, successful");
                    break;
                case "quicksort":
                    quickSort(0, data.size() - 1, comparator);
                    System.out.println("Sorted by: Quick Sort, successful");
                    break;
                default:
                    Collections.sort(data, comparator);
                    System.out.println("Sorted by: Default Java Sort, successful");
                    break;
            }
            sortedByAttribute = attribute;
        }

        private void bubbleSort(Comparator<Game> comp) {
            int n = data.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (comp.compare(data.get(j), data.get(j + 1)) > 0) {
                        Collections.swap(data, j, j + 1);
                    }
                }
            }
        }

        private void insertionSort(Comparator<Game> comp) {
            int n = data.size();
            for (int i = 1; i < n; i++) {
                Game key = data.get(i);
                int j = i - 1;
                while (j >= 0 && comp.compare(data.get(j), key) > 0) {
                    data.set(j + 1, data.get(j));
                    j--;
                }
                data.set(j + 1, key);
            }
        }

        private void selectionSort(Comparator<Game> comp) {
            int n = data.size();
            for (int i = 0; i < n - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < n; j++) {
                    if (comp.compare(data.get(j), data.get(minIndex)) < 0) {
                        minIndex = j;
                    }
                }
                if (minIndex != i) {
                    Collections.swap(data, i, minIndex);
                }
            }
        }

        private ArrayList<Game> mergeSort(ArrayList<Game> list, Comparator<Game> comp) {
            if (list.size() <= 1) return list;

            int mid = list.size() / 2;
            ArrayList<Game> left = mergeSort(new ArrayList<>(list.subList(0, mid)), comp);
            ArrayList<Game> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())), comp);
            return merge(left, right, comp);
        }

        private ArrayList<Game> merge(ArrayList<Game> left, ArrayList<Game> right, Comparator<Game> comp) {
            ArrayList<Game> result = new ArrayList<>();
            int i = 0, j = 0;

            while (i < left.size() && j < right.size()) {
                if (comp.compare(left.get(i), right.get(j)) <= 0) {
                    result.add(left.get(i));
                    i++;
                } else {
                    result.add(right.get(j));
                    j++;
                }
            }

            while (i < left.size()) {
                result.add(left.get(i));
                i++;
            }
            while (j < right.size()) {
                result.add(right.get(j));
                j++;
            }

            return result;
        }

        private void quickSort(int low, int high, Comparator<Game> comp) {
            if (low < high) {
                int pi = partition(low, high, comp);
                quickSort(low, pi - 1, comp);
                quickSort(pi + 1, high, comp);
            }
        }

        private int partition(int low, int high, Comparator<Game> comp) {
            Game pivot = data.get(high);
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (comp.compare(data.get(j), pivot) <= 0) {
                    i++;
                    Collections.swap(data, i, j);
                }
            }
            Collections.swap(data, i + 1, high);
            return i + 1;
        }
    }
}