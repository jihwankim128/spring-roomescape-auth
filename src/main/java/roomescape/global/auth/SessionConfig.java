package roomescape.global.auth;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
@EnableSpringHttpSession
public class SessionConfig {

    public static final String SESSION_HEADER_NAME = "X-Session-Id";

    @Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }

    @Bean
    public DefaultCookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName("JSESSIONID");
        cookieSerializer.setCookiePath("/");
        cookieSerializer.setSameSite("Lax");
        cookieSerializer.setUseHttpOnlyCookie(true);
        return cookieSerializer;
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver(DefaultCookieSerializer defaultCookieSerializer) {
        CookieHttpSessionIdResolver cookieResolver = new CookieHttpSessionIdResolver();
        cookieResolver.setCookieSerializer(defaultCookieSerializer);

        HeaderHttpSessionIdResolver headerResolver = new HeaderHttpSessionIdResolver(SESSION_HEADER_NAME);
        return new CompositeHttpSessionIdResolver(List.of(headerResolver, cookieResolver));
    }
}
