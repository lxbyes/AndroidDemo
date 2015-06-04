package me.leckie.demo.ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by leckie on 6/4/15.
 */
public class BallView extends SurfaceView implements SurfaceHolder.Callback {
    static final int V_MAX = 85;// 小球水平速度的最大值
    static final int V_MIN = 15;// 小球水平速度的最小值

    public static final int WOOD_EDGE = 60;// 木板右边沿的X坐标

    static final int GROUND_LING = 730;// 代表地面Y的坐标

    static final int UP_ZERO = 30;// 小球在上升的过程中，如果小于此数则为0，

    static final int DOWN_ZERO = 60;// 小球在下落的过程中，如果速度小于该值则为0.

    String fps = "FPS:N/A";// 用于显示帧率的字符串

    int ballNumber = 8;// 小球个数

    ArrayList<Moveable> arrayList = new ArrayList<Moveable>();// 小球对象数组

    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread;

    public BallView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        initMovables();
        drawThread = new DrawThread(this, surfaceHolder);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化小球
     */
    public void initMovables() {
        Random random = new Random();
        for (int i = 0; i < ballNumber; i++) {
            int index = random.nextInt(32); // 产生随机数
            Paint paint = new Paint();
            paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            Moveable m = new Moveable(0 + index, 70 - index, 30 + index, paint); // 创建Movable对象
            arrayList.add(m); // 将新建的Movable对象添加到ArrayList列表中
        }
    }

    /**
     * 绘制图片信息
     */
    public void doDraw(Canvas canvas) {
        // 绘制全屏
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawColor(Color.WHITE);// 绘制背景
        // 绘制一系列小球
        for (Moveable moveable : arrayList) {
            moveable.drawSelf(canvas);
        }
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(18);
        paint.setAntiAlias(true);
        canvas.drawText(fps, 30, 30, paint);// 绘制文字
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!drawThread.isAlive()) {
            drawThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

}
