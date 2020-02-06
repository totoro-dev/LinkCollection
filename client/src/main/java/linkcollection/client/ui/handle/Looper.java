package linkcollection.client.ui.handle;

public class Looper {

    public Looper() {

    }

    public static void loop() {
        new Loop().start();
    }

    static class Loop extends Thread {
        @Override
        public void run() {
            while (true) {
                if (MessageQueue.queue.size() > 0) {
                    MessageQueue.queue.get(0).target.handleMessage(MessageQueue.queue.get(0));
                    MessageQueue.queue.remove(0);
                } else {
                    try {
                        this.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
