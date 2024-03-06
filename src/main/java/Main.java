import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        List<MyThread> callable = new ArrayList<>();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
            callable.add(new MyThread(texts[i]));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(callable.size());
        List<Future<Integer>> futures = executorService.invokeAll(callable);

        Future<Integer> max = futures.stream().max(new Comparator<Future<Integer>>() {
            @Override
            public int compare(Future<Integer> o1, Future<Integer> o2) {
                try {
                    return o1.get().compareTo(o2.get());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        }).get();

        System.out.println("Максимальный интервал значений среди всех срок: " + max.get());

        executorService.shutdown();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
