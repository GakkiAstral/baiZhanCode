package MultitThreading;

/**
 * Thread类中的isAlive()方法可以知道当前线程的运行状态
 * 返回的结果是布尔类型，线程运行时为true，其他状态是false
 */
class Alive implements Runnable {
    @Override
    public void run() {
        System.out.println("子线程启动");
        System.out.println(Thread.currentThread().isAlive() + " 子线程运行中");
        for (int i = 1; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadIsAlive {
    public static void main(String[] args) {
        System.out.println("主线程启动");
        Alive alive1 = new Alive();
        Thread thread1 = new Thread(alive1);
        System.out.println(thread1.isAlive() + " 子线程运行前");
        thread1.start();
        System.out.println(thread1.isAlive() + " 子线程运行后");
        System.out.println("主线程结束");
    }
}
