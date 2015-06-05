package me.leckie.demo.view;

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
            movementView.updatePhysics();
            /*try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}
