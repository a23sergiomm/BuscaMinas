package principal;
import buscaminas.BuscaMinas;
import java.util.Scanner;

public class Interface {
    private BuscaMinas game;
    private Scanner scanner;

    public Interface(int size, int mines) {
        this.game = new BuscaMinas(size, mines);
        this.scanner = new Scanner(System.in);
    }

    public void startMenu() {
        while (true) {
            System.out.println(" ___________________________");
            System.out.println("|  Selecciona una opción:   |");
            System.out.println("|        1. Jugar           |");
            System.out.println("|        2. Salir           |");
            System.out.println("|___________________________|");
            game.start();
            while (!scanner.hasNextInt()) {
                System.out.println("Eso no es un número entero. Inténtalo de nuevo.");
                scanner.next(); // descartamos los valores que no sean enteros
            }
            int option = scanner.nextInt();

            if (option == 1) {
                playGame();
            } else if (option == 2) {
                System.out.println("Gracias por jugar. ¡Hasta la próxima!");
                System.exit(0);
            } else {
                System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    public void playGame() {
        while (!game.isGameOver()) {
            printtablero();
            System.out.println("Selecciona la accion [1 (Revelar), 2 (Marcar/Desmarcar]:");
            do {
                while (!scanner.hasNextInt()) {
                    System.out.println("Eso no es un numero entero. Intentalo de nuevo.");
                    System.out.println("Selecciona la accion [1 (Revelar), 2 (Marcar/Desmarcar]:");
                    scanner.next(); // decartamos los numeros que no sean enteros
                }
            } while (!scanner.hasNextInt());
            int action = scanner.nextInt();
            int x, y;

            do {
                System.out.println("Introduce las coordenadas separadas por un espacio (x, y):");
                while (!scanner.hasNextInt()) {
                    System.out.println("Eso no es un numero entero. Intentalo de nuevo.");
                    System.out.println("Introduce las coordenadas separadas por un espacio (x, y):");
                    scanner.next(); // decartamos los numeros que no sean enteros
                }
                x = scanner.nextInt();
                y = scanner.nextInt();
            } while (x < 0 || x >= game.getTableroSize() || y < 0 || y >= game.getTableroSize());

            if (action == 1) {
                if (game.reveal(x, y)) {
                    game.isGameOver();

                    System.out.println("\u001B[31m" + " ____    _____    _____     ___    __      __      __");
                    System.out.println("|  _ \\  /     \\  /     \\   |   \\_/   |    |  |    |  |");
                    System.out.println("| |_) | |  |  |  |  |  |   | |\\   /| |    |  |    |  |");
                    System.out.println("|  _ <  |  |  |  |  |  |   | | \\_/ | |    |  |    |  |");
                    System.out.println("| |_) | |  |  |  |  |  |   | |     | |    |__|    |__| ");
                    System.out.println("|____/  \\_____/  \\_____/   |_|     |_|    |__|    |__|" + "\u001B[0m");

                    System.out.println("Has explotado una mina. Fin del juego.");
                    String elapsedTime = game.Contandor();
                    System.out.println("Tiempo transcurrido: " + elapsedTime);
                    game.mostrarMinas();
                    printtablero();
                    game.reset();

                    return;
                } else {
                    if (game.playerWon() == true) {
                        System.out.println("\u001B[33m" + " __        __  __   ____    __ ");
                        System.out.println(" \\ \\      / / |__| |    \\   | |");
                        System.out.println("  \\ \\ /\\ / /  |  | |  |\\ \\  | |");
                        System.out.println("   \\ V  V /   |  | |  | \\ \\_| |");
                        System.out.println("    \\_/\\_/    |__| |__|  \\____|" + "\u001B[0m");
                        String elapsedTime = game.Contandor();
                        System.out.println("Tiempo transcurrido: " + elapsedTime);
                        game.reset();
                    } else {
                        System.out.println("Has revelado una casilla. Continua jugando.");
                    }
                }
            } else if (action == 2) {
                game.mark(x, y);
            }
        }
        printtablero();
    }

    private void printtablero() {
        // Imprime la primera fila con los números de las columnas
        System.out.print("   ");
        for (int j = 0; j < game.getTableroSize(); j++) {
            System.out.print("\u001B[33m"+j+" "+"\u001B[0m");
        }
        System.out.println();
        
        for (int i = 0; i < game.getTableroSize(); i++) {
            System.out.print("\u001B[33m"+i + "- "+"\u001B[0m");
            for (int j = 0; j < game.getTableroSize(); j++) {
                if (game.isMarcadas(i, j)) {
                    System.out.print("F ");
                } else if (game.isReveladas(i, j)) {
                    int cell = game.getCell(i, j);
                    if (cell == -1) {
                        System.out.print("\u001B[32m" + "M " + "\u001B[0m");
                    } else if (cell == 1) {
                        System.out.print("\u001B[32m" + cell + " \u001B[0m"); // Verde
                    } else if (cell == 2) {
                        System.out.print("\u001B[34m" + cell + " \u001B[0m"); // Azul
                    } else if (cell == 3) {
                        System.out.print("\u001B[31m" + cell + " \u001B[0m"); // Rojo
                    } else if (cell == 4) {
                        System.out.print("\u001B[35m" + cell + " \u001B[0m"); // Morado
                    } else {
                        System.out.print(cell + " ");
                    }
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Interface gameInterface = new Interface(10, 10);
        gameInterface.startMenu();
    }
}