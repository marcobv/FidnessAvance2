package com.fidness.ui;
import com.fidness.data.MockData;
import com.fidness.model.Rutina;
import com.fidness.model.Ejercicio;
import javax.swing.*;
import java.awt.*;
public class VentanaRutinas extends JFrame {
    private final DefaultListModel<Rutina> modelRutinas = new DefaultListModel<>();
    private final JTextArea txtDetalle = new JTextArea();
    public VentanaRutinas() {
        super("Rutinas guardadas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        MockData.getRutinas().forEach(modelRutinas::addElement);
        JList<Rutina> lstRutinas = new JList<>(modelRutinas);
        lstRutinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstRutinas.addListSelectionListener(e -> mostrarDetalle(lstRutinas.getSelectedValue()));
        txtDetalle.setEditable(false);
        txtDetalle.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panel.add(new JScrollPane(lstRutinas), BorderLayout.WEST);
        panel.add(new JScrollPane(txtDetalle), BorderLayout.CENTER);
        add(panel);
    }
    private void mostrarDetalle(Rutina r) {
        if (r == null) return;
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(r.getNombreRutina()).append("\n");
        sb.append("Fecha: ").append(r.getFechaCreacion()).append("\n\n");
        sb.append("Ejercicios:\n");
        if (r.getListaEjercicios().isEmpty())
            sb.append(" - (vacía)\n");
        else
            for (Ejercicio e : r.getListaEjercicios())
                sb.append(" - ").append(e.getNombre()).append(" (").append(e.getTipo()).append(")\n");
        txtDetalle.setText(sb.toString());
    }
}
