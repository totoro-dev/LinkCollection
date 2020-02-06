package linkcollection.client.ui.frame;

import linkcollection.client.ui.bar.DefaultActionBar;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.ImageButtonListener;
import linkcollection.client.ui.widgets.Toast;
import linkcollection.client.ui.widgets.WidgetConstant;
import user.Register;

import javax.swing.*;
import java.awt.*;

public class CheckMailFrame extends JFrame {

    private JFrame parent;
    private DefaultActionBar actionBar;
    private JLabel codeTipLabel;
    private JTextField codeField;
    private ImageButton submit;

    public CheckMailFrame(JFrame parent) {
        this.parent = parent;
        setLayout(null);
        setUndecorated(true);
        WidgetConstant.setVisibleSize(400, 300);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLocation(WidgetConstant.getCenterLocation());
        actionBar = new DefaultActionBar(this);
        actionBar.setBounds(0, 0, WidgetConstant.VisibleWidth, 50);
        actionBar.setTitle("邮箱验证");
        add(actionBar);
        codeTipLabel = new JLabel("验证码 ", JLabel.RIGHT);
        codeField = new JTextField();
        submit = new ImageButton(getClass(), "ui/img/register.png");

        codeTipLabel.setBounds(100, 150, 70, 30);
        codeField.setBounds(170, 150, 130, 30);
        submit.setBounds(100, 200, 200, 30);

        add(codeTipLabel);
        add(codeField);
        add(submit);

        getContentPane().setBackground(Color.white);

        submit.addClickListener(flag -> {
            String code = codeField.getText();
            if (code == null || code.length() != 4) {
                Toast.makeText(CheckMailFrame.this, "请输入正确的验证码").show(Toast.LONG);
            } else {
                if (Register.checkMail(code)) {
                    dispose();
                    parent.setVisible(false);
                } else {
                    Toast.makeText(CheckMailFrame.this, "验证码错误").show(Toast.LONG);
                }
            }
        });

        setVisible(true);
        Toast.makeText(this, "请验证邮箱").show(Toast.LONG);
    }

    @Override
    public void dispose() {
        super.dispose();
        WidgetConstant.rollBackVisibleSize();
        parent.setVisible(true);
    }
}
