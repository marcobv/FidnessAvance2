package com.fidness.ui;
import com.fidness.data.MockData;
import com.fidness.model.Ejercicio;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
public class ExerciseCatalogPanel extends JPanel {
    private JComboBox<String> cboTipo;
    private DefaultListModel<Ejercicio> listModel;
    private JList<Ejercicio> lstEjercicios;
    private JTextArea txtDescripcion;
    private JLabel lblImagen;
    public ExerciseCatalogPanel() {
        setLayout(new BorderLayout(10,10));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Filtrar por tipo:"));
        cboTipo = new JComboBox<>(); cboTipo.addItem("Todos");
        for (String t : MockData.getTipos()) cboTipo.addItem(t);
        cboTipo.addActionListener(e -> recargarLista());
        top.add(cboTipo);
        add(top, BorderLayout.NORTH);
        listModel = new DefaultListModel<>();
        lstEjercicios = new JList<>(listModel);
        lstEjercicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstEjercicios.addListSelectionListener(e -> mostrarDetalle(lstEjercicios.getSelectedValue()));
        add(new JScrollPane(lstEjercicios), BorderLayout.WEST);
        JPanel detalle = new JPanel(new BorderLayout());
        txtDescripcion = new JTextArea(); txtDescripcion.setEditable(false); txtDescripcion.setLineWrap(true); txtDescripcion.setWrapStyleWord(true);
        detalle.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);
        lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER); lblImagen.setPreferredSize(new Dimension(250,200));
        detalle.add(lblImagen, BorderLayout.EAST);
        add(detalle, BorderLayout.CENTER);
        recargarLista();
    }
    private void recargarLista() {
        listModel.clear();
        String tipoSel = (String) cboTipo.getSelectedItem();
        List<Ejercicio> data = MockData.getEjercicios();
        if (tipoSel != null && !"Todos".equalsIgnoreCase(tipoSel)) {
            data = data.stream().filter(e -> e.getTipo().equalsIgnoreCase(tipoSel)).collect(Collectors.toList());
        }
        for (Ejercicio e : data) listModel.addElement(e);
        if (!data.isEmpty()) lstEjercicios.setSelectedIndex(0);
    }
    private void mostrarDetalle(Ejercicio e) {
        if (e == null) { txtDescripcion.setText(""); lblImagen.setIcon(null); lblImagen.setText("Sin imagen"); return; }
        txtDescripcion.setText(e.getDescripcion());
        String path = e.getImagenPath();
        lblImagen.setIcon(null); lblImagen.setText("Sin imagen");
        if (path != null && !path.trim().isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img)); lblImagen.setText(null);
            } catch (Exception ex) { lblImagen.setText("No se pudo cargar la imagen"); }
        }
    }
}
