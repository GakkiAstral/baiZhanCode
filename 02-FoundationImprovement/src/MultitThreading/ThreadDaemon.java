package MultitThreading;

/**
 * 给一个用户线程设置一个守护线程
 * 用户线程死亡后，守护线程也会死亡
 */

class Daemon implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i < 20; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class UserThread implements Runnable {
    @Override
    public void run() {
        Thread t = new Thread(new Daemon(), "Daemon");
        //将该线程设置为守护线程
        t.setDaemon(true);
        t.start();
        for (int i = 1; i < 20; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadDaemon {
    public static void main(String[] args) {
        System.out.println("主线程开始");
        Thread thread = new Thread(new UserThread(),"UserThread");
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");
    }
    /**
     * 除主线程以外，并发执行着两个线程。UserThread和Daemon两个线程
     * 将Daemon设置为用户线程的守护线程，
     * 当用户线程执行完毕死亡后，守护线程立即死亡，不继续执行。
     */
}
