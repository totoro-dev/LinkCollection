package linkcollection.client.ui.frame;

import linkcollection.client.ui.bar.LeftSelectBar;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.Toast;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.client.ui.widgets.adapter.CollectLabelAdapter;
import linkcollection.client.ui.widgets.view.RecyclerView;
import user.Info;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class CollectionFrame extends JFrame {
    private JFrame parent;
    public static CollectionFrame context;

    private static JLabel title = new JLabel("<html><u>百度一下，你就知道</u></html");
    private static JLabel link = new JLabel("https://baidu.com");
    private JLabel label_1 = new JLabel("标签1");
    private JLabel label_2 = new JLabel("标签2");
    private JLabel label_3 = new JLabel("标签3");
    private static JTextField label_1_Field = new JTextField();
    private static JTextField label_2_Field = new JTextField();
    private static JTextField label_3_Field = new JTextField();
    private ImageButton sure = new ImageButton(getClass(), "ui/img/sure.png"), cancel = new ImageButton(getClass(), "ui/img/cancel.png");

    private int startX = 15, startY = 10;

    private static boolean putable = false;

    public CollectionFrame(JFrame parent) {
        getContentPane().setBackground(Color.white);
        context = this;
        this.parent = parent;
//        parent.setVisible(false);
        setUndecorated(true);
        WidgetConstant.setVisibleSize(500, 200);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLocation(WidgetConstant.getCenterLocation());
        getContentPane().setLayout(null);

        title.setForeground(WidgetConstant.NormalTitleColor);
        title.setFont(WidgetConstant.TitleFont);
        link.setFont(WidgetConstant.LinkFont);
        label_1.setFont(WidgetConstant.LabelFont);
        label_2.setFont(WidgetConstant.LabelFont);
        label_3.setFont(WidgetConstant.LabelFont);
        label_1_Field.setFont(WidgetConstant.TitleFont);
        label_2_Field.setFont(WidgetConstant.TitleFont);
        label_3_Field.setFont(WidgetConstant.TitleFont);

        title.setBounds(startX, startY, 500 - 2 * startX, 60);
        link.setBounds(startX, startY + 60, 400, 20);
        label_1.setBounds(startX, startY + 80, 100, 30);
        label_2.setBounds(startX + 160, startY + 80, 100, 30);
        label_3.setBounds(startX + 320, startY + 80, 100, 30);
        label_1_Field.setBounds(startX, startY + 110, 150, 30);
        label_2_Field.setBounds(startX + 160, startY + 110, 150, 30);
        label_3_Field.setBounds(startX + 320, startY + 110, 150, 30);
        sure.setBounds(125, startY + 150, 100, 30);
        cancel.setBounds(275, startY + 150, 100, 30);

        add(title);
        add(link);
        add(label_1);
        add(label_2);
        add(label_3);
        add(label_1_Field);
        add(label_2_Field);
        add(label_3_Field);
        add(sure);
        add(cancel);

        sure.addClickListener(flag -> {
            String l1 = label_1_Field.getText();
            String l2 = label_2_Field.getText();
            String l3 = label_3_Field.getText();
            LinkedList<String> list = new LinkedList<>();
            if (!"".equals(l1)) list.add(l1);
            if (!"".equals(l2)) list.add(l2);
            if (!"".equals(l3)) list.add(l3);
            String labels = list.stream().collect(Collectors.joining(","));
            if (Info.addCollection(link.getText(), labels, title.getText())) {
                CollectLabelAdapter.refreshInstance(Info.getCollectionInfos());
                new RecyclerView(LeftSelectBar.contentPanel).setAdapter(CollectLabelAdapter.getInstance());
                Toast.makeText(this, "收藏成功").show(Toast.SHORT);
            } else {
                Toast.makeText(this, "收藏失败").show(Toast.SHORT);
            }
            setVisible(false);
        });
//        setVisible(true);
        cancel.addClickListener(flag -> setVisible(false));
        setAlwaysOnTop(true);
    }

    public static void setLinkTitle(String title) {
        CollectionFrame.title.setText("<html><u>" + title + "</u></html");
    }

    public static void setLink(String link) {
        CollectionFrame.link.setText(link);
    }

    public static void setLabel_1_Field(String label_1_Field) {
        CollectionFrame.label_1_Field.setText(label_1_Field);
    }

    public static void setLabel_2_Field(String label_2_Field) {
        CollectionFrame.label_2_Field.setText(label_2_Field);
    }

    public static void setLabel_3_Field(String label_3_Field) {
        CollectionFrame.label_3_Field.setText(label_3_Field);
    }

    public static void setPutable(boolean putable) {
        CollectionFrame.putable = putable;
    }

    public static void main(String[] args) {
//        new CollectionFrame(MainFrame.context);
        System.out.println(Arrays.stream(new String[]{"a", "b"}).collect(Collectors.joining(",")));
    }
}