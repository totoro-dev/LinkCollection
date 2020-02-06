package linkcollection.client.ui.frame;

import linkcollection.client.ui.bar.DefaultActionBar;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.ImageButtonListener;
import linkcollection.client.ui.widgets.WidgetConstant;
import user.Login;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public static LoginFrame context;
    private JLabel nameLabel = new JLabel("用户 ", JLabel.RIGHT);
    private JLabel pwdLabel = new JLabel("密码 ", JLabel.RIGHT);
    private JTextField nameField = new JTextField();
    private JTextField pwdField = new JTextField();
    private ImageButton login = new ImageButton(getClass(), "ui/img/login.png");
    private ImageButton register = new ImageButton(getClass(), "ui/img/toRegister.png");

    private JFrame parent;
    private boolean firstRigster = false;

    public LoginFrame(JFrame parent) {
        context = this;
        this.parent = parent;
        parent.setVisible(false);
        setUndecorated(true);
        WidgetConstant.setVisibleSize(400, 300);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLocation(WidgetConstant.getCenterLocation());
        setLayout(new BorderLayout());
        DefaultActionBar actionBar = new DefaultActionBar(this);
        actionBar.setBounds(0, 0, WidgetConstant.VisibleWidth, 50);
        actionBar.setTitle("登录");
        add(actionBar);
        addLoginPanel();
        setVisible(true);
    }

    public LoginFrame(JFrame parent, boolean firstRegister) {
        this(parent);
        this.firstRigster = firstRegister;
    }

    private void addLoginPanel() {
        JPanel panel = new JPanel(null);
        int startX = 100;
        int startY = 100;
        nameLabel.setFont(WidgetConstant.TitleFont);
        pwdLabel.setFont(WidgetConstant.TitleFont);
        nameField.setFont(WidgetConstant.TitleFont);
        pwdField.setFont(WidgetConstant.TitleFont);
        nameLabel.setBounds(startX, startY, 40, 30);
        pwdLabel.setBounds(startX, startY + 40, 40, 30);
        nameField.setBounds(startX + 40, startY, 160, 30);
        pwdField.setBounds(startX + 40, startY + 40, 160, 30);
        login.setBounds(startX + 5, startY + 80, 90, 30);
        register.setBounds(startX + 115, startY + 80, 90, 30);
        panel.add(nameLabel);
        panel.add(pwdLabel);
        panel.add(nameField);
        panel.add(pwdField);
        panel.add(login);
        panel.add(register);
        panel.setBackground(Color.white);
        add(panel, BorderLayout.CENTER);
        login.addClickListener(new ImageButtonListener() {
            @Override
            public void click(String flag) {
                if (Login.firstLogin(nameField.getText(), pwdField.getText())) {
                    dispose();
                }
            }
        });

        register.addClickListener(new ImageButtonListener() {
            @Override
            public void click(String flag) {
                new RegisterFrame(parent);
                dispose();
                parent.setVisible(false);
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        WidgetConstant.rollBackVisibleSize();
        // 注册成功后跳转
        if (firstRigster) {
            new LoveSelectFrame(parent).init("");
            return;
        }
        parent.setVisible(true);
        parent.setEnabled(true);
    }
}
