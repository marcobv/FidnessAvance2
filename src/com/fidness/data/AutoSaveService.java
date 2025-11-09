package com.fidness.data;

import com.fidness.model.Rutina;
import java.util.List;

public class AutoSaveService implements Runnable {
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            try {
                
                List<Rutina> rutinas = MockData.getRutinas();
                DataStore.saveRutinas(rutinas);
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
                running = false;
            } catch (Exception ex) {
                System.err.println("AutoSave error: " + ex.getMessage());
            }
        }
    }

    public void detener() {
        running = false;
    }
}
