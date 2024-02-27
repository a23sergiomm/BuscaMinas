package buscaminas;
import java.util.Random;

public class BuscaMinas {
    private int[][] tablero;
    private boolean[][] reveladas;
    private boolean[][] marcadas;
    private int minas;
    private long startTime;
    
    public BuscaMinas(int size, int minas) {
        this.tablero = new int[size][size];
        this.reveladas = new boolean[size][size];
        this.marcadas = new boolean[size][size];
        this.minas = minas;
        generateMinas();
        calculateMinas();
    }


    private void generateMinas() {
        startTime = System.currentTimeMillis();
        Random rand = new Random();
        int minasToPlace = minas;

        while (minasToPlace > 0) {
            int x = rand.nextInt(tablero.length);
            int y = rand.nextInt(tablero[0].length);

            if (tablero[x][y] != -1) {
                tablero[x][y] = -1;
                minasToPlace--;
            }
        }
    }

    private void calculateMinas() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] != -1) {
                    int minasCount = 0;

                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if (i + dx >= 0 && i + dx < tablero.length && j + dy >= 0 && j + dy < tablero[0].length) {
                                if (tablero[i + dx][j + dy] == -1) {
                                    minasCount++;
                                }
                            }
                        }
                    }
                    tablero[i][j] = minasCount;
                }
            }
        }
    }

    public boolean reveal(int x, int y) {
        if (x < 0 || x >= tablero.length || y < 0 || y >= tablero[0].length) {
                return false;
            }
    
        if (reveladas[x][y]) {
                return false;
            }
    
           reveladas[x][y] = true;
    
        if (tablero[x][y] == -1) {
                return true;
            }
    
        if (tablero[x][y] == 0) {
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        reveal(x + dx, y + dy);
                    }
                }
            }
        return false;
    }

    public boolean mostrarMinas(){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == -1) {
                    reveladas[i][j] = true;
                }
            }
        }
        return true;
    }
    
    public boolean isGameOver() {
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero[0].length; j++) {
                    if (tablero[i][j] != -1 && !reveladas[i][j]) {
                        return false;   
                    }
                }
            }
            return true;
    }
    

    public boolean playerWon() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                // Si la celda no es una mina y aún no ha sido revelada, el jugador aún no ha ganado
                if (tablero[i][j] != -1 && !reveladas[i][j]) {
                    return false;
                }
            }
        }
        // Si todas las celdas que no son minas han sido reveladas, el jugador ha ganado
        return true;
    }
    

    // Inicia el contador de tiempo
    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void reset() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                tablero[i][j] = 0;
                reveladas[i][j] = false;
                marcadas[i][j] = false;
            }
        }
        generateMinas();
        calculateMinas();
    }


    public int getCell(int x, int y) {
        return tablero[x][y];
    }

    public boolean isReveladas(int x, int y) {
        return reveladas[x][y];
    }

    public int getTableroSize() {
        return tablero.length;
    }

    public String Contandor() {
        long milsegundos = System.currentTimeMillis() - startTime;
        long segundos = milsegundos / 1000;
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        long seconds = segundos % 60;
        return horas + "h " + minutos + "m " + seconds + "s";
    }

    public boolean mark(int x, int y) {
        if (x < 0 || x >= tablero.length || y < 0 || y >= tablero[0].length) {
            return false;
        }

        marcadas[x][y] = !marcadas[x][y];
        return true;
    }

    public boolean isMarcadas(int x, int y) {
        return marcadas[x][y];
    }
}