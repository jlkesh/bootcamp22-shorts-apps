package dev.jlkeesh.shorts.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class MailSenderService {
    private final String from;
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    public MailSenderService(@Value("${application.mail}") String from,
                             JavaMailSender javaMailSender,
                             Configuration configuration) {
        this.from = from;
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
    }

    @Async
    public void sendActivationMail(Map<String, String> model) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(model.get("to"));
            mimeMessageHelper.setSubject("Activate Your Account");
            Template template = configuration.getTemplate("activate_account.ftlh");
            String htmlMailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            mimeMessageHelper.setText(htmlMailContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

}
