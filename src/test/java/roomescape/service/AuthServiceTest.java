package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.global.auth.MemberPrincipal;
import roomescape.domain.EntityNotFoundException;
import roomescape.repository.MemberRepository;
import roomescape.service.command.LoginCommand;
import roomescape.service.command.MemberSignUpCommand;
import roomescape.service.fake.FakeMemberRepository;

class AuthServiceTest {

    private MemberRepository memberRepository;
    private MemberService memberService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        this.memberRepository = new FakeMemberRepository();
        this.memberService = new MemberService(memberRepository);
        this.authService = new AuthService(memberRepository);
    }

    @Test
    void 이름으로_로그인한다() {
        // given
        memberService.signUp(new MemberSignUpCommand("이프"));

        // when
        MemberPrincipal principal = authService.login(new LoginCommand("이프"));

        // then
        assertThat(principal.name()).isEqualTo("이프");
    }

    @Test
    void 존재하지_않는_이름으로_로그인하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> authService.login(new LoginCommand("이프")))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 멤버입니다.");
    }
}
