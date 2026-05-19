package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @Test
    void 정상적인_멤버_정보를_생성한다() {
        // given
        String name = "바니";

        // when
        Member member = new Member(name);

        // then
        assertThat(member)
                .extracting(Member::getId, Member::getName, Member::getRole)
                .containsExactly(null, name, MemberRole.USER);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void 멤버_이름이_비어있을_경우_예외가_발생한다(String invalidName) {
        // when & then
        assertThatThrownBy(() -> new Member(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("멤버 이름은 필수 값입니다.");
    }

    @Test
    void 멤버_권한이_비어있을_경우_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> new Member(1L, "바니", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("멤버 권한은 필수 값입니다.");
    }
}
