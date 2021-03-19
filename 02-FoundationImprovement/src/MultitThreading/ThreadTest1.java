package MultitThreading;

/**
 * 测试下靠继承Thread类实现线程
 */
public class ThreadTest1 extends Thread {
    public ThreadTest1() {
        System.out.println(this.getName());
        //this.getName返回当前线程名称,getName是Thread类中方法
    }

    @Override
    public void run() {
        System.out.println("线程开始");
        for (int i = 1; i < 20; i++) {
            System.out.println(this.getName() + "线程执行的第" + i + "次");
        }
        System.out.println("线程结束");
    }

    public static void main(String[] args) {
        System.out.println("主线程开始");

        ThreadTest1 threadTest1 = new ThreadTest1();
        threadTest1.start();

        ThreadTest1 threadTest11 = new ThreadTest1();
        threadTest11.start();

        System.out.println("主线程结束");
    }
    /**
     * 会发现主线程结束后，子线程继续执行
     * 线程间并不完全执行，某一线程结束后，另外一个线程可能还在执行
     * 线程并发执行，彼此间没有做限制
     */
}
