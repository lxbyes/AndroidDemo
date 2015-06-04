package me.leckie.demo.ball;

/**
 * Created by leckie on 6/4/15.
 */
public class BallThread extends Thread {
    private Moveable moveable;// 小球对象
    private boolean flag = false;// 线程标识
    private int sleepSpan = 10;// 休眠时间
    private float g = 200;// 重力加速度
    private double currentTime;// 记录当前事件

    /**
     * 构造方法
     *
     * @param moveable
     */
    public BallThread(Moveable moveable) {
        super();
        this.moveable = moveable;
        this.flag = true;
    }

    @Override
    public void run() {
        while (flag) {
            currentTime = System.nanoTime();// 获取当前时间，单位为纳秒
            double timeSpanX = (double) ((currentTime - moveable.timeX) / 1000 / 1000 / 1000);// 获取从玩家开始到现在水平方向走过的时间
            // 处理水平方向上的运动
            moveable.x = (int) (moveable.startX + moveable.v_x * timeSpanX);
            // 处理竖直方向上的运动
            if (moveable.bFall) {
                double timeSpanY = (double) ((currentTime - moveable.timeY) / 1000 / 1000 / 1000);
                moveable.y = (int) (moveable.startY + moveable.startVY
                        * timeSpanY + timeSpanY * timeSpanY * g / 2);
                moveable.v_y = (float) (moveable.startVY + g * timeSpanY);
                // 判断小球是否到达最高点
                if (moveable.startVY < 0
                        && Math.abs(moveable.v_y) <= BallView.UP_ZERO) {
                    moveable.timeY = System.nanoTime(); // 设置新的运动阶段竖直方向上的开始时间
                    moveable.v_y = 0; // 设置新的运动阶段竖直方向上的实时速度
                    moveable.startVY = 0; // 设置新的运动阶段竖直方向上的初始速度
                    moveable.startY = moveable.y; // 设置新的运动阶段竖直方向上的初始位置
                }
                // 判断小球是否撞地
                if (moveable.y + moveable.r * 2 >= BallView.GROUND_LING
                        && moveable.v_y > 0) {// 判断撞地条件
                    // 改变水平方向的速度
                    moveable.v_x = moveable.v_x * (1 - moveable.impactFactor); // 衰减水平方向上的速度
                    // 改变竖直方向的速度
                    moveable.v_y = 0 - moveable.v_y
                            * (1 - moveable.impactFactor); // 衰减竖直方向上的速度并改变方向
                    if (Math.abs(moveable.v_y) < BallView.DOWN_ZERO) { // 判断撞地后的速度，太小就停止
                        this.flag = false;
                    } else { // 撞地后的速度还可以弹起继续下一阶段的运动
                        // 撞地之后水平方向的变化
                        moveable.startX = moveable.x; // 设置新的运动阶段的水平方向的起始位置
                        moveable.timeX = System.nanoTime(); // 设置新的运动阶段的水平方向的开始时间
                        // 撞地之后竖直方向的变化
                        moveable.startY = moveable.y; // 设置新的运动阶段竖直方向上的起始位置
                        moveable.timeY = System.nanoTime(); // 设置新的运动阶段竖直方向开始运动的时间
                        moveable.startVY = moveable.v_y; // 设置新的运动阶段竖直方向上的初速度
                    }
                }
            } else if (moveable.x + moveable.r / 2 >= BallView.WOOD_EDGE) {// 判断球是否移出了挡板
                moveable.timeY = System.nanoTime(); // 记录球竖直方向上的开始运动时间
                moveable.bFall = true; // 设置表示是否开始下落标志位
            }
            try {
                Thread.sleep(sleepSpan); // 休眠一段时间
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
