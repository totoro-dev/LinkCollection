package linkcollection.client.ui.widgets.adapter;

import linkcollection.client.ui.MainContentPanel;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.client.ui.widgets.view.Adapter;
import user.Info;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LoveLabelAdapter extends Adapter<LoveLabelAdapter.ViewHolder> {

    private static ViewHolder selectedViewHolder = null;

    private List<String> dataInChinese;
    private List<String> dataInEnglish;

    private static LoveLabelAdapter instance;

    public static LoveLabelAdapter getInstance() {
        return instance;
    }

    public static void refreshInstance(String info) {
        instance = new LoveLabelAdapter(info);
    }

    private LoveLabelAdapter() {
        if (instance == null) {
            dataInChinese = new LinkedList<>();
            instance = new LoveLabelAdapter();
        }
    }

    public LoveLabelAdapter(String info) {
        if (info == null || info.length() == 0) {
            dataInEnglish = new LinkedList<>();
            dataInChinese = new LinkedList<>();
            return;
        }
        String[] loves = info.split(",");
        dataInEnglish = Arrays.asList(loves);
        dataInChinese = Arrays.asList(Info.getLovesInChinese(info).split(","));
    }

    @Override
    public ViewHolder onCreateViewHolder(JPanel parent) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setBounds(0, position * 30, 200, 30);
        holder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, WidgetConstant.BorderColor));
        holder.label.setText(dataInChinese.get(position));
        holder.label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (holder.getBackground() == WidgetConstant.SelectedColor) {
                    holder.setBackground(WidgetConstant.NormalColor);
                } else {
                    if (selectedViewHolder != null) {
                        selectedViewHolder.setBackground(WidgetConstant.NormalColor);
                        selectedViewHolder.remove(selectedViewHolder.imgBtn);
                    }
                    holder.setBackground(WidgetConstant.SelectedColor);
                    holder.add(holder.imgBtn);
                    selectedViewHolder = holder;
                    PushAdapter.refreshInstance(dataInEnglish.get(position));
                    MainContentPanel.showPushContent();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (holder.getBackground() != WidgetConstant.SelectedColor)
                    holder.setBackground(WidgetConstant.EnterColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (holder.getBackground() != WidgetConstant.SelectedColor)
                    holder.setBackground(WidgetConstant.NormalColor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataInChinese.size();
    }

    public class ViewHolder extends JPanel {
        public JLabel label;
        public ImageButton imgBtn;

        public ViewHolder(JPanel panel) {
            setLayout(null);
            label = new JLabel();
            imgBtn = new ImageButton(getClass(), "ui/img/right.png");
            label.setFont(WidgetConstant.LabelFont);
            label.setBounds(10, 0, 150, 30);
            imgBtn.setBounds(170, 7, 16, 16);
            imgBtn.addClickListener(flag -> {
            });
            add(label);
        }
    }
}
