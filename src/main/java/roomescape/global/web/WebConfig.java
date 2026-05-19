package roomescape.global.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import roomescape.global.auth.PreAuthInterceptor;
import roomescape.repository.MemberRepository;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ObjectProvider<MemberRepository> memberRepositoryProvider;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageableHandlerMethodArgumentResolver());
        resolvers.add(new MemberPrincipalArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        memberRepositoryProvider.ifAvailable(
                memberRepository -> registry.addInterceptor(new PreAuthInterceptor(memberRepository))
        );
    }
}
