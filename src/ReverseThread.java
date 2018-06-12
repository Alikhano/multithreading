
public class ReverseThread extends Thread {
    private int count;

    public static void main(String[] args) {
        Thread t = new ReverseThread(50);
        t.start();

    }

    @Override
    public void run() {
        System.out.println("Hello from thread " + count);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (count > 1) {
            createNewtT(count);
        }

    }

    public ReverseThread(int count) {
        this.count = count;
    }

    public static void createNewtT(int count) {
        Thread t = new ReverseThread(count - 1);
        t.start();
    }
}
