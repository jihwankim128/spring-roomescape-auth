package roomescape.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Member {

    private final Long id;
    private final String name;
    private final MemberRole role;

    public Member(Long id, String name, MemberRole role) {
        validateName(name);
        validateRole(role);
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Member(String name) {
        this(null, name, MemberRole.USER);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("멤버 이름은 필수 값입니다.");
        }
    }

    private static void validateRole(MemberRole role) {
        if (role == null) {
            throw new IllegalArgumentException("멤버 권한은 필수 값입니다.");
        }
    }
}
