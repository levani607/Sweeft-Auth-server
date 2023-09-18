package com.example.authorizationserver.model.flow.request;

import com.example.authorizationserver.mappers.UserMapper;
import com.example.authorizationserver.model.core.ExecutionRequest;
import com.example.authorizationserver.model.core.SecureRequestChain;
import com.example.authorizationserver.model.domain.RealmUser;
import com.example.authorizationserver.model.enums.EntityStatus;
import com.example.authorizationserver.model.response.PayloadResponse;
import com.example.authorizationserver.repository.UserRepository;
import com.example.authorizationserver.utils.ParserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsernamePasswordExecutionRequest implements ExecutionRequest {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String getName() {
        return "username_password";
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, SecureRequestChain chain, PayloadResponse payloadResponse) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String realmName = ParserUtils.getRealmNameFromUri(request.getRequestURI());


        Optional<RealmUser> userOptional = userRepository.findByUsernameAndRealmName(username, realmName, EntityStatus.ACTIVE);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        RealmUser realmUser = userOptional.get();
        if (!passwordEncoder.matches(password, realmUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        UserMapper.mapBasicInfo(realmUser,payloadResponse);
        chain.doFilter(request,response,payloadResponse);
    }

}
