package linkcollection.client.ui.widgets.adapter;

import entry.SearchInfo;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.client.ui.widgets.view.Adapter;
import search.Search;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

/**
 * 全网搜索的列表适配器
 */
@SuppressWarnings("ALL")
public class SearchAdapter extends Adapter<SearchAdapter.ViewHolder> {

    private List<SearchInfo> data;
    private SearchAdapter.ViewHolder selectedItemHolder;

    private static SearchAdapter instance;

    public static SearchAdapter getInstance() {
        if (instance == null) instance = new SearchAdapter();
        return instance;
    }

    public static void refreshInstance(String key) {
        instance = new SearchAdapter(key);
    }

    private SearchAdapter() {
        data = new LinkedList<>();
    }

    public SearchAdapter(String key) {
        data = Search.searchInService(key);
    }

    @Override
    public ViewHolder onCreateViewHolder(JPanel parent) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setBounds(0, position * 90, WidgetConstant.VisibleWidth - 200, 90);
        viewHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, WidgetConstant.BorderColor));
        SearchInfo info = data.get(position);
        viewHolder.title.setText("<html><u>" + info.getTitle() + "</u></html>");
        viewHolder.link.setText(info.getLink());
        viewHolder.labels.setText(info.getLabels().replace(",", "，"));
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

    public static class ViewHolder extends JPanel {
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
