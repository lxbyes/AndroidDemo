package me.leckie.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by leckie on 6/3/15.
 */
public class MovementView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MovementView";

    private float fx = Float.MAX_VALUE;
    private float fy = Float.MAX_VALUE;

    private float xPos = 0f;
    private float yPos = 0f;

    private int xVel = 1;
    private int yVel = 1;

    private float width;
    private float height;

    private float circleRadius;
    private Paint circlePaint;

    private MovementUpdateThread movementUpdateThread;

    private SurfaceHolder holder;

    public MovementView(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);

        circleRadius = 75;
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);

        xVel = 1;
        yVel = 1;

        movementUpdateThread = new MovementUpdateThread(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();

        Log.e("position", width + ":" + height);

        xPos = width / 2;
        yPos = circleRadius;

        movementUpdateThread.setRunning(true);
        movementUpdateThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean syn = true;
        int count = 0;
        movementUpdateThread.setRunning(false);
        while (syn) {
            Log.e(TAG, "" + (++count));
            try {
                movementUpdateThread.join();
                syn = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.fx = event.getX();
                this.fy = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                this.fx = Float.MAX_VALUE;
                this.fy = Float.MAX_VALUE;
                break;
            case MotionEvent.ACTION_MOVE:
                this.fx = event.getX();
                this.fy = event.getY();
                break;
        }

        return true;
    }

    public void updatePhysics() {
        Random random = new Random();
        //更新当前的x,y坐标
        xPos += (random.nextInt(30) + 12) * xVel;
        yPos += (random.nextInt(40) + 16) * yVel;

        if (yPos - circleRadius < 0 || yPos + circleRadius > height) {


            if (yPos - circleRadius < 0) {

                //如果小球到达画布区域的上顶端，则弹回

                yPos = circleRadius;
            } else {

                //如果小球到达了画布的下端边界，则弹回

                yPos = height - circleRadius;
            }

            // 将Y坐标设置为相反方向
            yVel = -yVel;
        }
        if (xPos - circleRadius < 0 || xPos + circleRadius > width) {


            if (xPos - circleRadius < 0) {

                // 如果小球到达左边缘

                xPos = circleRadius;
            } else {

                // 如果小球到达右边缘

                xPos = width - circleRadius;
            }

            // 重新设置x轴坐标
            xVel = -xVel;
        }

        Canvas c = null;
        try {
            c = holder.lockCanvas();
            c.drawColor(Color.WHITE);
            c.drawCircle(xPos, yPos, circleRadius, circlePaint);
            circlePaint.setTextSize(40);
            c.drawText(" x:" + xPos + ",  y:" + yPos, 30, 30, circlePaint);
            c.drawText("fx:" + fx + ", fy:" + fy, 30, 80, circlePaint);
            c.drawLine(0f, 120f, 1000.0f - Math.abs(fx - xPos), 120f, circlePaint);
            c.drawLine(0f, 150f, 1000.0f - Math.abs(fy - yPos), 150f, circlePaint);
            c.drawLine(0f, 180f, 1000.0f - (float) Math.sqrt((fx - fy) * (fx - fy) + (xPos - yPos) * (xPos - yPos)), 180f, circlePaint);
        } catch (Exception e) {
        } finally {
            if (c != null) {
                holder.unlockCanvasAndPost(c);
            }
        }
    }
}
