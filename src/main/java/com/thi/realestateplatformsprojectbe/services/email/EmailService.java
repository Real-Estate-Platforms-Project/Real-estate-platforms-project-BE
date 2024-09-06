package com.thi.realestateplatformsprojectbe.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService (JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerifyEmail(String to, String confirmationUrl) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String htmlContent = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><style>"
                + "body {font-family: Arial, sans-serif; background-color: #f4f4f4;}"
                + ".email-content {max-width: 600px; margin: auto; background-color: white; padding: 20px; border-radius: 8px;}"
                + "h1 {font-size: 24px; color: #333;}"
                + "p {font-size: 16px; color: #555;}"
                + ".button {display: inline-block; background-color: #4CAF50; color: white; padding: 10px 20px; border-radius: 5px; text-decoration: none; text-align: center}"
                + "</style></head><body><div class='email-content'><h1>Chào mừng tới với Siêu Dự Án Tỷ Đô!</h1>"
                + "<p>Cảm ơn bạn đã đăng ký tài khoản. Vui lòng nhấn vào nút dưới đây để kích hoạt tài khoản của bạn</p>"
                + "<a class='button' href='" + confirmationUrl + "'>Kích hoạt tài khoản</a>"
                + "</div></body></html>";

        helper.setTo(to);
        helper.setSubject("Real Estate Platforms");
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
