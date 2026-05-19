package roomescape.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.repository.MemberRepository;

@RequiredArgsConstructor
public class PreAuthInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        PreAuth preAuth = findPreAuth(handlerMethod);
        if (preAuth == null) {
            return true;
        }

        MemberPrincipal principal = findPrincipal(request);
        validateRole(principal, preAuth.role());
        return true;
    }

    private PreAuth findPreAuth(HandlerMethod handlerMethod) {
        PreAuth methodAnnotation = handlerMethod.getMethodAnnotation(PreAuth.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        return handlerMethod.getBeanType().getAnnotation(PreAuth.class);
    }

    private MemberPrincipal findPrincipal(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        Object principal = session.getAttribute(MemberPrincipal.SESSION_KEY);
        if (!(principal instanceof MemberPrincipal memberPrincipal)) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return memberPrincipal;
    }

    private void validateRole(MemberPrincipal principal, String role) {
        memberRepository.findByName(principal.name())
                .filter(member -> member.getRole().name().equals(role))
                .orElseThrow(() -> new UnauthorizedException("로그인이 필요합니다."));
    }
}
