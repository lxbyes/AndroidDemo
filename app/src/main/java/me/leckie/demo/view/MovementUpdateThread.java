package me.leckie.demo.view;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by leckie on 6/3/15.
 */
public class MovementUpdateThread extends Thread {

    private boolean running = false;
    private MovementView movementView;

    private SurfaceHolder holder;

    public MovementUpdateThread(MovementView view) {
        this.movementView = view;
        holder = view.getHolder();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            Canvas c = null;
            try {
                c = holder.lockCanvas(null);
                movementView.updatePhysics();
                movementView.draw(c);
            } finally {
                holder.unlockCanvasAndPost(c);
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
