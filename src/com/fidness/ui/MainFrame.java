package com.fidness.ui;
import javax.swing.*; import java.awt.*;
public class MainFrame extends JFrame {
    private JTabbedPane tabs;
    public MainFrame() {
        super("Fidness - Prototipo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setLocationRelativeTo(null);
        setJMenuBar(buildMenu());
        initUI();
    }
    private void initUI() {
        tabs = new JTabbedPane();
        tabs.addTab("Catálogo de ejercicios", new ExerciseCatalogPanel());
        tabs.addTab("Constructor de rutinas", new RoutineBuilderPanel());
        add(tabs, BorderLayout.CENTER);
    }
    private JMenuBar buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu mArchivo = new JMenu("Archivo");
        JMenuItem mSalir = new JMenuItem("Salir");
        mSalir.addActionListener(e -> System.exit(0));
        mArchivo.add(mSalir);
        JMenu mVer = new JMenu("Ver");
        JMenuItem verEjercicios = new JMenuItem("Ver ejercicios");
        verEjercicios.addActionListener(e -> tabs.setSelectedIndex(0));
        JMenuItem crearRutina = new JMenuItem("Crear rutina");
        crearRutina.addActionListener(e -> tabs.setSelectedIndex(1));
        JMenuItem rutinasGuardadas = new JMenuItem("Rutinas guardadas");
        rutinasGuardadas.addActionListener(e -> new VentanaRutinas().setVisible(true));
        mVer.add(verEjercicios); mVer.add(crearRutina); mVer.add(rutinasGuardadas);
        bar.add(mArchivo); bar.add(mVer);
        return bar;
    }
}
