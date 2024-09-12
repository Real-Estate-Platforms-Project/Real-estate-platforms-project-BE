package com.thi.realestateplatformsprojectbe.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class AdminAccountCreationEmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public AdminAccountCreationEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendAccountCreationEmail(String to, String email, String temporaryPassword) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String htmlContent = "<!DOCTYPE html>"
                + "<html lang=\"vi\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Tài Khoản Được Tạo</title>"
                + "</head>"
                + "<body style=\"margin: 0; padding: 0; background-color: #f4f4f4;\">"
                + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "        <tr>"
                + "            <td style=\"padding: 20px 0 30px 0;\">"
                + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">"
                + "                    <tr>"
                + "                        <td align=\"center\" bgcolor=\"#FC650B\" style=\"padding: 40px 0 30px 0;\">"
                + "                            <h2 style=\"color: white;\">Thông tin tài khoản của bạn</h2>"
                + "                        </td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">"
                + "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "                                <tr>"
                + "                                    <td style=\"color: #333333; font-family: Arial, sans-serif; font-size: 16px; line-height: 24px;\">"
                + "                                        Chào bạn, <br/>"
                + "                                        Tài khoản của bạn đã được tạo thành công bởi quản trị viên. Dưới đây là thông tin đăng nhập:"
                + "                                    </td>"
                + "                                </tr>"
                + "                                <tr>"
                + "                                    <td style=\"padding: 20px 0 10px 0; color: #555555; font-family: Arial, sans-serif; font-size: 16px;\">"
                + "                                        <b>Tên đăng nhập:</b> " + email + "<br/>"
                + "                                        <b>Mật khẩu tạm thời:</b> " + temporaryPassword
                + "                                    </td>"
                + "                                </tr>"
                + "                                <tr>"
                + "                                    <td style=\"padding: 10px 0 30px 0; color: #777777; font-family: Arial, sans-serif; font-size: 14px; line-height: 20px;\">"
                + "                                        Vui lòng đăng nhập và thay đổi mật khẩu của bạn sớm nhất có thể để đảm bảo an toàn."
                + "                                    </td>"
                + "                                </tr>"
                + "                                <tr>"
                + "                                    <td align=\"center\">"
                + "                                        <a href=\"http://localhost:3000\" style=\"background-color: #FC650B; color: white; padding: 15px 25px; text-decoration: none; font-size: 16px; border-radius: 5px; font-family: Arial, sans-serif;\">Đăng nhập ngay</a>"
                + "                                    </td>"
                + "                                </tr>"
                + "                            </table>"
                + "                        </td>"
                + "                    </tr>"
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
        helper.setSubject("Thông tin tài khoản mới từ Real Estate Platform");
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}