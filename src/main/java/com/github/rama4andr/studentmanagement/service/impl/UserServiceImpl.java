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

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtEncoder jwtEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder,
                           UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(UserDto userDto) {
        UserEntity userEntity = dtoToEntity(userDto);

        if (userRepository.getUserEntityByLogin(userEntity.getLogin()).isPresent()) {
            throw new BadCredentialsException("A user with this username already exists: " + userEntity.getLogin());
        }
        UserEntity savedEntity = userRepository.save(userEntity);

        return entityToDto(savedEntity);
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

    private UserDto entityToDto(UserEntity savedEntity) {
        return new UserDto(savedEntity.getLogin(), savedEntity.getPassword());
    }
}
