package com.prelude.todoapp.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${spring.mail.password:}") // Mặc định là rỗng nếu không có
    private String mailPassword;

    public void sendDueDateReminder(String recipient, String taskTitle, String dueDate) {
        if (mailPassword.isEmpty()) {
            log.info("🔔 [LOG] Task sắp đến hạn: {} - DueDate: {}", taskTitle, dueDate);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(senderEmail);
            helper.setTo(recipient);
            helper.setSubject("🔔 Task sắp đến hạn!");
            helper.setText("Task: " + taskTitle + "\nNgày hết hạn: " + dueDate, false);

            mailSender.send(message);
            log.info("📧 Email nhắc nhở đã gửi đến: {}", recipient);
        } catch (MessagingException e) {
            log.error("❌ Lỗi khi gửi email: {}", e.getMessage());
        }
    }
}
