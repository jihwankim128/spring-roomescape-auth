package integration.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import integration.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.DuplicateEntityException;
import roomescape.domain.Member;
import roomescape.domain.MemberRole;
import roomescape.repository.MemberRepository;

class MemberRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource.clearId();
        dataSource.clearTable();
    }

    @Test
    void 멤버를_저장하고_ID로_조회할_수_있다() {
        // given
        Member member = new Member("이프");

        // when
        Member saved = memberRepository.save(member);

        // then
        assertThat(memberRepository.findById(saved.getId()))
                .isPresent()
                .get()
                .extracting(Member::getName, Member::getRole)
                .containsExactly("이프", MemberRole.MEMBER);
    }

    @Test
    void 이름으로_멤버가_존재하는지_확인한다() {
        // given
        memberRepository.save(new Member("이프"));

        // when
        boolean result = memberRepository.existsByName("이프");

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 이름으로_멤버를_조회할_수_있다() {
        // given
        memberRepository.save(new Member("이프"));

        // when & then
        assertThat(memberRepository.findByName("이프"))
                .isPresent()
                .get()
                .extracting(Member::getName, Member::getRole)
                .containsExactly("이프", MemberRole.MEMBER);
    }

    @Test
    void 같은_이름의_멤버를_저장하면_제약_위반_예외가_발생한다() {
        // given
        Member member = new Member("이프");
        memberRepository.save(member);

        // when & then
        assertThatThrownBy(() -> memberRepository.save(member))
                .isInstanceOf(DuplicateEntityException.class)
                .hasMessageContaining("이미 존재하는 멤버입니다.");
    }
}
