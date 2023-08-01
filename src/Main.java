import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static boolean jobWithMap = false;


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int searchLetter(String letters, char letter) {
        int count = 0;
        for (int i = 0; i < letters.length(); i++) {
            if (letter == letters.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public static void maxCountRepit() {
        int[] intMassiv = {0, 0};
        int i = 1;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (intMassiv[0] < entry.getKey()) {
                intMassiv[0] = entry.getKey();
                intMassiv[1] = entry.getValue();
            }
        }
        System.out.println("******* Макс. количество повторений =  " + intMassiv[0] + " :: Встречаеться ( " + intMassiv[1] + " ) раз.");
    }


    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        MapThread thread = new MapThread();
        thread.start();

        int numberOfThreads = 1000;
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(() -> {
                String strRoute = generateRoute("RLRFR", 100);
                int numbOfOccur = searchLetter(strRoute, 'R');
                synchronized (sizeToFreq) {
                    while (jobWithMap) {
                        try {
                            sizeToFreq.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (!sizeToFreq.containsKey(numbOfOccur)) {
                        sizeToFreq.put(numbOfOccur, 1);
                    } else {
                        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
                            if (numbOfOccur == entry.getKey()) {
                                entry.setValue(entry.getValue() + 1);
                                break;
                            }
                        }
                    }
                    jobWithMap = true;
                    sizeToFreq.notifyAll();
                }
            }));
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads.get(i).start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads.get(i).join();
        }

        thread.interrupt();

        int i = 1;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println(i++ + ". Количество повторений =  " + entry.getKey() + " :: Встречаеться ( " + entry.getValue() + " ) раз.");
        }
        maxCountRepit();
    }
}








