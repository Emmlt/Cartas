import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    Random r = new Random();

    public void repartir() {
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicion = MARGEN + cartas.length * DISTANCIA;
        for (Carta c : cartas) {
            c.mostrar(pnl, posicion, MARGEN);
            posicion -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";

        int[] contadores = new int[NombreCarta.values().length];
        boolean[] usadas = new boolean[TOTAL_CARTAS];
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            contadores[cartas[i].getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int c : contadores) {
            if (c >= 2) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = "Se encontraron los siguientes grupos:\n";
            for (int i = 0; i < NombreCarta.values().length; i++) {
                if (contadores[i] >= 2) {
                    mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
                    for (int j = 0; j < TOTAL_CARTAS; j++) {
                        if (cartas[j].getNombre().ordinal() == i) {
                            usadas[j] = true; 
                        }
                    }
                }
            }
        }

        for (Pinta pinta : Pinta.values()) {
            for (int i = 0; i < TOTAL_CARTAS - 1; i++) {
                if (!usadas[i] && cartas[i].getPinta() == pinta) {
                    String escaleraMensaje = Grupo.values()[2] + " de " + pinta + " (" + cartas[i].getNombre();
                    int consecutivas = 1;
                    for (int j = i + 1; j < TOTAL_CARTAS; j++) {
                        int diferencia = cartas[j].getNombre().ordinal() - cartas[i].getNombre().ordinal();
                        if (!usadas[j] && cartas[j].getPinta() == pinta && (diferencia == 1 || diferencia == -1)) {
                            usadas[i] = true;
                            usadas[j] = true;
                            escaleraMensaje += ", " + cartas[j].getNombre();
                            consecutivas++;
                        }
                    }
                    if (consecutivas >= 2) {
                        mensaje += escaleraMensaje + ")\n";
                    }
                }
            }
        }

        return mensaje;
    }

    public int calcularPuntaje() {
        boolean[] usadas = new boolean[TOTAL_CARTAS];
        int puntaje = 0;

        int[] contadores = new int[NombreCarta.values().length];
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            contadores[cartas[i].getNombre().ordinal()]++;
        }
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (contadores[cartas[i].getNombre().ordinal()] >= 2) {
                usadas[i] = true;
            }
        }

        for (Pinta pinta : Pinta.values()) {
            for (int i = 0; i < TOTAL_CARTAS - 1; i++) {
                if (!usadas[i] && cartas[i].getPinta() == pinta) {
                    for (int j = i + 1; j < TOTAL_CARTAS; j++) {
                        int diferencia = cartas[j].getNombre().ordinal() - cartas[i].getNombre().ordinal();
                        if (!usadas[j] && cartas[j].getPinta() == pinta && (diferencia == 1 || diferencia == -1)) {
                            usadas[i] = true;
                            usadas[j] = true;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (!usadas[i]) {
                int valor = cartas[i].getNombre().ordinal() + 1;
                if (valor > 10 || cartas[i].getNombre() == NombreCarta.AS) valor = 10; 
                puntaje += valor;
            }
        }
        return puntaje;
    }
}
    
