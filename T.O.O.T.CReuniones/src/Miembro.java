import java.io.Serializable;

public class Miembro implements Serializable {
    private String nombre;
    private boolean esLider;
    private String perfil; // Agregamos un atributo para el perfil del miembro


    public Miembro(String nombre) {
        this.nombre = nombre;
        this.esLider = false;
        this.perfil = ""; // Inicializamos el perfil vacío
    }
    public void editarPerfil(String nuevoPerfil) {
        this.perfil = nuevoPerfil; // Actualizamos el perfil con el nuevo valor
    }
    public String getNombre() {
        return nombre;
    }

    public void setEsLider(boolean esLider) {
        this.esLider = esLider;
    }
    public boolean getEsLider() {
        return esLider;
    }

}