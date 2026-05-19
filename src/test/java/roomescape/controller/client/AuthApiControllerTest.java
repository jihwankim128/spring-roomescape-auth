package roomescape.controller.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;
import roomescape.global.auth.MemberPrincipal;
import roomescape.controller.BaseControllerUnitTest;
import roomescape.controller.client.api.AuthApiController;
import roomescape.controller.client.api.dto.LoginRequest;
import roomescape.domain.Member;
import roomescape.domain.MemberRole;
import roomescape.service.AuthService;
import roomescape.service.command.LoginCommand;

@WebMvcTest(AuthApiController.class)
class AuthApiControllerTest extends BaseControllerUnitTest {

    @MockitoBean
    private AuthService authService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvcSetting(webApplicationContext);
    }

    @Test
    void 이름으로_로그인_요청에_성공하면_세션에_인증_정보를_저장한다() {
        // given
        MemberPrincipal principal = new MemberPrincipal("이프");
        when(authService.login(any(LoginCommand.class))).thenReturn(principal);

        // when
        HttpSession session = RestAssuredMockMvc.given().spec(defaultSpec()).log().all()
                .body(new LoginRequest("이프"))
                .when().post("/api/auth/login")
                .then().log().all()
                .status(HttpStatus.NO_CONTENT)
                .extract()
                .response()
                .mvcResult()
                .getRequest()
                .getSession(false);

        // then
        assertThat(session).isNotNull();
        assertThat(session.getAttribute(MemberPrincipal.SESSION_KEY)).isEqualTo(principal);
    }

    @Test
    void 로그인된_멤버_정보를_조회한다() {
        // given
        MemberPrincipal principal = new MemberPrincipal("이프");
        when(authService.getMember(principal)).thenReturn(new Member(1L, "이프", MemberRole.MEMBER));

        // when & then
        RestAssuredMockMvc.given().spec(authenticatedSpec(principal)).log().all()
                .when().get("/api/auth/me")
                .then().log().all()
                .status(HttpStatus.OK)
                .body("name", equalTo("이프"))
                .body("role", equalTo("MEMBER"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void 로그인_요청_시_이름이_비어있으면_400_BAD_REQUEST(String invalidName) {
        // given
        LoginRequest request = new LoginRequest(invalidName);

        // when & then
        RestAssuredMockMvc.given().spec(defaultSpec()).log().all()
                .body(request)
                .when().post("/api/auth/login")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .body(org.hamcrest.Matchers.containsString("로그인 이름은 비어있을 수 없습니다."));
    }
}
