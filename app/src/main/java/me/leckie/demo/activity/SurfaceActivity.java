package me.leckie.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import me.leckie.demo.R;

public class SurfaceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }

    class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

        private static final String TAG = "MySurfaceView";

        private SurfaceHolder holder;

        private MyThread myThread;

        public MySurfaceView(Context context) {
            super(context);
            holder = getHolder();
            holder.addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            myThread = new MyThread(holder);
            myThread.setRunning(true);
            myThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            myThread.setRunning(false);
            myThread = null;
        }
    }

    class MyThread extends Thread {

        private SurfaceHolder holder;

        private boolean running = false;

        public MyThread(SurfaceHolder holder) {
            this.holder = holder;
            running = true;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            int count = 0;
            while (running) {
                Canvas c = null;
                try {
                    c = holder.lockCanvas();

                    c.drawColor(Color.WHITE);//设置画布背景颜色
                    Paint p = new Paint(); //创建画笔
                    p.setColor(Color.YELLOW);
                    Rect r = new Rect(100, 50, 300, 250);
                    c.drawRect(r, p);
                    p.setTextSize(32.0f);
                    p.setColor(Color.BLACK);
                    c.drawText("这是第" + (count++) + "秒", 100, 310, p);
                    Thread.sleep(1000);//睡眠时间为1秒
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }

}
