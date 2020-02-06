package linkcollection.client.ui.bar;


import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.ImageButtonListener;
import linkcollection.client.ui.widgets.WidgetConstant;

import javax.swing.*;
import java.awt.*;

/**
 * 左侧选择栏
 */
public class LeftSelectBar extends JPanel implements ImageButtonListener {
    private JPanel container = new JPanel(null);
    private JPanel top = new JPanel(null);
    public static JPanel recycleLabelPanel = new JPanel(null);
    ;
    public static JScrollPane scrollPane = new JScrollPane();
    private ImageButton collection_selected;
    private ImageButton favor_selected;
    private ImageButton selected;

    public LeftSelectBar() {
        setLayout(new BorderLayout());
        top.setBounds(0, 0, 200, 40);
        top.setBackground(Color.white);

        initSelect("ui/img/collection-20x20-blue.png", "ui/img/favor-20x20.png", "ui/img/select-collection-200x6.png");

        scrollPane.setBounds(0, 40, 200, WidgetConstant.VisibleHeight - 90);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(WidgetConstant.NoneBorder);
        recycleLabelPanel.setPreferredSize(new Dimension(200, 0));
        recycleLabelPanel.setBackground(Color.white);
        scrollPane.getViewport().add(recycleLabelPanel);

        container.add(top);
        container.add(scrollPane);

        add(container);

        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, WidgetConstant.BorderColor));
    }

    @Override
    public void click(String flag) {
        if (flag.equals(collection_selected.path)) {
            selectedTab("ui/img/collection-20x20-blue.png", "ui/img/favor-20x20.png", "ui/img/select-collection-200x6.png");
            repaint();
        } else if (flag.equals(favor_selected.path)) {
            selectedTab("ui/img/collection-20x20.png", "ui/img/favor-20x20-blue.png", "ui/img/select-favor-200x6.png");
            repaint();
        }
    }

    void selectedTab(String path1, String path2, String path3) {
        top.remove(collection_selected);
        top.remove(favor_selected);
        top.remove(selected);
        initSelect(path1, path2, path3);
    }

    void initSelect(String path1, String path2, String path3) {
        collection_selected = new ImageButton(getClass(), path1);
        favor_selected = new ImageButton(getClass(), path2);
        selected = new ImageButton(getClass(), path3);
        collection_selected.setBounds(40, 7, 20, 20);
        favor_selected.setBounds(140, 7, 20, 20);
        selected.setBounds(0, 34, 200, 6);
        top.add(collection_selected);
        top.add(favor_selected);
        top.add(selected);
        addListener();
    }

    void addListener() {
        collection_selected.addClickListener(this::click);
        favor_selected.addClickListener(this::click);
    }
}
