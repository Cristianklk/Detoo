import java.io.Serializable;
import java.util.ArrayList;

public class Reunion implements Serializable {
    private String datos;
    private boolean finalizada;
    private ArrayList<Asistencia> asistencias;
    private int numLineas;

    public Reunion(String datos, boolean finalizada) {
        this.datos = datos;
        this.finalizada = finalizada;
        this.asistencias = new ArrayList<>();
    }

    public String getDatos() {
        return datos;
    }



    public boolean isFinalizada() {
        return finalizada;
    }


    public ArrayList<Asistencia> getAsistencias() {
        return asistencias;
    }



    public int getNumLineas() {
        // Calcula la cantidad de líneas que ocupará la información de la reunión en el archivo de texto
        numLineas = 2; // Datos y finalizada
        for (Asistencia asistencia : asistencias) {
            numLineas += asistencia.getNumLineas(); // Sumar líneas por cada asistencia (nombre, asistio, excusa)
        }
        return numLineas;
    }

    public static Reunion construirDesdeTexto(String[] lineas, int index) {
        String datos = lineas[index];
        boolean finalizada = Boolean.parseBoolean(lineas[index + 1]);
        Reunion reunion = new Reunion(datos, finalizada);
        int asistenciasIndex = index + 2;
        while (asistenciasIndex < lineas.length && !lineas[asistenciasIndex].equals("")) {
            Asistencia asistencia;
            asistencia = Asistencia.construirDesdeTexto(lineas, asistenciasIndex);
            reunion.asistencias.add(asistencia);
            asistenciasIndex += asistencia.getNumLineas(); // Usamos getNumLineas() de Asistencia
        }
        return reunion;
    }
}

