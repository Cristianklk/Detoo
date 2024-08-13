import java.io.Serializable;
import java.util.ArrayList;

public class Lider extends Miembro implements Serializable {
    private String nombreIglesia;
    private int totalGrupos;
    private int totalMiembros;
    private int totalLideres;
    private Iglesia iglesia;
    private ArrayList<Grupo> grupos;

    public Lider(String nombre, String nombreIglesia) {
        super(nombre);
        this.nombreIglesia = nombreIglesia;
        this.totalGrupos = 0;
        this.totalMiembros = 0;
        this.totalLideres = 0;
        this.grupos = new ArrayList<>();
        this.iglesia = new Iglesia(nombreIglesia);
    }

    public static Lider construirDesdeTexto(String[] lineas) {
        String nombreLider = lineas[0];
        String nombreIglesia = lineas[1];

        Lider liderCargado = new Lider(nombreLider, nombreIglesia);

        int gruposIndex = 2;
        while (gruposIndex < lineas.length && !lineas[gruposIndex].equals("")) {
            Grupo grupo;
            grupo = Grupo.construirDesdeTexto(lineas, gruposIndex);
            liderCargado.agregarGrupo(grupo);
            gruposIndex += grupo.getNumLineas(); // Usamos getNumLineas() de Grupo
        }

        return liderCargado;
    }

    public String convertirATexto() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNombre()).append("\n");
        sb.append(nombreIglesia).append("\n");

        for (Grupo grupo : grupos) {
            sb.append(grupo.convertirATexto()); // Utilizamos el método de la clase Grupo
        }

        return sb.toString();
    }
    public void agregarMiembroNuevoEnIglesia(Miembro miembro) {
        iglesia.agregarMiembroNuevo(miembro, false); // Agregar miembro a la iglesia
        totalMiembros++;
        if (miembro.getEsLider()) { // Aquí estás verificando si el miembro es líder
            totalLideres++; // Incrementar el contador de líderes
        }
    }


    public void crearGrupo(String nombre, String descripcion) {
        Grupo nuevoGrupo = new Grupo(nombre, descripcion);
        nuevoGrupo.agregarLider(this); // Agregar el líder al grupo recién creado
        grupos.add(nuevoGrupo); // Agregar el grupo a la lista de grupos
        totalGrupos++;
    }

    public void editarGrupo(Grupo grupo, String nuevoNombre, String nuevaDescripcion) {
        grupo.setNombre(nuevoNombre);
        grupo.setDescripcion(nuevaDescripcion);
    }
    public void setInformacionIglesia(String nuevaInformacion) {
        this.nombreIglesia = nuevaInformacion;
    }
    public void editarInformacionIglesia(String nuevaInformacion) {
        setInformacionIglesia(nuevaInformacion);
    }
    public String getNombreIglesia() {
        return nombreIglesia;
    }
    public Iglesia getIglesia() {
        return iglesia;
    }
    public int getTotalMiembros() {
        return totalMiembros;
    }
    public int getTotalLideres() {
        return totalLideres;
    }
    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }
    public void agregarGrupo(Grupo grupo) {
        grupos.add(grupo);
    }



}