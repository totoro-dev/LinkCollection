package linkcollection.client.ui.frame;

import linkcollection.client.ui.bar.DefaultActionBar;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.ImageButtonListener;
import linkcollection.client.ui.widgets.Toast;
import linkcollection.client.ui.widgets.WidgetConstant;
import user.Register;

import javax.swing.*;
import java.awt.*;

/**
 * 申请界面
 */
public class RegisterFrame extends JFrame {

    public static JFrame context;
    private JFrame parent;

    private DefaultActionBar actionBar;
    private JLabel nameTipLabel;
    private JLabel mailTipLabel;
    private JLabel pwdTipLabel;
    private JLabel rePwdTipLabel;

    private JTextField nickField;
    private JTextField mailField;
    private JTextField pwdField;
    private JTextField rePwdField;

    private ImageButton submit;

    // 退出当前界面时是否打开父界面
    private boolean parentVisible = true;

    public RegisterFrame(JFrame parent) {
        this.parent = parent;
        context = this;
        setLayout(null);
        setUndecorated(true);
        WidgetConstant.setVisibleSize(400, 300);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLocation(WidgetConstant.getCenterLocation());
        actionBar = new DefaultActionBar(this);
        actionBar.setBounds(0, 0, WidgetConstant.VisibleWidth, 50);
        actionBar.setTitle("注册");
        add(actionBar);

        nameTipLabel = new JLabel("用户名 ", JLabel.RIGHT);
        mailTipLabel = new JLabel("邮箱地址 ", JLabel.RIGHT);
        pwdTipLabel = new JLabel("密码 ", JLabel.RIGHT);
        rePwdTipLabel = new JLabel("确认密码 ", JLabel.RIGHT);
        nickField = new JTextField();
        mailField = new JTextField();
        pwdField = new JTextField();
        rePwdField = new JTextField();

        nameTipLabel.setFont(WidgetConstant.TitleFont);
        mailTipLabel.setFont(WidgetConstant.TitleFont);
        pwdTipLabel.setFont(WidgetConstant.TitleFont);
        rePwdTipLabel.setFont(WidgetConstant.TitleFont);
        nickField.setFont(WidgetConstant.TitleFont);
        mailField.setFont(WidgetConstant.TitleFont);
        pwdField.setFont(WidgetConstant.TitleFont);
        rePwdField.setFont(WidgetConstant.TitleFont);

        submit = new ImageButton(getClass(), "ui/img/register.png");

        int startY = 80;
        int startX = 100;
        nameTipLabel.setBounds(startX, startY, 70, 30);
        mailTipLabel.setBounds(startX, startY + 40, 70, 30);
        pwdTipLabel.setBounds(startX, startY + 80, 70, 30);
        rePwdTipLabel.setBounds(startX, startY + 120, 70, 30);

        nickField.setBounds(startX + 70, startY, 130, 30);
        mailField.setBounds(startX + 70, startY + 40, 130, 30);
        pwdField.setBounds(startX + 70, startY + 80, 130, 30);
        rePwdField.setBounds(startX + 70, startY + 120, 130, 30);

        submit.setBounds(startX + 60, startY + 160, 100, 30);

        add(nameTipLabel);
        add(mailTipLabel);
        add(pwdTipLabel);
        add(rePwdTipLabel);

        add(nickField);
        add(mailField);
        add(pwdField);
        add(rePwdField);

        add(submit);
        submit.addClickListener(new ImageButtonListener() {
            @Override
            public void click(String flag) {
                String nick = nickField.getText();
                String mail = mailField.getText();
                String pwd = pwdField.getText();
                String rePwd = rePwdField.getText();
                if (nick == null || nick.equals("")) {
                    Toast.makeText(RegisterFrame.this, "用户名不能为空").show(1000);
                    return;
                }
                if (mail == null || mail.equals("") || !mail.contains("@")) {
                    Toast.makeText(RegisterFrame.this, "请输入正确的邮箱").show(1000);
                    return;
                }
                if (pwd != null && pwd.equals(rePwd)) {
                    boolean register = Register.register(nick, mail, pwd);
                    if (register) {
                        dispose(false);
                    } else {
                        Toast.makeText(RegisterFrame.this, "该账号无法注册").show(1000);
                    }
                } else {
                    Toast.makeText(RegisterFrame.this, "密码不一致").show(1000);
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        getContentPane().setBackground(Color.white);
    }

    public void dispose(boolean parentVisible) {
        this.parentVisible = parentVisible;
        dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
        WidgetConstant.rollBackVisibleSize();
        parent.setVisible(parentVisible);
    }
}
