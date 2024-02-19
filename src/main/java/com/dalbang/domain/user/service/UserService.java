package com.dalbang.domain.user.service;

import com.dalbang.domain.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
    public User userInsert(User user);
    public PasswordEncoder passwordEncoder();
    public User userUpdate(User user);
    public User getId(String id);
    public int loginPro(String id);
}
