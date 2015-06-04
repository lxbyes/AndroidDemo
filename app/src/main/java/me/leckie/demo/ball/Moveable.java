package me.leckie.demo.ball;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by leckie on 6/4/15.
 */
public class Moveable {
    int startX = 0;// 初始X坐标
    int startY = 0;// 初始Y坐标
    int x;// 时时X,
    int y;// 时时Y

    float startVX = 0f;// 初始竖直方向的速度
    float startVY = 0f;// 初始水平方向的速度

    float v_x = 0f;// 时时竖直方向速度
    float v_y = 0f;// 时时水平方向速度

    int r;// 半径

    double timeX;// 水平运动时间
    double timeY;// 竖直运动时间

    Paint paint = null;

    BallThread bt = null;

    boolean bFall = false;// 小球是否已经从木板下落

    float impactFactor = 0.25f;// 小球撞地后速度的损失系数.

    /**
     * 构造方法
     *
     * @param x
     * @param y
     * @param r
     * @param paint
     */
    public Moveable(int x, int y, int r, Paint paint) {
        super();
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.r = r;
        this.paint = paint;
        timeX = System.nanoTime();
        this.v_x = BallView.V_MIN
                + (int) ((BallView.V_MAX - BallView.V_MIN) * Math.random());// 初始水平速度
        bt = new BallThread(this);// 创建并启动BallThread
        bt.start();
    }

    /**
     * 绘画
     */
    public void drawSelf(Canvas canvas) {
        canvas.drawCircle(x, y, r, paint);
    }
}
