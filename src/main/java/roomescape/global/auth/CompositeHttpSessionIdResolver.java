package roomescape.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.session.web.http.HttpSessionIdResolver;

public record CompositeHttpSessionIdResolver(List<HttpSessionIdResolver> delegates) implements HttpSessionIdResolver {

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        return delegates.stream()
                .map(delegate -> delegate.resolveSessionIds(request))
                .filter(ids -> !ids.isEmpty())
                .findFirst()
                .orElse(List.of());
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        delegates.forEach(delegate -> delegate.setSessionId(request, response, sessionId));
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        delegates.forEach(delegate -> delegate.expireSession(request, response));
    }
}
