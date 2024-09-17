package com.thi.realestateplatformsprojectbe.services.email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotifyEmailToChangePasswordService {
    private final JavaMailSender javaMailSender;
    @Autowired
    public NotifyEmailToChangePasswordService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotiFyEmailChangePassword(String to, Long changePasswordDate) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String htmlContent = "<!DOCTYPE html>"
                + "<html lang=\"vi\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title> Thông báo thay đổi mật khẩu</title>"
                + "</head>"
                + "<body style=\"margin: 0; padding: 0; background-color: #f4f4f4;\">"
                + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "        <tr>"
                + "            <td style=\"padding: 20px 0 30px 0;\">"
                + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">"
                + "                    <!-- Header -->"
                + "                    <tr>"
                + "                        <td align=\"center\" bgcolor=\"#FC650B\" style=\"padding: 40px 0 30px 0;\">"
                + "                        </td>"
                + "                    </tr>"
                + "                    <!-- Body -->"
                + "                    <tr>"
                + "                        <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">"
                + "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "                                <tr>"
                + "                                    <td style=\"color: #333333; text-align: center; font-family: Arial, sans-serif; font-size: 24px;\">"
                + "                                        <b style=\"margin-bottom: 30px;\"> Real Estate Platform. Thông báo thay đổi mật khẩu!</b>"
                + "                                    </td>"
                + "                                </tr>"
                + "                                <tr>"
                + "                                    <td style=\"padding: 20px 0 30px 0; color: #555555; font-family: Arial, sans-serif; font-size: 16px; line-height: 24px;\">"
                + "                                        Bạn cần thay đổi mật khẩu trước khi hết thời gian thay đổi, Tài khoản của bạn còn : "+ changePasswordDate + " ngày"
                + "                                    </td>"
                + "                                </tr>"
                + "                                <tr>"
                + "                                    <td style=\"padding: 30px 0 0 0; color: #777777; font-family: Arial, sans-serif; font-size: 14px; line-height: 20px;\">"
                + "                                        Nếu việc lấy lại mật khẩu là không cần thiết, vui lòng bỏ qua email này hoặc <a href=\"mailto:support@realestateplatform.com\" style=\"color: #FC650B; text-decoration: none;\">liên hệ với chúng tôi</a>."
                + "                                    </td>"
                + "                                </tr>"
                + "                            </table>"
                + "                        </td>"
                + "                    </tr>"
                + "                    <!-- Footer -->"
                + "                    <tr>"
                + "                        <td bgcolor=\"#FC650B\" style=\"padding: 30px 30px 30px 30px;\">"
                + "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "                                <tr>"
                + "                                    <td style=\"color: #ffffff; text-align: center; font-family: Arial, sans-serif; font-size: 14px;\" width=\"75%\">"
                + "                                        &copy; 2024 Real Estate Platform. All rights reserved.<br/>"
                + "                                    </td>"
                + "                                </tr>"
                + "                            </table>"
                + "                        </td>"
                + "                    </tr>"
                + "                </table>"
                + "            </td>"
                + "        </tr>"
                + "    </table>"
                + "</body>"
                + "</html>";

        helper.setTo(to);
        helper.setSubject("Real Estate Platforms");
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}

