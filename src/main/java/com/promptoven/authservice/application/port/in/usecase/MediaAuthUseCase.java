package com.promptoven.authservice.application.port.in.usecase;

public interface MediaAuthUseCase {

    boolean checkMedia(String email, String code);

    void requestEmail(String email);

    void requestPhone(String phone);

}
