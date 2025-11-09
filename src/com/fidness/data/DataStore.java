package com.fidness.data;

import com.fidness.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static final String FILE_RUTINAS = "rutinas.dat";
    private static final String FILE_USUARIOS = "usuarios.dat";

    // === Guardar rutinas ===
    public static void saveRutinas(List<Rutina> rutinas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_RUTINAS))) {
            oos.writeObject(rutinas);
            System.out.println("Rutinas guardadas correctamente en " + FILE_RUTINAS);
        } catch (IOException ex) {
            System.err.println("Error al guardar rutinas: " + ex.getMessage());
        }
    }

    // === Cargar rutinas ===
    @SuppressWarnings("unchecked")
    public static List<Rutina> loadRutinas() {
        File file = new File(FILE_RUTINAS);
        if (!file.exists()) {
            System.out.println("No existe archivo de rutinas, se devolverá una lista vacía.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Rutina>) obj;
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar rutinas: " + ex.getMessage());
        }

        return new ArrayList<>();
    }

    // === Guardar usuarios ===
    public static void saveUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_USUARIOS))) {
            oos.writeObject(usuarios);
            System.out.println("Usuarios guardados correctamente en " + FILE_USUARIOS);
        } catch (IOException ex) {
            System.err.println("Error al guardar usuarios: " + ex.getMessage());
        }
    }

    // === Cargar usuarios ===
    @SuppressWarnings("unchecked")
    public static List<Usuario> loadUsuarios() {
        File file = new File(FILE_USUARIOS);
        if (!file.exists()) {
            System.out.println("No existe archivo de usuarios, se devolverá una lista vacía.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Usuario>) obj;
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar usuarios: " + ex.getMessage());
        }

        return new ArrayList<>();
    }
}
