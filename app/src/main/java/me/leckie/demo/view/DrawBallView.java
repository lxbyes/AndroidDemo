package me.leckie.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by leckie on 6/3/15.
 */
public class DrawBallView extends View {

    private float x = 0.0f;

    private float y = 0.0f;

    private float radius = 100.0f;

    private Paint paint = new Paint();

    public DrawBallView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.GRAY);

        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        invalidate();

        return true;
    }
}
