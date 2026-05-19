package roomescape.controller.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;
import roomescape.controller.BaseControllerUnitTest;
import roomescape.controller.client.api.MemberApiController;
import roomescape.controller.client.api.dto.MemberResponse;
import roomescape.controller.client.api.dto.MemberSignUpRequest;
import roomescape.domain.MemberRole;
import roomescape.service.MemberService;
import roomescape.service.command.MemberSignUpCommand;
import roomescape.service.result.MemberResult;

@WebMvcTest(MemberApiController.class)
class MemberApiControllerTest extends BaseControllerUnitTest {

    @MockitoBean
    private MemberService memberService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvcSetting(webApplicationContext);
    }

    @Test
    void 이름으로_회원가입_요청에_성공하면_정상_응답이_반환된다() {
        // given
        MemberSignUpRequest request = new MemberSignUpRequest("바니");
        MemberResult result = new MemberResult(1L, "바니", MemberRole.USER);
        when(memberService.signUp(any(MemberSignUpCommand.class))).thenReturn(result);

        // when & then
        MemberResponse response = RestAssuredMockMvc.given().spec(defaultSpec()).log().all()
                .body(request)
                .when().post("/api/members")
                .then().log().all()
                .status(HttpStatus.CREATED)
                .extract().as(new TypeRef<>() {
                });

        assertThat(response).isEqualTo(MemberResponse.from(result));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void 회원가입_요청_시_이름이_비어있으면_400_BAD_REQUEST(String invalidName) {
        // given
        MemberSignUpRequest request = new MemberSignUpRequest(invalidName);

        // when & then
        RestAssuredMockMvc.given().spec(defaultSpec()).log().all()
                .body(request)
                .when().post("/api/members")
                .then().log().all()
                .status(HttpStatus.BAD_REQUEST)
                .body(containsString("멤버 이름은 비어있을 수 없습니다."));
    }
}
