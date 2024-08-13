import java.io.Serializable;
import java.util.ArrayList;

public class Grupo implements Serializable {
    private String nombre;
    private String descripcion;
    private ArrayList<Miembro> miembros;
    private ArrayList<Lider> lideres;
    private ArrayList<Reunion> reuniones;
    private Lider lider;

    public Grupo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.miembros = new ArrayList<>();
        this.lideres = new ArrayList<>();
        this.reuniones = new ArrayList<>();
    }
    public static Grupo construirDesdeTexto(String[] lineas, int index) {
        String nombre = lineas[index];
        String descripcion = lineas[index + 1];

        // Creamos una lista para almacenar las reuniones
        ArrayList<Reunion> reuniones = new ArrayList<>();

        // Empezamos el índice en index + 2 para saltar las líneas que ya hemos leído
        int reunionesIndex = index + 2;
        while (reunionesIndex < lineas.length && !lineas[reunionesIndex].equals("")) {
            Reunion reunion;
            reunion = Reunion.construirDesdeTexto(lineas, reunionesIndex);
            reuniones.add(reunion);
            reunionesIndex += reunion.getNumLineas(); // Usamos getNumLineas() de Reunion
        }

        // Crea y retorna un objeto Grupo con la información obtenida
        Grupo grupo = new Grupo(nombre, descripcion);
        grupo.reuniones = reuniones; // Asignamos la lista de reuniones al grupo
        return grupo;
    }

    public String convertirATexto() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append("\n");
        sb.append(descripcion).append("\n");

        for (Miembro miembro : miembros) {
            sb.append(miembro.getNombre()).append("\n");
        }

        for (Reunion reunion : reuniones) {
            sb.append(reunion.getDatos()).append("\n");
            sb.append(reunion.isFinalizada()).append("\n");
            for (Asistencia asistencia : reunion.getAsistencias()) {
                sb.append(asistencia.getNombreMiembro()).append("\n");
                sb.append(asistencia.isAsistio()).append("\n");
                sb.append(asistencia.getExcusa()).append("\n");
            }
            sb.append("").append("\n"); // Línea en blanco para separar las reuniones
        }

        return sb.toString();
    }
    public void designarLider(Miembro miembro) {
        if (!miembros.contains(miembro)) {
            System.out.println("El miembro mencionado no pertenece a este grupo y no puede ser designado como líder.");
            return;
        }

        for (Miembro m : miembros) {
            m.setEsLider(m == miembro); // Solo establecer como líder al miembro específico
        }
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
    }
    public String getNombre() {
        return nombre;
    }
    // Resto del código igual...
    public ArrayList<Miembro> getMiembros() {
        return miembros;
    }
    public ArrayList<Reunion> getReuniones() {
        return reuniones;
    }
    public ArrayList<Lider> getLideres() {  // Cambiado el tipo de lista a Lider
        return lideres;
    }
    public void agregarLider(Lider lider) {  // Cambiado el tipo de parámetro a Lider
        lideres.add(lider);
    }
    public int getNumLineas() {
        // Cada grupo tiene una descripción y al menos una línea para el nombre
        int numLineas = 1;
        for (Miembro miembro : getMiembros()) {  // Corregido: getmiembros() -> getMiembros()
            numLineas++; // Una línea por cada miembro del grupo
        }
        for (Reunion reunion : getReuniones()) {
            numLineas += 3; // Tres líneas por cada reunión (datos, finalizada y asistencias)
        }
        return numLineas;
    }
    public void agregarReunion(Reunion reunion) {
        reuniones.add(reunion);
    }

}