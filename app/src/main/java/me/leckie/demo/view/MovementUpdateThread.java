package me.leckie.demo.view;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.logging.Handler;

/**
 * Created by leckie on 6/3/15.
 */
public class MovementUpdateThread extends Thread {

    private boolean running = false;
    private MovementView movementView;

    public MovementUpdateThread(MovementView view) {
        this.movementView = view;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            try {
                movementView.updatePhysics();
            } finally {
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
