package MultitThreading;

/**
 * 通过银行取款的案例，加线程同步代码前存在的线程冲突现象
 */
class Account {
    private String accountNo;
    private double balance;

    public Account() {
    }

    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class DrawThread extends Thread {
    private Account account;
    private double drawMoney;

    public DrawThread(String name, Account account, double drawMoney) {
        super(name);
        this.account = account;
        this.drawMoney = drawMoney;
    }

    /**
     * 对应的取款线程
     */
    @Override
    public void run() {
        if (this.account.getBalance() >= this.drawMoney) {
            System.out.println(this.getName() + "取款成功,已取款 " + this.drawMoney + " 元");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //取前之后更新用户信息
            this.account.setBalance(this.account.getBalance() - this.drawMoney);
            System.out.println(this.account.getAccountNo() + "账户当前余额是：" + this.account.getBalance()+ " 元");
        } else {
            System.out.println(this.getName() + "余额不足，取钱失败");
        }
    }
}

public class DrawThreadTest1 {
    public static void main(String[] args) {
        Account account = new Account("8001", 1000);
        new DrawThread("老公", account, 600).start();
        new DrawThread("老婆", account, 800).start();
        /**
         * 老公取款成功,已取款 600.0 元
         * 老婆取款成功,已取款 800.0 元
         * 8001账户当前余额是：200.0 元
         * 8001账户当前余额是：200.0 元
         *
         * 可以看出来如果线程间不做并发限制，存在线程间冲突
         */
    }
}
