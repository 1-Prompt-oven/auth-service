package com.promptoven.authservice.application.port.out.call;

public interface MailSending {

	void sendMail(String to, String subject, String text);

}
