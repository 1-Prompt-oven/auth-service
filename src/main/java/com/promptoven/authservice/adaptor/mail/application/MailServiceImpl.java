package com.promptoven.authservice.adaptor.mail.application;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.out.call.MailService;

@Service
public class MailServiceImpl implements MailService {

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
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.ssl.protocols", "TLSv1.3");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Override
	public void sendMail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(username);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		try {
			JavaMailSender mailSender = getJavaMailSender();
			mailSender.send(message);
		} catch (MailSendException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
}