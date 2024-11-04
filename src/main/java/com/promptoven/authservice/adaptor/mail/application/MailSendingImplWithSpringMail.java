package com.promptoven.authservice.adaptor.mail.application;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.out.call.MailSending;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailSendingImplWithSpringMail implements MailSending {

	private static final String HOST = "smtp.gmail.com";
	private static final int PORT = 587;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	private JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(HOST);
		mailSender.setPort(PORT);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", HOST);
		props.put("mail.smtp.ssl.protocols", "TLSv1.3");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Override
	public void sendMail(String to, String subject, String text) {
		JavaMailSender mailSender = getJavaMailSender();
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(username);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true); // true indicates HTML content
			mailSender.send(message);

		} catch (MessagingException e) {
			throw new MailSendException("Failed to send email", e);
		}
	}
}