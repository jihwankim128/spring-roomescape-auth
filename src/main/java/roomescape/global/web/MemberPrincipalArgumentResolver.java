package roomescape.global.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import roomescape.global.auth.MemberPrincipal;
import roomescape.global.auth.UnauthorizedException;

@RequiredArgsConstructor
public class MemberPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return MemberPrincipal.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Object principal = webRequest.getAttribute(MemberPrincipal.SESSION_KEY, RequestAttributes.SCOPE_SESSION);
        if (!(principal instanceof MemberPrincipal)) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        return principal;
    }
}
