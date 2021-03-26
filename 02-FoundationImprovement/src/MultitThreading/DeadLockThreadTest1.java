package MultitThreading;

/**
 * 通过化妆案例演示死锁，同步代码块嵌套导致的线程死锁
 */

/**
 * 口红类
 */
class Lipstick {

}

/**
 * 镜子类
 */

class Mirror {

}

/**
 * 化妆线程类
 */
class Makeup extends Thread {
    private int flag = 0;
    /**
     * flag = 0,拿着口红
     * flag != 0,拿着镜子
     */
    private String girlName;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setGirlName(String girlName) {
        this.girlName = girlName;
    }

    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    @Override
    public void run() {
        this.doMakeup();
    }

    public void doMakeup() {
        if (flag == 0) {
            synchronized (lipstick){
                System.out.println(this.girlName+"拿着口红");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (mirror){
                System.out.println(this.girlName+"拿着镜子");
            }
        }else {
            synchronized (mirror){
                System.out.println(this.girlName+"拿着镜子");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (lipstick){
                System.out.println(this.girlName+"拿着口红");
            }
        }
    }
}

public class DeadLockThreadTest1 {
    public static void main(String[] args) {
        Makeup makeup1 = new Makeup();
        makeup1.setFlag(0);
        makeup1.setGirlName("小红");
        Makeup makeup2 = new Makeup();
        makeup2.setFlag(1);
        makeup2.setGirlName("小花");
        makeup1.start();
        makeup2.start();
        /**
         * 小红拿着口红
         * 小花拿着镜子
         * 小花拿着口红
         * 小红拿着镜子
         *
         * 根据输出结果可以看到
         * 小红和小花不能同时拿到镜子和口红，释放不了资源，陷入死锁状态
         */
    }
}
