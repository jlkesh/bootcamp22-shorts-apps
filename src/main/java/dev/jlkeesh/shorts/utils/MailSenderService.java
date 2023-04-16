package dev.jlkeesh.shorts.utils;

import dev.jlkeesh.shorts.entities.Url;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.List;
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
        sendMail(model, "activate_account.ftlh");
    }
    @Async
    public void report(Map<String, Object> model) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo((String) model.get("to"));
//            mimeMessageHelper.setSubject((String) model.get("subject"));
            Template template = configuration.getTemplate("report.ftlh");
            String htmlMailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            mimeMessageHelper.setText(htmlMailContent, true);
//            mimeMessageHelper.addInline((String) model.get("id"), new ClassPathResource((String) model.get("path")));
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendResetPasswordMail(Map<String, String> model) {
        sendMail(model, "resetPassword.ftlh");
    }

    private void sendMail(Map<String, String> model, String templateName) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(model.get("to"));
            mimeMessageHelper.setSubject(model.get("subject"));
            Template template = configuration.getTemplate(templateName);
            String htmlMailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            mimeMessageHelper.setText(htmlMailContent, true);
            mimeMessageHelper.addInline(model.get("id"), new ClassPathResource(model.get("path")));
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }


}
