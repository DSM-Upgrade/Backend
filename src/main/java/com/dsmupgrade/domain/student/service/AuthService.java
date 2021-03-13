package com.dsmupgrade.domain.student.service;

import com.dsmupgrade.domain.field.domain.Field;
import com.dsmupgrade.domain.field.domain.FieldRepository;
import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.domain.student.dto.request.LoginRequest;
import com.dsmupgrade.domain.student.dto.request.SignUpRequest;
import com.dsmupgrade.domain.student.dto.response.LoginResponse;
import com.dsmupgrade.domain.student.dto.response.TokenRefreshResponse;
import com.dsmupgrade.global.error.exception.FieldNotFoundException;
import com.dsmupgrade.global.error.exception.InvalidLoginInfoException;
import com.dsmupgrade.global.error.exception.StudentNotRegisteredException;
import com.dsmupgrade.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final FieldRepository fieldRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signUp(SignUpRequest signUpRequest) {
        Field field = fieldRepository.findById(signUpRequest.getFieldId())
                .orElseThrow(() -> new FieldNotFoundException(signUpRequest.getFieldId()));

        Student student = signUpRequest.toStudentEntity(field);

        studentRepository.save(student);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Student student = studentRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(InvalidLoginInfoException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), student.getPassword())) {
            throw new InvalidLoginInfoException();
        }

        if (!student.getIsRegistered()) {
            throw new StudentNotRegisteredException(loginRequest.getUsername());
        }

        return LoginResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(loginRequest.getUsername()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(loginRequest.getUsername()))
                .build();
    }

    public TokenRefreshResponse generateNewToken(String refreshToken) {
        jwtTokenProvider.validateRefreshToken(refreshToken);

        return TokenRefreshResponse.builder()
                .accessToken(
                        jwtTokenProvider.generateAccessToken(jwtTokenProvider.getUsername(refreshToken))
                )
                .build();
    }
}
