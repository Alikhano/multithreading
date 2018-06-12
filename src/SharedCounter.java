import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedCounter implements Callable<AtomicInteger> {
    private static AtomicInteger counter;

    @Override
    public AtomicInteger call() throws Exception {
        counter.addAndGet(10);
        return counter;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int numOfThreads = 10;
        ExecutorService pool = Executors.newFixedThreadPool(numOfThreads);
        List<Future<AtomicInteger>> futures = new ArrayList<>();
        List<Callable<AtomicInteger>> list = new ArrayList<>();
        for (int i = 0; i <numOfThreads; i++) {
            Callable worker = new SharedCounter(new AtomicInteger(0));
            list.add(worker);
        }
        futures =  pool.invokeAll(list);
        System.out.println(futures.get(numOfThreads-1).get());
    }

    public SharedCounter(AtomicInteger counter) {
        this.counter = counter;
    }
}
