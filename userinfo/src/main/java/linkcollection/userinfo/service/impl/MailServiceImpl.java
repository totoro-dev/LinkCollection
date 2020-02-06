package linkcollection.userinfo.service.impl;

import linkcollection.userinfo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendMail(String to, String text) {
        System.out.println(from);
        MimeMessage message = this.sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(from, "LinksCollection");
            helper.setSubject("【链接收集分析系统】邮箱验证");
            helper.setTo(to);
            helper.setText("您的验证码是：" + text);
            this.sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
