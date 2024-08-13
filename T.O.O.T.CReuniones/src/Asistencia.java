import java.io.Serializable;

public class Asistencia implements Serializable {
    private String nombreMiembro;
    private boolean asistio;
    private String excusa;
    private String nombre;

    public Asistencia(String nombreMiembro, boolean asistio, String excusa) {
        this.nombreMiembro = nombreMiembro;
        this.asistio = asistio;
        this.excusa = excusa;
    }
    public String getNombreMiembro() {
        return nombreMiembro;
    }
    public boolean isAsistio() {
        return asistio;
    }
    public String getExcusa() {return excusa;}
    public String getNombre() {return nombre;}
    public int getNumLineas() {
        // Devuelve la cantidad de líneas que ocupará la información de la asistencia en el archivo de texto
        return 3;
    }
    public static Asistencia construirDesdeTexto(String[] lineas, int texto) {
        String nombreMiembro = lineas[texto];
        boolean asistio = Boolean.parseBoolean(lineas[texto + 1]);
        String excusa = lineas[texto + 2];
        return new Asistencia(nombreMiembro, asistio, excusa);
    }
}
