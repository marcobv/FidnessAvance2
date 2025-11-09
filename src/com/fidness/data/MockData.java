package com.fidness.data;

import com.fidness.exceptions.DatosInvalidosException;
import com.fidness.model.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MockData {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<Ejercicio> ejercicios = new ArrayList<>();
    private static final List<Rutina> rutinas = new ArrayList<>();
    private static final Pattern EMAIL_RE = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    static {
        // === Cargar usuarios desde archivo si existen ===
        usuarios.addAll(DataStore.loadUsuarios());
        if (usuarios.isEmpty()) {
            usuarios.add(new Admin(1, "Admin", "admin@fidness.com", "admin"));
            usuarios.add(new Cliente(2, "Marco", "marco@example.com", "1234"));
        }

        // === Ejercicios base ===
        String basePath = "images/";
        ejercicios.add(new EjercicioFuerza(1, "Curl de Bíceps", "Brazo", "Ejercicio de fuerza para bíceps", basePath + "curlbiceps.jpg"));
        ejercicios.add(new EjercicioFuerza(2, "Peso Muerto", "Espalda", "Fuerza para cadena posterior", basePath + "pesomuerto.jpg"));
        ejercicios.add(new EjercicioCardio(3, "Plancha", "Core", "Estabilidad del core", basePath + "plancha.jpg"));
        ejercicios.add(new Ejercicio(4, "Sentadilla", "Pierna", "Flexión de rodillas manteniendo espalda recta.", basePath + "sentadilla.jpg"));
        ejercicios.add(new Ejercicio(5, "Press banca", "Pecho", "Empuje de barra acostado.", basePath + "pressdebanca.jpg"));

        // === Cargar rutinas persistentes si existen ===
        List<Rutina> cargadas = DataStore.loadRutinas();
        if (cargadas != null) rutinas.addAll(cargadas);
    }

    public static boolean correoValido(String correo) {
        return EMAIL_RE.matcher(correo).matches();
    }

    public static List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public static List<String> getTipos() {
        return ejercicios.stream()
                .map(Ejercicio::getTipo)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<Rutina> getRutinas() {
        return rutinas;
    }

    public static Usuario findUsuarioByCorreo(String correo) {
        return usuarios.stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElse(null);
    }

    public static boolean registrarUsuario(String nombre, String correo, String contrasena) {
        if (nombre == null || nombre.isBlank() || !correoValido(correo) || contrasena == null || contrasena.length() < 4)
            return false;
        if (findUsuarioByCorreo(correo) != null) return false;

        int id = usuarios.stream().mapToInt(Usuario::getIdUsuario).max().orElse(0) + 1;
        usuarios.add(new Cliente(id, nombre, correo, contrasena));
        DataStore.saveUsuarios(usuarios); // ✅ ahora guarda los usuarios
        return true;
    }

    public static boolean validarLogin(String correo, String contrasena) {
        Usuario u = findUsuarioByCorreo(correo);
        return u != null && u.getContrasena().equals(contrasena);
    }

    public static void agregarRutina(Rutina r) throws DatosInvalidosException {
        if (r == null || r.getNombreRutina() == null || r.getNombreRutina().isBlank()) {
            throw new DatosInvalidosException("Nombre de rutina inválido");
        }

        rutinas.add(r);

        try {
            DataStore.saveRutinas(rutinas);
        } catch (Exception ex) {
            System.err.println("No se pudo guardar rutinas: " + ex.getMessage());
        }
    }
}