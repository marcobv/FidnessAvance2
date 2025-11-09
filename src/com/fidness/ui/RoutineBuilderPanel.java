package com.fidness.ui;
import com.fidness.data.MockData;
import com.fidness.exceptions.DatosInvalidosException;
import com.fidness.model.Ejercicio;
import com.fidness.model.Rutina;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
public class RoutineBuilderPanel extends JPanel {
    private DefaultListModel<Ejercicio> modelDisponibles;
    private DefaultListModel<Ejercicio> modelSeleccionados;
    private JList<Ejercicio> lstDisponibles;
    private JList<Ejercicio> lstSeleccionados;
    private JTextField txtNombreRutina;
    public RoutineBuilderPanel() { super(new BorderLayout()); initUI(); }
    private void initUI() {
        modelDisponibles = new DefaultListModel<>();
        modelSeleccionados = new DefaultListModel<>();
        MockData.getEjercicios().forEach(modelDisponibles::addElement);
        lstDisponibles = new JList<>(modelDisponibles);
        lstSeleccionados = new JList<>(modelSeleccionados);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Nombre de la rutina:"));
        txtNombreRutina = new JTextField(20);
        top.add(txtNombreRutina);
        JButton btnGuardar = new JButton("Guardar rutina");
        btnGuardar.addActionListener(this::onGuardarRutina);
        top.add(btnGuardar);
        add(top, BorderLayout.NORTH);
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6); gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1;
        gbc.gridx=0; gbc.gridy=0; gbc.weightx=0.45; center.add(new JScrollPane(lstDisponibles), gbc);
        JPanel buttons = new JPanel(new GridLayout(2,1,5,5));
        JButton btnAdd = new JButton(">>"); JButton btnRemove = new JButton("<<");
        btnAdd.addActionListener(this::onAdd); btnRemove.addActionListener(this::onRemove);
        buttons.add(btnAdd); buttons.add(btnRemove);
        gbc.gridx=1; gbc.gridy=0; gbc.weightx=0.1; center.add(buttons, gbc);
        gbc.gridx=2; gbc.gridy=0; gbc.weightx=0.45; center.add(new JScrollPane(lstSeleccionados), gbc);
        add(center, BorderLayout.CENTER);
        // Barra inferior con acciones de vista previa / exportación
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnPreview = new JButton("Vista previa");
        JButton btnExportTxt = new JButton("Exportar TXT");
        JButton btnSaveJson = new JButton("Guardar JSON");
        btnPreview.addActionListener(e -> mostrarPreview());
        btnExportTxt.addActionListener(e -> exportarTxt());
        btnSaveJson.addActionListener(e -> guardarJson());
        bottom.add(btnPreview); bottom.add(btnExportTxt); bottom.add(btnSaveJson);
        add(bottom, BorderLayout.SOUTH);
    }
    private void onAdd(ActionEvent e) { for (Ejercicio ej : lstDisponibles.getSelectedValuesList()) modelSeleccionados.addElement(ej); }
    private void onRemove(ActionEvent e) { for (Ejercicio ej : lstSeleccionados.getSelectedValuesList()) modelSeleccionados.removeElement(ej); }
    private Rutina construirRutinaActual() {
        String nombre = txtNombreRutina.getText().trim();
        if (nombre.isEmpty()) nombre = "Mi rutina";
        Rutina r = new Rutina((int)(Math.random()*100000), nombre, LocalDate.now().toString());
        for (int i = 0; i < modelSeleccionados.size(); i++) r.agregarEjercicio(modelSeleccionados.get(i));
        return r;
    }
    private void onGuardarRutina(ActionEvent e) {
        try {
            MockData.agregarRutina(construirRutinaActual());
            JOptionPane.showMessageDialog(this, "Rutina guardada correctamente.");
        } catch (DatosInvalidosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // === Vista previa ===
    private void mostrarPreview() {
        Rutina r = construirRutinaActual();
        StringBuilder sb = new StringBuilder();
        sb.append("Rutina: ").append(r.getNombreRutina()).append(" (").append(r.getFechaCreacion()).append(")\n\n");
        if (r.getListaEjercicios().isEmpty()) sb.append("- (vacía)\n");
        else r.getListaEjercicios().forEach(e -> sb.append("- ").append(e.getNombre()).append(" (").append(e.getTipo()).append(")\n"));
        JOptionPane.showMessageDialog(this, sb.toString(), "Vista previa", JOptionPane.INFORMATION_MESSAGE);
    }
    // === Exportar TXT ===
    private void exportarTxt() {
        Rutina r = construirRutinaActual();
        String desktop = System.getProperty("user.home") + File.separator + "Desktop";
        String filename = "Rutina_" + LocalDate.now().toString() + ".txt";
        File out = new File(desktop, filename);
        try (PrintWriter pw = new PrintWriter(java.nio.file.Files.newBufferedWriter(out.toPath(), java.nio.charset.StandardCharsets.UTF_8))) {
            pw.println("Rutina: " + r.getNombreRutina() + " (" + r.getFechaCreacion() + ")");
            pw.println();
            if (r.getListaEjercicios().isEmpty()) pw.println("- (vacía)");
            else for (Ejercicio e : r.getListaEjercicios()) pw.println("- " + e.getNombre() + " (" + e.getTipo() + ")");
            JOptionPane.showMessageDialog(this, "Exportación completada:\n" + out.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo exportar el TXT: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // === Guardar JSON ===
    private void guardarJson() {
        Rutina r = construirRutinaActual();
        String desktop = System.getProperty("user.home") + File.separator + "Desktop";
        String filename = "Rutina_" + LocalDate.now().toString() + ".json";
        File out = new File(desktop, filename);
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"nombre\": \"").append(r.getNombreRutina().replace("\\","\\\\").replace("\"","\\\"")).append("\",\n");
        json.append("  \"fecha\": \"").append(r.getFechaCreacion()).append("\",\n");
        json.append("  \"ejercicios\": [\n");
        for (int i=0; i<r.getListaEjercicios().size(); i++) {
            Ejercicio e = r.getListaEjercicios().get(i);
            json.append("    \"").append((e.getNombre()+" ("+e.getTipo()+")").replace("\\","\\\\").replace("\"","\\\"")).append("\""); 
        }
        if (r.getListaEjercicios().size() > 0) json.deleteCharAt(json.length()-1); // ensure commas not needed
        json.append("\n  ]\n}");
        try (PrintWriter pw = new PrintWriter(java.nio.file.Files.newBufferedWriter(out.toPath(), java.nio.charset.StandardCharsets.UTF_8))) {
            pw.write(json.toString());
            JOptionPane.showMessageDialog(this, "Guardado JSON completo:\n" + out.getAbsolutePath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el JSON: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
