package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.DuplicateEntityException;
import roomescape.domain.MemberRole;
import roomescape.repository.MemberRepository;
import roomescape.service.command.MemberSignUpCommand;
import roomescape.service.fake.FakeMemberRepository;
import roomescape.service.result.MemberResult;

class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        this.memberRepository = new FakeMemberRepository();
        this.memberService = new MemberService(memberRepository);
    }

    @Test
    void 이름으로_회원가입을_한다() {
        // given
        MemberSignUpCommand command = new MemberSignUpCommand("이프");

        // when
        MemberResult result = memberService.signUp(command);

        // then
        assertThat(result)
                .extracting(MemberResult::id, MemberResult::name, MemberResult::role)
                .containsExactly(1L, "이프", MemberRole.MEMBER);
    }

    @Test
    void 이미_존재하는_이름으로_회원가입을_시도하면_예외가_발생한다() {
        // given
        memberService.signUp(new MemberSignUpCommand("이프"));

        // when & then
        assertThatThrownBy(() -> memberService.signUp(new MemberSignUpCommand("이프")))
                .isInstanceOf(DuplicateEntityException.class)
                .hasMessageContaining("이미 존재하는 멤버입니다.");
    }
}
