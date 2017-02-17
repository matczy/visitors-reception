package app.email;

import app.login.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Component
public class EmailSender {
    private JavaMailSender javaMailSender;


    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Inject
    private SpringTemplateEngine templateEngine;

    @Async
    public EmailStatus sendMessage(String to, String subject, String text) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);
            javaMailSender.send(mail);
            return new EmailStatus(to, subject, text).success();
        } catch (Exception e) {
            return new EmailStatus(to, subject, text).error(e.getMessage());
        }
    }


    @Async
    public EmailStatus sendHtmlMessage(String to, String subject, String text) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mail);
            return new EmailStatus(to, subject, text).success();
        } catch (Exception e) {
            return new EmailStatus(to, subject, text).error(e.getMessage());
        }
    }
    @Async
    public void sendPasswordResetMail(User user, String baseUrl) {
        Locale locale = Locale.forLanguageTag("pl");
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("passwordResetEmail", context);
        sendHtmlMessage(user.getEmail(), "reset password", content);
    }
}
