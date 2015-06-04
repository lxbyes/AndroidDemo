package me.leckie.demo.ball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import me.leckie.demo.R;

/**
 * Created by leckie on 6/4/15.
 */
public class BallView extends SurfaceView implements SurfaceHolder.Callback {
    static final int V_MAX = 35;// 小球水平速度的最大值
    static final int V_MIN = 15;// 小球水平速度的最小值

    public static final int WOOD_EDGE = 60;// 木板右边沿的X坐标

    static final int GROUND_LING = 730;// 代表地面Y的坐标

    static final int UP_ZERO = 30;// 小球在上升的过程中，如果小于此数则为0，

    static final int DOWN_ZERO = 60;// 小球在下落的过程中，如果速度小于该值则为0.

    Bitmap bitmapArray[] = new Bitmap[6];// 图片数组
    Bitmap bmpBack;// 背景图片背景
    Bitmap bmpWood;// 模板图片背景

    String fps = "FPS:N/A";// 用于显示帧率的字符串

    int ballNumber = 8;// 小球个数

    ArrayList<Moveable> arrayList = new ArrayList<Moveable>();// 小球对象数组

    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread;

    public BallView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        initBitmaps(getResources());
        initMovables();
        drawThread = new DrawThread(this, surfaceHolder);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化图片数据
     *
     * @param resources
     */
    void initBitmaps(Resources resources) {
        bitmapArray[0] = BitmapFactory.decodeResource(resources,
                R.drawable.ball_red); // 红色较小球
        bitmapArray[1] = BitmapFactory.decodeResource(resources,
                R.drawable.ball_red); // 紫色较小球
        bitmapArray[2] = BitmapFactory.decodeResource(resources,
                R.drawable.ball_red); // 绿色较小球
        bitmapArray[3] = BitmapFactory.decodeResource(resources,
                R.drawable.ball_red); // 红色较大球
        bitmapArray[4] = BitmapFactory.decodeResource(resources,
                R.drawable.ball_red); // 紫色较大球
        bitmapArray[5] = BitmapFactory.decodeResource(resources,
                R.drawable.ball_red); // 绿色较大球
        bmpBack = BitmapFactory.decodeResource(resources, R.drawable.back); // 背景砖墙
        bmpWood = BitmapFactory.decodeResource(resources, R.drawable.wood); // 木板
    }

    /**
     * 初始化小球
     */
    public void initMovables() {
        Random random = new Random();
        for (int i = 0; i < ballNumber; i++) {
            int index = random.nextInt(32); // 产生随机数
            Bitmap tempBitmap = null; // 声明一个Bitmap图片引用
            if (i < ballNumber / 2) {
                tempBitmap = bitmapArray[3 + index % 3];// 如果是初始化前一半球，就从大球中随机找一个
            } else {
                tempBitmap = bitmapArray[index % 3];// 如果是初始化后一半球，就从小球中随机找一个
            }
            Moveable m = new Moveable(0, 70 - tempBitmap.getHeight(),
                    tempBitmap.getWidth() / 2, tempBitmap); // 创建Movable对象
            arrayList.add(m); // 将新建的Movable对象添加到ArrayList列表中
        }
    }

    /**
     * 绘制图片信息
     */
    public void doDraw(Canvas canvas) {
        // 绘制全屏
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(bmpBack, null, rectF, null);// 绘制背景
        canvas.drawBitmap(bmpWood, 0, 60, null);// 绘制木板
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
        if (!drawThread.isAlive())
            drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.flag = false;
        drawThread = null;

    }

}
