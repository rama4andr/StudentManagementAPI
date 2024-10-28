package com.github.rama4andr.studentmanagement.service.impl;

import com.github.rama4andr.studentmanagement.dto.UserDto;
import com.github.rama4andr.studentmanagement.entity.UserEntity;
import com.github.rama4andr.studentmanagement.repository.UserRepository;
import com.github.rama4andr.studentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(UserDto userDto) {
        UserEntity userEntity = dtoToEntity(userDto);

        if (userRepository.getUserEntityByLogin(userEntity.getLogin()).isEmpty()) {
            userRepository.save(userEntity);
        } else {
            throw new BadCredentialsException("Пользователь с таким логином уже существует: " + userEntity.getLogin());
        }
    }

    @Override
    public String getAccessToken(UserDto userDto) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDto.login(), userDto.password()));

        if (authenticate.isAuthenticated()) {
            Instant now = Instant.now();

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .subject(userDto.login())
                    .issuedAt(now)
                    .expiresAt(now.plus(1, ChronoUnit.HOURS))
                    .claim("type", "access")
                    .build();

            JwtEncoderParameters jwt = JwtEncoderParameters.from(claims);
            return jwtEncoder.encode(jwt).getTokenValue();
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    private UserEntity dtoToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(userDto.login());
        userEntity.setPassword(passwordEncoder.encode(userDto.password()));
        return userEntity;
    }
}
