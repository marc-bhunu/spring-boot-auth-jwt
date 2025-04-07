package com.demo.security.auth.service;


import com.demo.security.auth.domain.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

        public void sendVerificationEmail(String to, String subject, String verificationCode) throws MessagingException {
            String htmlMessage = createVerificationEmail(verificationCode);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlMessage, true);
            mailSender.send(message);

    }

    public String createVerificationEmail(String verificationCode) {
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Your Verification Code</title>"
                + "</head>"
                + "<body style=\"margin: 0; padding: 0; font-family: 'Arial', sans-serif; background-color: #f8f9fa;\">"
                + "    <div style=\"max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 12px rgba(0,0,0,0.1);\">"
                + "        <div style=\"background: linear-gradient(135deg, #6e8efb, #a777e3); padding: 30px; text-align: center;\">"
                + "            <h1 style=\"margin: 0; color: white; font-size: 24px;\">Verify Your Account</h1>"
                + "        </div>"
                + "        <div style=\"padding: 30px;\">"
                + "            <p style=\"font-size: 16px; color: #555; line-height: 1.5;\">"
                + "                Thank you for registering! Please use the following verification code to complete your signup:"
                + "            </p>"
                + "            <div style=\"margin: 30px 0; text-align: center;\">"
                + "                <div style=\"display: inline-block; padding: 15px 25px; background-color: #f0f4ff; border-radius: 6px; border: 1px dashed #6e8efb;\">"
                + "                    <span style=\"font-size: 28px; font-weight: bold; letter-spacing: 2px; color: #4a6cf7;\">" + verificationCode + "</span>"
                + "                </div>"
                + "            </div>"
                + "            <p style=\"font-size: 14px; color: #888; text-align: center;\">"
                + "                This code will expire in 15 minutes. If you didn't request this, please ignore this email."
                + "            </p>"
                + "        </div>"
                + "        <div style=\"padding: 20px; background-color: #f8f9fa; text-align: center; font-size: 12px; color: #777;\">"
                + "            © " + LocalDate.now().getYear() + " Your App Name. All rights reserved."
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";
    }


}
