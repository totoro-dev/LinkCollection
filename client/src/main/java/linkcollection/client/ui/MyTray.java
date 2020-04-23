package linkcollection.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyTray implements ActionListener, MouseListener {
    private Image icon;// 图标
    private TrayIcon trayIcon;
    private SystemTray systemTray;// 系统托盘

    private JFrame frame; // 托盘所属主窗体
    private PopupMenu pop = new PopupMenu(); // 弹出菜单
    private MenuItem show = new MenuItem("open");
    private MenuItem exit = new MenuItem("exit");

    public MyTray(JFrame frame) {
        this.frame = frame;
        icon = new ImageIcon(this.getClass().getClassLoader().getResource(
                "ui/img/sys.png")).getImage();

        if (SystemTray.isSupported()) {
            systemTray = SystemTray.getSystemTray();
            trayIcon = new TrayIcon(icon, "Link Collection", pop);
            pop.add(show);
            pop.add(exit);

            try {
                systemTray.add(trayIcon);
            } catch (AWTException e1) {
                e1.printStackTrace();
                trayIcon.addMouseListener(this);
            }
        }
        trayIcon.addMouseListener(this);
        show.addActionListener(this);
        exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == show) {
            frame.setVisible(true);
            frame.setExtendedState(JFrame.NORMAL);
        } else if (e.getSource() == exit) {
            System.exit(0);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1 && e.getButton() != MouseEvent.BUTTON3) {
            frame.setVisible(true);
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }
}
