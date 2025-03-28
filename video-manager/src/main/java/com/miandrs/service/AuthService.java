package com.miandrs.service;

import com.miandrs.models.dto.AuthDto;
import com.miandrs.models.dto.LoginDto;
import com.miandrs.models.dto.RegisterDto;

public interface AuthService {
	AuthDto login(LoginDto login) throws Exception;
    AuthDto register(RegisterDto register) throws Exception;
}
