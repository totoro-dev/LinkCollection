package linkcollection.client.ui.frame;

import linkcollection.client.ui.bar.DefaultActionBar;
import linkcollection.client.ui.widgets.WidgetConstant;

import javax.swing.JFrame;

/**
 * 设置页面
 */
public class SettingFrame extends JFrame {

    private JFrame parent;

    public SettingFrame(JFrame parent) {
        this.parent = parent;
        setUndecorated(true);
        WidgetConstant.setVisibleSize(400, 300);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLocation(WidgetConstant.getCenterLocation());
        setLayout(null);
        DefaultActionBar actionBar = new DefaultActionBar(this);
        actionBar.setBounds(0, 0, WidgetConstant.VisibleWidth, 50);
        actionBar.setTitle("设置");
        add(actionBar);
        setVisible(true);
    }

    @Override
    public void dispose() {
        super.dispose();
        WidgetConstant.rollBackVisibleSize();
        parent.setVisible(true);
    }
}
