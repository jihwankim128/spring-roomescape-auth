package roomescape.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecBuilder;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.WebApplicationContext;
import roomescape.global.auth.MemberPrincipal;
import roomescape.domain.Member;
import roomescape.domain.MemberRole;
import roomescape.repository.MemberRepository;

public abstract class BaseControllerUnitTest {

    protected void mockMvcSetting(WebApplicationContext webApplicationContext) {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

    }

    protected MockMvcRequestSpecification defaultSpec() {
        return new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    protected MockMvcRequestSpecification authenticatedSpec(MemberPrincipal principal) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(MemberPrincipal.SESSION_KEY, principal);

        return defaultSpec()
                .postProcessors(request -> {
                    request.setSession(session);
                    return request;
                });
    }

    protected MockMvcRequestSpecification adminSpec() {
        return authenticatedSpec(new MemberPrincipal("관리자"));
    }

    protected void mockAdmin(MemberRepository memberRepository) {
        org.mockito.Mockito.when(memberRepository.findByName("관리자"))
                .thenReturn(java.util.Optional.of(new Member(1L, "관리자", MemberRole.ADMIN)));
    }
}
