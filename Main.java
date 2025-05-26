import java.util.*;
import java.io.File;
import java.io.IOException;

public class Main {
public static void main(String[] args) {
            int op = 1;
            boolean readed = false;
            Scanner sc = new Scanner(System.in);
            ArrayList<Game> games = null;
            Dataset dataset = null;
            while(op != 0) {
                System.out.println("""
                0. Salir del sistema
                1. Escanear archivo y crear dataset con los datos
                2. Probar los algoritmos de ordenamiento con el archivo seleccionado según criterio
                3. Utilizar getters del sistema dataset
                4. Leer los juegos en el dataset
                """
                );
                op = sc.nextInt();
                switch(op) {
                    case 0:
                        sc.close();
                        break;
                    case 1:
                        System.out.printf("Seleccione el archivo: \n" +
                            "1 Archivo de 100 datos \n" +
                            "2 Archivo de 10000 datos \n" +
                            "3 Archivo de 1000000 datos \n "
                        );
                        int op2 = 0;
                        op2 = sc.nextInt();
                            switch(op2) {
                                case 1:
                                    games = loadGamesFromFile("games_100.csv");
                                    dataset = new Dataset(games, "none");
                                    readed = true;
                                    break;
                                case 2:
                                    games = loadGamesFromFile("games_10000.csv");
                                    dataset = new Dataset(games, "none");
                                    readed = true;
                                    break;
                                case 3:
                                    games = loadGamesFromFile("games_1000000.csv");
                                    dataset = new Dataset(games, "none");
                                    readed = true;
                                    break;
                                default:
                                    System.out.println("\n Opcion invalida \n");
                                break;
                                }
                        break;
                case 2:
                    if(!readed) {
                        System.out.println("\n No se a leido ningun archivo \n");
                        break;
                    }
                    System.out.printf("1 Probar con todos los algortimos de ordenamiento \n" +
                        "2 Probar solo con los algoritmos O( n*log(n) )\n"
                    );
                    int op3 = 0;
                    op3 = sc.nextInt();
                    System.out.println("\n Seleccione por el criterio que desea ordenar: los disponibles son: " +
                            "(categoria,calidad,precio) \n");
                    String criterio = sc.next();
                    switch (op3) {
                        case 1:
                            String[] algorithms = {"bubbleSort", "insertionSort", "selectionSort", "mergeSort",
                            "quickSort","collectionSort"};
                            for (String algo : algorithms) {
                                long startTime = System.nanoTime();
                                dataset.sortByAlgorithm(algo, criterio);
                                long elapsed = System.nanoTime() - startTime;
                                System.out.println(algo + " took " + elapsed / 1000000 + " ms \n");
                            }
                       break;
                       case 2:
                           String[] algorithms1 = {"mergeSort","collectionSort","quickSort"};
                           for (String algo : algorithms1) {
                               long startTime = System.nanoTime();
                               dataset.sortByAlgorithm(algo, criterio);
                               long elapsed = System.nanoTime() - startTime;
                               System.out.println(algo + " took " + elapsed / 1000000 + " ms \n");
                           }
                           break;
                        default:
                        System.out.println("\n Opcion invalida \n");
                        break;
                    }
                break;
                case 3:
                    if(!readed) {
                        System.out.println("\n No se a leido ningun archivo \n");
                        break;
                    }
                    System.out.printf("\n getters del sistema dataset \n " +
                        "1: getGamesbyPrice(int precio) \n" +
                        "2: getGamesbyPriceRange(int min,int max) \n " +
                        "3: getGamesbyCategory(String category) \n " +
                        "4: getGamesbyQuality(int calidad) \n"
                    );
                int op4 = 0;
                op4 = sc.nextInt();
                int z=1;
                ArrayList<Game> result = null;
                switch (op4){
                case 1:
                        System.out.println("\n Inserte el precio a buscar \n");
                        int precio = sc.nextInt();
                        result = dataset.getGamesByPrice(precio);
                        System.out.println("cantidad de resultados: " + result.size() + "\n \n");
                        z = 1;
                        for (Game g : result) {
                            System.out.printf("%d. %s %n", z, g.toString());
                            z++;
                        }
                        System.out.println("\n \n");
                        break;
                case 2:
                    System.out.println("\n Inserte el precio minimo\n");
                    int min = sc.nextInt();
                    System.out.println("\n Inserte el precio maximo\n");
                    int max = sc.nextInt();
                    result = dataset.getGamesByPriceRange(min,max);
                    System.out.println("cantidad de resultados: " + result.size() + "\n \n");
                    z = 1;
                    for (Game g : result) {
                        System.out.printf("%d. %s %n", z, g.toString());
                        z++;
                    }
                    System.out.println("\n \n");
                    break;
                case 3:
                    System.out.println("\n Inserte la categoria a buscar (Accion, Aventura, Estrategia, RPG, Deportes, Simulacion)\n");
                    sc.nextLine();
                    String Categoria = sc.next();
                    result = dataset.getGamesByCategory(Categoria);
                    System.out.println("cantidad de resultados: " + result.size() + "\n \n");
                    z= 1;
                    for (Game g : result) {
                        System.out.printf("%d. %s %n", z, g.toString());
                        z++;
                    }
                    System.out.println("\n \n");
                    break;
                case 4:
                    System.out.println("\n Inserte la calidad a buscar\n");
                    int calidad = sc.nextInt();
                    result = dataset.getGamesByQuality(calidad);
                    System.out.println("cantidad de resultados: " + result.size() + "\n \n");
                    z = 1;
                    for (Game g : result) {
                        System.out.printf("%d. %s %n", z, g.toString());
                        z++;
                    }
                    System.out.println("\n \n");
                    break;
                 default:
                    System.out.println("\n Opcion invalida \n");
                    break;
                 }
                 break;
                    case 4:
                        if(!readed) {
                            System.out.println("\n No se ha leido ningun archivo \n");
                            break;
                        }
                        System.out.println("La cantidad de datos que hay es: " + dataset.getData().size()+"\n");
                        System.out.print("\n");
                        result = dataset.getData();
                        z = 1;
                        for (Game g : result) {
                            System.out.printf("%d. %s %n", z, g.toString());
                            z++;
                        }
                        System.out.print("\n");
                        break;
                default:
                    System.out.println("\n Opcion invalida \n");
                break;
            }
        }
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
            System.out.println("\n Archivo encontrado y leido correctamente \n");
        } catch (IOException e) {
            System.err.println("\n Error al leer archivo: " + e.getMessage() + "\n");
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
                System.out.printf("Dataset ordenado por precio, utilizando busqueda binaria! \n");
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
                System.out.printf("Dataset no esta ordenado por precio, utilizando busqueda lineal! \n");
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
                System.out.printf("Dataset ordenado por precio, utilizando busqueda binaria! \n");
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
                System.out.printf("Dataset no esta ordenado por precio, utilizando busqueda lineal! \n");
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
                System.out.printf("Dataset esta ordenado por categoria, utilizando busqueda binaria! \n");
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
                System.out.printf("Dataset no esta ordenado por categoria, utilizando busqueda lineal! \n");
                for (Game g : data) {
                    if ((g.getCategoria().toLowerCase()).equals(categoria.toLowerCase())) {
                        result.add(g);
                    }
                }
            }
            return result;
        }

        public ArrayList<Game> getGamesByQuality(int calidad) {
            ArrayList<Game> result = new ArrayList<>();
            if (sortedByAttribute.equals("calidad")) {
                System.out.printf("Dataset esta ordenado por calidad, utilizando busqueda binaria! \n");
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
                System.out.printf("Dataset no esta ordenado por calidad, utilizando busqueda lineal! \n");
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
                    System.out.println("Sorting with Bubble Sort \n");
                    bubbleSort(comparator);
                    break;
                case "insertionsort":
                    System.out.println("Sorting with Insertion Sort \n");
                    insertionSort(comparator);
                    break;
                case "selectionsort":
                    System.out.println("Sorting with Selection Sort \n");
                    selectionSort(comparator);
                    break;
                case "mergesort":
                    System.out.println("Sorting with Merge Sort \n");
                    data = mergeSort(data, comparator);
                    break;
                case "quicksort":
                    System.out.println("Sorting with Quick Sort \n");
                    quickSortIterative(comparator);
                    break;
                default:
                    System.out.println("Sorting with Default Java Sort \n");
                    Collections.sort(data, comparator);
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
            ArrayList<Game> merged = new ArrayList<>();
            int i = 0, j = 0;

            while (i < left.size() && j < right.size()) {
                if (comp.compare(left.get(i), right.get(j)) <= 0) {
                    merged.add(left.get(i++));
                } else {
                    merged.add(right.get(j++));
                }
            }

            while (i < left.size()) merged.add(left.get(i++));
            while (j < right.size()) merged.add(right.get(j++));

            return merged;
        }

        private void quickSortIterative(Comparator<Game> comp) {
            Stack<int[]> stack = new Stack<>();
            stack.push(new int[]{0, data.size() - 1});

            while (!stack.isEmpty()) {
                int[] range = stack.pop();
                int low = range[0], high = range[1];
                if (low < high) {
                    int pivotIndex = partition(data, low, high, comp);
                    stack.push(new int[]{pivotIndex + 1, high});
                    stack.push(new int[]{low, pivotIndex - 1});
                }
            }
        }

        private int partition(ArrayList<Game> arr, int low, int high, Comparator<Game> comp) {
            Game pivot = arr.get(high);
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (comp.compare(arr.get(j), pivot) <= 0) {
                    i++;
                    Collections.swap(arr, i, j);
                }
            }
            Collections.swap(arr, i + 1, high);
            return i + 1;
        }
    }
}