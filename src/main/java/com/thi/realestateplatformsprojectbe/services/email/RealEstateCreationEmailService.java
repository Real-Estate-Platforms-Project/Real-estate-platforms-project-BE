package com.thi.realestateplatformsprojectbe.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class RealEstateCreationEmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public RealEstateCreationEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendRealEstateCreationEmail(String to, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String htmlContent = "<!DOCTYPE html>"
                + "<html lang=\"vi\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Bất Động Sản Mới Được Thêm</title>"
                + "</head>"
                + "<body style=\"margin: 0; padding: 0; background-color: #f4f4f4;\">"
                + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "        <tr>"
                + "            <td style=\"padding: 20px 0 30px 0;\">"
                + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">"
                + "                    <tr>"
                + "                        <td align=\"center\" bgcolor=\"#FC650B\" style=\"padding: 10px;\">"
                + "                            <h2 style=\"color: white;\">Thông Báo Bất Động Sản Mới</h2>"
                + "                        </td>"
                + "                    </tr>"
                + "                    <tr>"
                + "                        <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">"
                + "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "                                <tr>"
                + "                                    <td>"
                + "                                        <p>" + content + "</p>"
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
        helper.setSubject("New Real Estate Listing Added");
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}

