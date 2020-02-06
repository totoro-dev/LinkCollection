package linkcollection.client.ui.widgets.view;

import javax.swing.*;

public abstract class Adapter<ViewHolder extends JPanel> {
    public abstract ViewHolder onCreateViewHolder(JPanel parent);
    public abstract void onBindViewHolder(ViewHolder holder, int position);
    public abstract int getItemCount();
}
