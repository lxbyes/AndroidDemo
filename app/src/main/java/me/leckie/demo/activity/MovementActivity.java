package me.leckie.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import me.leckie.demo.view.MovementView;

public class MovementActivity extends Activity {

    private MovementView movementView;

    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        movementView = new MovementView(this);
        handler = new MyHandler();

        super.onCreate(savedInstanceState);
        setContentView(movementView);

        MovementUpdateThread ch = new MovementUpdateThread();
        ch.setRunning(true);
        ch.start();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            movementView.doDraw();
        }
    }


    /**
     * Created by leckie on 6/3/15.
     */
    class MovementUpdateThread extends Thread {

        private boolean running = false;

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    movementView.updatePhysics();
                    handler.sendMessage(new Message());
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


}
