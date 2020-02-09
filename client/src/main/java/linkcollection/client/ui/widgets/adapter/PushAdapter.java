package linkcollection.client.ui.widgets.adapter;

import entry.SearchInfo;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.client.ui.widgets.view.Adapter;
import user.Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("ALL")
public class PushAdapter extends Adapter<PushAdapter.ViewHolder> {
    private List<SearchInfo> data;
    private PushAdapter.ViewHolder selectedItemHolder;

    private static PushAdapter instance;

    public static PushAdapter getInstance() {
        if (instance == null) instance = new PushAdapter();
        return instance;
    }

    public static void refreshInstance(String types) {
        instance = new PushAdapter(types);
    }

    private PushAdapter() {
        data = new LinkedList<>();
    }

    public PushAdapter(String types) {
        data = Info.getPushContent(types);
    }

    @Override
    public PushAdapter.ViewHolder onCreateViewHolder(JPanel parent) {
        return new PushAdapter.ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(PushAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setBounds(0, position * 90, WidgetConstant.VisibleWidth - 200, 90);
        viewHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, WidgetConstant.BorderColor));
        SearchInfo info = data.get(position);
        viewHolder.title.setText("<html><u>" + info.getTitle() + "</u></html>");
        viewHolder.link.setText(info.getLink());
        viewHolder.labels.setText(info.getLabels());
        viewHolder.title.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO：根据设置的浏览器，打开浏览器
                openBrowser(viewHolder.link.getText());
                if (selectedItemHolder != null) {
                    selectedItemHolder.setBackground(WidgetConstant.NormalColor);
                }
                selectedItemHolder = viewHolder;
                selectedItemHolder.setBackground(WidgetConstant.SelectedColor);
                selectedItemHolder.title.setForeground(WidgetConstant.SelectedTitleColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                viewHolder.title.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewHolder.title.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        viewHolder.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO：根据设置的浏览器，打开浏览器
                openBrowser(viewHolder.link.getText());
                if (selectedItemHolder != null) {
                    selectedItemHolder.setBackground(WidgetConstant.NormalColor);
                }
                selectedItemHolder = viewHolder;
                selectedItemHolder.setBackground(WidgetConstant.SelectedColor);
                selectedItemHolder.title.setForeground(WidgetConstant.SelectedTitleColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (viewHolder.getBackground() != WidgetConstant.SelectedColor)
                    viewHolder.setBackground(WidgetConstant.EnterColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (viewHolder.getBackground() != WidgetConstant.SelectedColor)
                    viewHolder.setBackground(WidgetConstant.NormalColor);
            }
        });
    }

    public void openBrowser(String link) {
        if (!Desktop.isDesktopSupported()) return;
        URI uri = URI.create(link);
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            // 获取系统默认浏览器打开链接
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends JPanel {
        private JLabel title;
        private JLabel link;
        private JLabel labels;

        public ViewHolder(JPanel parent) {
            setLayout(null);
            title = new JLabel();
            link = new JLabel();
            labels = new JLabel();
            title.setFont(WidgetConstant.TitleFont);
            link.setFont(WidgetConstant.LinkFont);
            labels.setFont(WidgetConstant.LabelFont);
            title.setForeground(WidgetConstant.NormalTitleColor);
            title.setBounds(10, 0, 500, 50);
            link.setBounds(10, 50, 400, 20);
            labels.setBounds(10, 70, 400, 20);
            add(title);
            add(link);
            add(labels);
        }
    }
}
