package MultitThreading;

/**
 * 测试下靠实现Runnable接口实现线程
 */
public class ThreadTest2 implements Runnable {
    public ThreadTest2() {
        System.out.println(Thread.currentThread().getName());
        //实现Runnable接口区别于继承Thread类，调线程名称的时候需要先通过Thread类获取当前线程才能获取
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程开始！");
        for (int i = 1; i < 20; i++) {
            System.out.println(Thread.currentThread().getName() + "线程执行了等" + i + "次");
        }
        System.out.println(Thread.currentThread().getName() + "线程结束！");
    }

    public static void main(String[] args) {
        System.out.println("主线程开始");
        ThreadTest2 threadTest2 = new ThreadTest2();
        Thread thread2 = new Thread(threadTest2);
        thread2.start();

        ThreadTest2 threadTest22 = new ThreadTest2();
        Thread thread22 = new Thread(threadTest22);
        thread22.start();

        System.out.println("主线程关闭");
    }
}
