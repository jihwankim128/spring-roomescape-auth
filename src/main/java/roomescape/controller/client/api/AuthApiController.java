package roomescape.controller.client.api;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.global.auth.MemberPrincipal;
import roomescape.controller.client.api.dto.LoginRequest;
import roomescape.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Validated
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        MemberPrincipal principal = authService.login(request.toCommand());
        session.setAttribute(MemberPrincipal.SESSION_KEY, principal);

        return ResponseEntity.noContent().build();
    }
}
