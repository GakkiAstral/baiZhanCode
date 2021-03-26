package MultitThreading;

/**
 * 通过生产者消费者馒头的案例，实现线程同步
 */

/**
 * 定义一个馒头类
 */
class Mantou {
    private int id;

    public Mantou(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

/**
 * 定义缓冲区类，只用来存放而不实现缓冲
 */
class SyncStack {
    private Mantou[] mt = new Mantou[10];
    private int index;
}


public class ProduceThread {
}
