import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Lider lider;

    public static void main(String[] args) {
        //cargarDatosDesdeArchivo(); //Aca hay un problema con la carga de los datos existentes

        System.out.println("¡Bienvenido al sistema de Gestion de Reuniones!");

        System.out.print("Ingrese nombre del líder: ");
        String nombreLider = scanner.nextLine();
        System.out.print("Ingrese nombre de la iglesia: ");
        String nombreIglesia = scanner.nextLine();

        lider = new Lider(nombreLider, nombreIglesia);

        mostrarMenuPrincipal();

        guardarDatosEnArchivo();
    }

    private static void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("datos.txt"))) {
            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                sb.append(linea).append("\n");
            }
            String texto = sb.toString();
            String[] lineas = texto.split("\n");

            Lider liderCargado;
            liderCargado = Lider.construirDesdeTexto(lineas);
            lider = liderCargado;
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de datos. Se creará uno nuevo.");
            lider = new Lider("Nombre del Lider", "Nombre de la Iglesia");
        }
    }

    private static void guardarDatosEnArchivo() {
        String texto = lider.convertirATexto();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("datos.txt"))) {
            writer.write(texto);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo");
            e.printStackTrace();
        }
    }

    private static void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n========== Menú ==========");
            System.out.println("1. Editar información de la iglesia");
            System.out.println("2. Agregar Miembro");
            System.out.println("3. Editar Informacion de Miembro");
            System.out.println("4. Crear grupo ");
            System.out.println("5. Agregar Miembro a grupo");
            System.out.println("6. Asignar líder de grupo");
            System.out.println("7. Editar Informacion grupo");
            System.out.println("8. Ver estadísticas");
            System.out.println("9. Agregar reunion");
            System.out.println("10. Ver Asistencias");
            System.out.println("11. Exit");
            System.out.print("Elija una opcion ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de lectura

            switch (opcion) {
                case 1:
                    editarInformacionIglesia();
                    break;
                case 2:
                    agregarMiembro();
                    break;
                case 3:
                    editarMiembro();
                    break;
                case 4:
                    crearGrupo();
                    break;
                case 5:
                    agregarMiembroAGrupo();
                    break;
                case 6:
                    designarLiderDeGrupo();
                    break;
                case 7:
                    editarGrupo();
                    break;
                case 8:
                    verEstadisticas();
                    break;
                case 9:
                    agregarReunionAGrupo();
                    break;
                case 10:
                    System.out.println("¡Hasta luego, vuelve pronto!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                    break;
            }
        } while (opcion != 10); // Terminar el bucle al seleccionar la opción 11 (Salir)
    }


    private static void designarLiderDeGrupo() {
        System.out.print("Ingrese el nombre del grupo: ");
        String nombreGrupo = scanner.nextLine();
        Grupo grupo = buscarGrupoPorNombre(nombreGrupo);
        if (grupo != null) {
            System.out.println("Miembros disponibles en el grupo:");
            ArrayList<Miembro> miembrosGrupo = grupo.getMiembros();
            for (int i = 0; i < miembrosGrupo.size(); i++) {
                System.out.println((i + 1) + ". " + miembrosGrupo.get(i).getNombre());
            }
            System.out.print("Ingrese el número del miembros que desea designar como líder: ");
            int numeroMiembro = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de lectura
            if (numeroMiembro >= 1 && numeroMiembro <= miembrosGrupo.size()) {
                Miembro miembroLider = miembrosGrupo.get(numeroMiembro - 1);
                grupo.designarLider(miembroLider);
                System.out.println("Líder asignado correctamente.");
            } else {
                System.out.println("Número de miembros inválido.");
            }
        } else {
            System.out.println("Grupo no existente.");
        }
    }

    private static Grupo buscarGrupoPorNombre(String nombreGrupo) {
        ArrayList<Grupo> grupos = lider.getGrupos();
        for (Grupo grupo : grupos) {
            if (grupo.getNombre().equalsIgnoreCase(nombreGrupo)) {
                return grupo;
            }
        }
        return null;
    }
    private static void editarInformacionIglesia() {
        System.out.print("Ingrese la nueva información de la iglesia: ");
        String nuevaInformacion = scanner.nextLine();
        lider.editarInformacionIglesia(nuevaInformacion);
        System.out.println("Información de la iglesia actualizada correctamente.");
    }

    private static void agregarMiembro() {
        System.out.print("Ingrese el nombre del nuevo Miembro: ");
        String nombreMiembro = scanner.nextLine();

        Miembro nuevoMiembro = new Miembro(nombreMiembro);
        lider.agregarMiembroNuevoEnIglesia(nuevoMiembro); // Agregar a la iglesia

        System.out.println("Miembro agregado correctamente a la iglesia.");
    }

    private static void editarMiembro() {
        System.out.print("Ingrese el nombre del Miembro a editar: ");
        String nombreMiembro = scanner.nextLine();
        Miembro miembro = buscarMiembroPorNombre(nombreMiembro);
        if (miembro != null) {
            System.out.print("Ingrese el nuevo perfil del miembro: ");
            String nuevoPerfil = scanner.nextLine();
            miembro.editarPerfil(nuevoPerfil);
            System.out.println("Perfil del miembro actualizado correctamente.");
        } else {
            System.out.println("Miembro no encontrado.");
        }
    }

    private static void crearGrupo() {
        System.out.print("Ingrese el nombre del nuevo grupo: ");
        String nombreGrupo = scanner.nextLine();
        System.out.print("Ingrese la descripción del nuevo grupo: ");
        String descripcionGrupo = scanner.nextLine();
        lider.crearGrupo(nombreGrupo, descripcionGrupo);
        System.out.println("Grupo creado correctamente.");
    }


    private static void agregarMiembroAGrupo() {
        ArrayList<Miembro> miembrosIglesia = lider.getIglesia().getMiembros();

        if (miembrosIglesia.isEmpty()) {
            System.out.println("No hay Miembro en la iglesia para agregar a un grupo.");
            return;
        }

        System.out.println("Miembros disponibles en la iglesia:");
        for (int i = 0; i < miembrosIglesia.size(); i++) {
            System.out.println((i + 1) + ". " + miembrosIglesia.get(i).getNombre());
        }

        System.out.print("Ingrese el número del miembros que desea agregar al grupo: ");
        int numeroMiembro = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de lectura

        if (numeroMiembro >= 1 && numeroMiembro <= miembrosIglesia.size()) {
            Miembro miembroAgregar = miembrosIglesia.get(numeroMiembro - 1);

            ArrayList<Grupo> grupos = lider.getGrupos();
            System.out.println("Grupos disponibles:");
            for (int i = 0; i < grupos.size(); i++) {
                System.out.println((i + 1) + ". " + grupos.get(i).getNombre());
            }

            System.out.print("Ingrese el número del grupo al que desea agregar al miembro: ");
            int numeroGrupo = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de lectura

            if (numeroGrupo >= 1 && numeroGrupo <= grupos.size()) {
                Grupo grupo = grupos.get(numeroGrupo - 1);
                grupo.agregarMiembro(miembroAgregar);
                System.out.println("Miembro agregado al grupo correctamente.");
            } else {
                System.out.println("Número de grupo inválido.");
            }
        } else {
            System.out.println("Número de miembro inválido.");
        }
    }




    private static void editarGrupo() {
        System.out.println("Grupos disponibles:");
        ArrayList<Grupo> grupos = lider.getGrupos();
        for (int i = 0; i < grupos.size(); i++) {
            System.out.println((i + 1) + ". " + grupos.get(i).getNombre());
        }
        System.out.print("Ingrese el número del grupo a editar: ");
        int numeroGrupo = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de lectura
        if (numeroGrupo >= 1 && numeroGrupo <= grupos.size()) {
            Grupo grupo = grupos.get(numeroGrupo - 1);
            System.out.print("Ingrese el nuevo nombre del grupo: ");
            String nuevoNombreGrupo = scanner.nextLine();
            System.out.print("Ingrese la nueva descripción del grupo: ");
            String nuevaDescripcionGrupo = scanner.nextLine();
            lider.editarGrupo(grupo, nuevoNombreGrupo, nuevaDescripcionGrupo);
            System.out.println("Grupo actualizado correctamente.");
        } else {
            System.out.println("Número de grupo inválido.");
        }
    }

    private static void verEstadisticas() {
        System.out.println("\n========== Estadísticas ==========");
        System.out.println("Iglesia: " + lider.getNombreIglesia());
        System.out.println("Total de miembros en la iglesia: " + lider.getTotalMiembros());
        System.out.println("Total de líderes en la iglesia: " + lider.getTotalLideres());
        System.out.println("Total de reuniones en la iglesia: " + contarReunionesIglesia());
        System.out.println("\nMiembros de la iglesia:");
        for (Miembro miembro : lider.getIglesia().getMiembros()) {
            System.out.println("- " + miembro.getNombre());
        }
        ArrayList<Grupo> grupos = lider.getGrupos();
        for (Grupo grupo : grupos) {
            System.out.println("\nGrupo: " + grupo.getNombre());
            System.out.println("Total de miembros en el grupo: " + grupo.getMiembros().size());
            System.out.println("Total de líderes en el grupo: " + grupo.getLideres().size());
            System.out.println("Total de reuniones en el grupo: " + grupo.getReuniones().size());
            System.out.println("Miembros del grupo:");
            for (Miembro miembro : grupo.getMiembros()) {
                System.out.println("- " + miembro.getNombre());
            }
        }
    }

    private static int contarReunionesIglesia() {
        int totalReuniones = 0;
        ArrayList<Grupo> grupos = lider.getGrupos();
        for (Grupo grupo : grupos) {
            totalReuniones += grupo.getReuniones().size();
        }
        return totalReuniones;
    }

    private static Miembro buscarMiembroPorNombre(String nombreMiembro) {
        ArrayList<Grupo> grupos = lider.getGrupos();
        for (Grupo grupo : grupos) {
            ArrayList<Miembro> miembros = grupo.getMiembros();
            for (Miembro miembro : miembros) {
                if (miembro.getNombre().equalsIgnoreCase(nombreMiembro)) {
                    return miembro;
                }
            }
        }
        return null;
    }
    private static void mostrarReunionesDeGrupo(Grupo grupo) {
        ArrayList<Reunion> reuniones = grupo.getReuniones();
        System.out.println("Reuniones del grupo \"" + grupo.getNombre() + "\":");
        for (int i = 0; i < reuniones.size(); i++) {
            Reunion reunion = reuniones.get(i);
            System.out.println((i + 1) + ". " + reunion.getDatos());
        }
    }
    private static void agregarReunionAGrupo() {
        System.out.print("Ingrese el nombre del grupo al que desea agregar la reunión: ");
        String nombreGrupo = scanner.nextLine();
        Grupo grupo = buscarGrupoPorNombre(nombreGrupo);
        if (grupo != null) {
            System.out.print("Ingrese los datos de la reunión: ");
            String datosReunion = scanner.nextLine();
            Reunion reunion = new Reunion(datosReunion, false);
            grupo.agregarReunion(reunion);
            System.out.println("Reunión agregada al grupo correctamente.");

        } else {
            System.out.println("Grupo no encontrado.");
        }
    }


    private static void verAsistentesDeReunion() {
        System.out.print("Ingrese el nombre del grupo: ");
        String nombreGrupo = scanner.nextLine();
        Grupo grupo = buscarGrupoPorNombre(nombreGrupo);
        if (grupo != null) {
            mostrarReunionesDeGrupo(grupo);
            System.out.print("Ingrese el número de la reunión para ver los asistentes (o 0 para volver): ");
            int numeroReunion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de lectura
            if (numeroReunion >= 1 && numeroReunion <= grupo.getReuniones().size()) {
                Reunion reunion = grupo.getReuniones().get(numeroReunion - 1);
                mostrarAsistentesDeReunion(reunion);
            } else if (numeroReunion == 0) {
                // Volver al menú principal
            } else {
                System.out.println("Número de reunión inválido.");
            }
        } else {
            System.out.println("Grupo no encontrado.");
        }
    }
    private static void mostrarMiembrosDeGrupo(Grupo grupo) {
        ArrayList<Miembro> miembrosGrupo = grupo.getMiembros();
        System.out.println("Miembros del grupo \"" + grupo.getNombre() + "\":");
        for (int i = 0; i < miembrosGrupo.size(); i++) {
            Miembro miembro = miembrosGrupo.get(i);
            System.out.println((i + 1) + ". " + miembro.getNombre() + " - Líder: " + (miembro.getEsLider() ? "Sí" : "No"));
        }
    }

    private static void mostrarAsistentesDeReunion(Reunion reunion) {
        ArrayList<Asistencia> asistencias = reunion.getAsistencias();
        System.out.println("Asistentes a la reunión:");
        for (int i = 0; i < asistencias.size(); i++) {
            Asistencia asistencia = asistencias.get(i);
            System.out.println((i + 1) + ". " + asistencia.getNombre() + " - Asistió: " + asistencia.isAsistio() + " - Excusa: " + asistencia.getExcusa());
        }
    }
}