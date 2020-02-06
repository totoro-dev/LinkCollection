package linkcollection.client.ui.widgets;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Toast extends JWindow {

    private JFrame parent;
    private ScheduledExecutorService service;

    public static final long SHORT = 1500;
    public static final long LONG = 3000;

    private Callable<String> close = new Callable<String>() {
        @Override
        public String call() throws Exception {
            dispose();
            return "";
        }
    };

    private Toast() {
        setLayout(null);
        service = Executors.newScheduledThreadPool(1);
    }

    public static Toast makeText(JFrame parent, String text) {
        WidgetConstant.reSetToastLocation(parent, text.length());
        int width = 25 * text.length();
        Toast toast = new Toast();
        toast.parent = parent;
        toast.setLocation(WidgetConstant.ToastLocation);
        toast.setSize(width, 30);
        JLabel textLabel = new JLabel(text, JLabel.CENTER) {
            @Override
            public void paint(Graphics g) {
                WidgetConstant.drawBorderFiveRadius(g, WidgetConstant.BorderColor, width);
                super.paint(g);
            }
        };
        textLabel.setBounds(0, 0, width, 30);
        textLabel.setFont(WidgetConstant.TitleFont);
        toast.add(textLabel);
        toast.getContentPane().setBackground(Color.white);
        toast.setVisible(true);
        return toast;
    }

    public void show(long delay) {
        service.schedule(close, delay, TimeUnit.MILLISECONDS);
    }

}
