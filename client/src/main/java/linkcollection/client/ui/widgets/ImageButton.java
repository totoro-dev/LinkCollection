package linkcollection.client.ui.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 图片主题的按钮
 */
public class ImageButton extends JLabel {

    public int x = 0, y = 0, w = 20, h = 20;

    private Class<?> parent;
    private ImageButtonListener listener;
    public String path;

    public ImageButton(Class<?> parent, String path) {
        this(parent);
        this.path = path;
        resize();
    }

    public ImageButton(Class<?> parent) {
        super();
        this.setBounds(x, y, w, h);
        setFont(WidgetConstant.TitleFont);
        this.parent = parent;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listener.click(path);
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
        });
    }

    public void addClickListener(ImageButtonListener listener) {
        this.listener = listener;
    }

    public void setIcon(String path) {
        ImageIcon image = new ImageIcon(path);
        image.setImage(image.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        super.setIcon(image);
        this.path = path;
    }

    public void setIcon(Class parent,String path){
        ImageIcon icon = new ImageIcon(parent.getClassLoader().getResource(path));
        icon.setImage(icon.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
        super.setIcon(icon);
        this.path = path;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        resize();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.w = width;
        this.h = height;
        resize();
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        this.x = x;
        this.y = y;
    }

    private void resize() {
        if (path != null) {
            ImageIcon image = null;
            if (path.contains(":")) {
                image = new ImageIcon(path);
            } else if (parent != null) {
                image = new ImageIcon(parent.getClassLoader().getResource(path));
            }
            if (image != null) {
                image.setImage(image.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
                super.setIcon(image);
            }
        }
    }
}
