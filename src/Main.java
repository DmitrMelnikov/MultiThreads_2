import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

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

    public static void main(String[] args) {
        int numberOfThreads = 1000;
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(() -> {
                String strRoute = generateRoute("RLRFR", 100);
                int numbOfOccur = searchLetter(strRoute, 'R');
                synchronized (sizeToFreq) {
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
                }
            }).start();
        }
        int i = 1;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println(i++ + ". Количество повторений =  " + entry.getKey() + " :: Встречаеться ( " + entry.getValue() + " ) раз.");
        }
    }
}

