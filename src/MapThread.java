public class MapThread extends Thread {
    public void run() {
        while (!interrupted()) {
            synchronized (Main.sizeToFreq) {
                while (!Main.jobWithMap) {
                    try {
                        Main.sizeToFreq.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                Main.maxCountRepit();
                Main.jobWithMap = false;
                Main.sizeToFreq.notifyAll();
            }
        }
    }
}
