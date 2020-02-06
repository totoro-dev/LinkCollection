package linkcollection.client.ui.widgets.view;

import linkcollection.client.ui.widgets.WidgetConstant;

import javax.swing.*;
import java.awt.*;

public class RecyclerView {

    private JPanel context;

    public RecyclerView(JPanel context) {
        this.context = context;
    }

    public void setAdapter(Adapter adapter) {
        int count = adapter.getItemCount();
        int height = 0;
        for (int i = 0; i < count; i++) {
            JPanel holder = adapter.onCreateViewHolder(context);
            adapter.onBindViewHolder(holder, i);
            height += holder.getHeight();
            context.add(holder);
        }
        context.setPreferredSize(new Dimension(WidgetConstant.VisibleWidth - 200, height));
        context.repaint();
    }
}
