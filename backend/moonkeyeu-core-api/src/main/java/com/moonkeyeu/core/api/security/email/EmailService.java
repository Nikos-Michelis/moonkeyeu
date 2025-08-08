package com.moonkeyeu.core.api.security.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.sender}")
    private String sender;

    private MimeMessageHelper MimeMessageSetup() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        return new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );
    }
    @Async // Avoid blocking all other user actions, while send the auth email.
    public void sendOtpEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String url,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.getName();
        }
        MimeMessageHelper helper = MimeMessageSetup();
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("url", url);
        properties.put("code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);
        mailSender.send(helper.getMimeMessage());
    }
}
