package linkcollection.client.ui.handle;

public class Handler {

    public void handleMessage(Message msg) {
    }

    public void sendMessage(Message msg) {
        msg.target = this;
        MessageQueue.queue.add(msg);
    }

}
