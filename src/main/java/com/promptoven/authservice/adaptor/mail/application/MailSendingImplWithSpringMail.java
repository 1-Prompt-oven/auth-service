package com.promptoven.authservice.adaptor.mail.application;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.promptoven.authservice.application.port.out.call.MailSending;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSendingImplWithSpringMail implements MailSending {

	private static final String HOST = "smtp.gmail.com";
	private static final int PORT = 587;

	private final SpringTemplateEngine templateEngine;

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
		props.put("mail.mime.charset", "UTF-8");
		props.put("mail.smtp.encoding", "UTF-8");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Override
	public void sendMail(String to, String subject, String text) {
		JavaMailSender mailSender = getJavaMailSender();
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(new InternetAddress(username, "PromptOven", "UTF-8"));
			helper.setTo(to);
			helper.setSubject(subject);

			String mailBody = buildMailBody(text);
			helper.setText(mailBody, true);

			mailSender.send(message);

		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new MailSendException("Failed to send email", e);
		}
	}

	private String buildMailBody(String text) {
		Context context = new Context();
		context.setVariable("inputedString", text);
		context.setLocale(Locale.KOREAN);
		return templateEngine.process("BaseMailBody", context);
	}

}