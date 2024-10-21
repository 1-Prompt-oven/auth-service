package com.promptoven.authservice.application.port.out.call;

public interface MailService {

	void sendMail(String to, String subject, String text);

}
