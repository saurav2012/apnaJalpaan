package com.food.apnajalpaan.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailHelper {

    public final String email = "apnajalpaan@gmail.com";
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(email);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Send...");
    }

//    public void sendEmailWithAttachment(String toEmail,
//                                        String body,
//                                        String subject,
//                                        String attachment) throws MessagingException {
//
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//        MimeMessageHelper mimeMessageHelper
//                = new MimeMessageHelper(mimeMessage, true);
//
//        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
//        mimeMessageHelper.setTo(toEmail);
//        mimeMessageHelper.setText(body);
//        mimeMessageHelper.setSubject(subject);
//
//        FileSystemResource fileSystem
//                = new FileSystemResource(new File(attachment));
//
//        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
//                fileSystem);
//
//        mailSender.send(mimeMessage);
//        System.out.println("Mail Send...");
//
//    }
}
