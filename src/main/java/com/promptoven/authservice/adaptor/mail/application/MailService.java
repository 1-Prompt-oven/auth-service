package com.promptoven.authservice.adaptor.mail.application;

public interface MailService {

	void sendMail(String to, String subject, String text);

}
