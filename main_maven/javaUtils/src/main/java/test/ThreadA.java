package test;

class ThreadA {
    public static void main(String[] args) {
        ThreadB b = new ThreadB();
        b.start();
        System.out.println("b is start.... : " + b.hashCode());
        synchronized (b) {
            try {
                System.out.println("Waiting for b to complete...");
                b.wait();
                System.out.println("Completed.Now back to main thread");
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Total is :" + b.total);
    }
}
class ThreadB extends Thread {
    int total;
    public void run() {
        try {
            System.out.println("this : " + this.hashCode());
            synchronized (this) {
                System.out.println("ThreadB is running..");
                for (int i = 0; i < 10; i++) {
                    total += i;
                    System.out.println("total is " + total);
                }
            }
        } catch (Exception e) { }
    }
}