import java.util.Random;
import java.util.concurrent.*;

class MaxThread implements Callable<Integer>{
    private int max;
    private int lo;
    private int hi;
    private int[] arr;

    public MaxThread(int[] arr, int lo, int hi) {
      this.arr = arr;
      this.lo = lo;
      this.hi = hi;
    }

    @Override
    public Integer call() {
        for (int i = lo; i < hi; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
            else continue;
            //System.out.println("between is " + max);
        }
        return max;
    }

    public static int findMax(int[] array) throws InterruptedException, ExecutionException {
        int len = array.length;
        int max = 0;
        Future[] futures = new Future[4];

        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 4; i++) {
            Callable<Integer> worker = new MaxThread(array, (i*len)/4, ((i + 1) * len / 4));
            Future<Integer> future = (Future<Integer>)pool.submit(worker);
            futures[i] = future;

        }

        for (int i = 0; i < 4; i++) {
            int number = (int) futures[i].get();
            if (max < number) {
                max = number;
            } else continue;
        }
        pool.shutdown();

        return max;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int arr[] = new int[10];
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            arr[i] = random.nextInt();

        }
        int max = findMax(arr);
        System.out.println("max is " + max);
    }
}


