import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
            threads.add(new MyThread(texts[i]));
        }



        long startTs = System.currentTimeMillis(); // start time
        for (Thread thread : threads) {
            thread.start();
            thread.join();
            thread.interrupt();
        }
        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
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
