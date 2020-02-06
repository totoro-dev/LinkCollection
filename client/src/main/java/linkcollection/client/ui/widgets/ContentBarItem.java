package linkcollection.client.ui.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ContentBarItem extends JLabel implements MouseListener {

    private String name;
    private ContentBarItemListener listener;

    public ContentBarItem(String text) {
        super(text, JLabel.CENTER);
        name = text;
        setFont(new Font("DialogInput", 0, 14));
        addMouseListener(this);
    }

    public void addListener(ContentBarItemListener listener) {
        this.listener = listener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        listener.click(name);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
