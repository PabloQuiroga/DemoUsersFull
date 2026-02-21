package org.siar.domain.service;

public interface PasswordService {
    String hash(String password);
    boolean verify(String password, String hash);
}
