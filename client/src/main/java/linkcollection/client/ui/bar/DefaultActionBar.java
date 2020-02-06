package linkcollection.client.ui.bar;

import linkcollection.client.ui.widgets.ActionBar;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.ImageButtonListener;
import linkcollection.client.ui.widgets.WidgetConstant;

import javax.swing.*;

@SuppressWarnings("Duplicates")
public class DefaultActionBar extends ActionBar implements ImageButtonListener {

    private JFrame parent;
    private JLabel title;
    private ImageButton min, exit;

    public DefaultActionBar(JFrame parent) {
        super(parent);
        this.parent = parent;
        setLayout(null);
        setBackground(WidgetConstant.ThemeColor);
        addOptPanel();
    }

    private void addOptPanel() {
        int optWidth = 30;
        title = new JLabel("");
        min = new ImageButton(getClass(), "ui/img/min-16x16.png");
        exit = new ImageButton(getClass(), "ui/img/exit-16x16.png");
        min.setSize(16, 16);
        exit.setSize(16, 16);
        title.setFont(WidgetConstant.TitleFont);
        title.setBounds(10,0,100,50);
        min.setLocation(WidgetConstant.VisibleWidth - 2 * optWidth - 10, 15);
        exit.setLocation(WidgetConstant.VisibleWidth - optWidth - 10, 15);
        add(title);
        add(min);
        add(exit);
        min.addClickListener(this::click);
        exit.addClickListener(this::click);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    @Override
    public void click(String flag) {
        if (flag.equals(min.path)) {
            parent.setExtendedState(JFrame.ICONIFIED);
        } else if (flag.equals(exit.path)) {
            parent.dispose();
        }
    }

}