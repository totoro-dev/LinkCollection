package linkcollection.client.test;


import linkcollection.client.ui.bar.LeftSelectBar;
import linkcollection.client.ui.bar.MainActionBar;
import linkcollection.client.ui.MainContentPanel;
import linkcollection.client.ui.widgets.WidgetConstant;

import javax.swing.*;
import java.awt.*;

public class UITest extends JFrame {
    public UITest() {
        setUndecorated(true);
        WidgetConstant.setVisibleSize(800, 600);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLayout(null);

        MainActionBar actionBar = new MainActionBar(this);
        actionBar.setBounds(0, 0, WidgetConstant.VisibleWidth, 50);
        add(actionBar);

        LeftSelectBar leftSelectBar = new LeftSelectBar();
        leftSelectBar.setBounds(0, 50, 200, WidgetConstant.VisibleHeight - 50);
        add(leftSelectBar);

        MainContentPanel mainContentPanel = new MainContentPanel();
        mainContentPanel.setBounds(200, 50, WidgetConstant.VisibleWidth - 200, WidgetConstant.VisibleHeight - 50);
        add(mainContentPanel);

//        setSize(300,300);
//        JPanel instance = new JPanel(null);
//        JPanel content = new JPanel(null);
//        JScrollPane scrollPane = new JScrollPane();
//
//        instance.setBounds(0,0,300,300);
//        scrollPane.setBounds(0,0,300,200);
//        content.setPreferredSize(new Dimension(300,300));
//
//        add(instance);
//        instance.add(scrollPane);
//        scrollPane.getViewport().add(content);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new UITest();
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(1);
    }
}
