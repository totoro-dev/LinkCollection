package linkcollection.client.ui.widgets.adapter;

import entry.CollectionInfo;
import linkcollection.client.ui.MainContentPanel;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.client.ui.widgets.view.Adapter;
import user.Info;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LabelAdapter extends Adapter<LabelAdapter.LabelHolder> {

    public static Map<String, List<CollectionInfo>> itemMap = new LinkedHashMap<>();
    private static LabelHolder selectedLabelHolder = null;
    private List<CollectionInfo> data;
    private List<String> labelList = new LinkedList<>();

    public LabelAdapter() {
        data = Info.getCollectionInfos();
        if (data == null || data.size() == 0) return;
        String labelJoin = "";
        for (CollectionInfo info :
                data) {
            String[] ls = info.getLabels();
            for (String l :
                    ls) {
                if (labelJoin.equals("," + l) || labelJoin.endsWith("," + l) || labelJoin.contains("," + l + ",")) {
                    continue;
                } else {
                    labelJoin += "," + l;
                    labelList.add(l);
                }
                List<CollectionInfo> list = itemMap.get(l);
                if (list == null) {
                    list = new LinkedList<>();
                    itemMap.put(l, list);
                }
                list.add(info);
            }
        }
    }

    public List<CollectionInfo> getItemList() {
        if (selectedLabelHolder == null) return new LinkedList<>();
        return itemMap.get(selectedLabelHolder.label.getText());
    }

    @Override
    public LabelHolder onCreateViewHolder(JPanel parent) {
        return new LabelHolder(parent);
    }

    @Override
    public void onBindViewHolder(LabelHolder holder, int position) {
        holder.setBounds(0, position * 30, 200, 30);
        holder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, WidgetConstant.BorderColor));
        holder.label.setText(labelList.get(position));
        holder.label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (holder.getBackground() == WidgetConstant.SelectedColor) {
                    holder.setBackground(WidgetConstant.NormalColor);
                } else {
                    if (selectedLabelHolder != null) {
                        selectedLabelHolder.setBackground(WidgetConstant.NormalColor);
                        selectedLabelHolder.remove(selectedLabelHolder.imgBtn);
                    }
                    holder.setBackground(WidgetConstant.SelectedColor);
                    holder.add(holder.imgBtn);
                    selectedLabelHolder = holder;
                    ItemAdapter.refreshInstance(getItemList());
                    MainContentPanel.showMyContent();
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
        return labelList.size();
    }

    public class LabelHolder extends JPanel {
        public JLabel label;
        public ImageButton imgBtn;

        public LabelHolder(JPanel panel) {
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
