import java.io.Serializable;
import java.util.ArrayList;

public class Iglesia implements Serializable {
    private String informacion;
    private ArrayList<Miembro> miembros;

    public Iglesia(String informacion) {
        this.informacion = informacion;
        this.miembros = new ArrayList<>();
    }

    public void agregarMiembroNuevo(Miembro miembro, boolean esLider) {
        miembros.add(miembro);
        if (esLider) {
            // Aquí puedes realizar las acciones necesarias para asignar a este miembro como líder
        }
    }
    public ArrayList<Miembro> getMiembros() {
        return miembros;
    }

}
