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
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
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
            int p = 0;
            for (int c : contadores) {
                if (c >= 2) {
                    mensaje += Grupo.values()[c] + " de " + NombreCarta.values()[p] + "\n";
                }
                p++;
            }
        }

        return mensaje;
    }
    
    public int calcularPuntaje() {
        int puntaje = 0;
        for (Carta c : cartas) {
            int valor = c.getNombre().ordinal() + 1; 
            if (valor > 10) {
                valor = 10;
            }
            puntaje += valor;
        }
        return puntaje;
    }
    
}