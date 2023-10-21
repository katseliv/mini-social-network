package ru.relex.minisocialnetwork.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.relex.minisocialnetwork.model.dto.UserDetailsDto;
import ru.relex.minisocialnetwork.model.request.JwtRequest;
import ru.relex.minisocialnetwork.model.request.LoginRequest;
import ru.relex.minisocialnetwork.model.response.JwtResponse;
import ru.relex.minisocialnetwork.model.response.LoginResponse;
import ru.relex.minisocialnetwork.service.AuthService;
import ru.relex.minisocialnetwork.utils.SecurityContextFacade;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthRestController {

    private final AuthService authService;
    private final SecurityContextFacade securityContextFacade;
    protected AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        securityContextFacade.getContext().setAuthentication(authentication);

        final UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        final LoginResponse loginResponse = authService.login(userDetailsDto);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/new-access-token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody @Valid final JwtRequest jwtRequest) throws AuthException {
        final JwtResponse jwtResponse = authService.getNewAccessToken(jwtRequest.getRefreshToken());
        return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        final SecurityContext context = securityContextFacade.getContext();
        final String email = (String) context.getAuthentication().getPrincipal();
        authService.logout(email);
        context.setAuthentication(null);
        return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
    }

}
