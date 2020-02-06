package linkcollection.client.ui.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActionBar extends JPanel implements MouseListener {

    private JFrame parent;

    private int gapX = 0, gapY = 0;
    private int mouseX, mouseY;
    private Lock lock = new ReentrantLock();
    private ScheduledFuture task;
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    private Runnable move = new Runnable() {
        @Override
        public void run() {
            try {
                lock.lock();
                Point p = MouseInfo.getPointerInfo().getLocation();
                mouseX = (int) p.getX();
                mouseY = (int) p.getY();
                parent.setLocation(mouseX - gapX, mouseY - gapY);
                WidgetConstant.ToastLocation = new Point(mouseX - gapX + 230, mouseY - gapY + 400);
            } finally {
                lock.unlock();
            }
        }
    };

    public ActionBar(JFrame parent) {
        this.parent = parent;
        addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            lock.lock();
            Point p = MouseInfo.getPointerInfo().getLocation();
            mouseX = (int) p.getX();
            mouseY = (int) p.getY();
            gapX = mouseX - parent.getX();
            gapY = mouseY - parent.getY();
            task = service.scheduleAtFixedRate(move, 0, 40, TimeUnit.MILLISECONDS);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        task.cancel(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}