package me.leckie.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by leckie on 6/3/15.
 */
public class MovementView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MovementView";

    private int xPos = 0;
    private int yPos = 0;

    private int xVel = 0;
    private int yVel = 0;

    private int width;
    private int height;

    private int circleRadius;
    private Paint circlePaint;

    private MovementUpdateThread movementUpdateThread;

    public MovementView(Context context) {
        super(context);

        getHolder().addCallback(this);

        circleRadius = 75;
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);

        xVel = 2;
        yVel = 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(xPos, yPos, circleRadius, circlePaint);

        invalidate();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();

        Log.e("position", width + ":" + height);

        xPos = width / 2;
        yPos = circleRadius;

        movementUpdateThread = new MovementUpdateThread(this);
        movementUpdateThread.setRunning(true);
        movementUpdateThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        movementUpdateThread.setRunning(false);
        while (retry) {
            try {
                movementUpdateThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    public void updatePhysics() {
        //更新当前的x,y坐标
        xPos += xVel;
        yPos += yVel;

        if (yPos - circleRadius < 0 || yPos + circleRadius > height) {


            if (yPos - circleRadius < 0) {

                //如果小球到达画布区域的上顶端，则弹回

                yPos = circleRadius;
            } else {

                //如果小球到达了画布的下端边界，则弹回

                yPos = height - circleRadius;
            }

            // 将Y坐标设置为相反方向
            yVel *= -1;
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
            xVel *= -1;
        }
    }
}
