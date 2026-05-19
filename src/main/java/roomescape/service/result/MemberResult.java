package roomescape.service.result;

import roomescape.domain.Member;
import roomescape.domain.MemberRole;

public record MemberResult(
        long id,
        String name,
        MemberRole role
) {

    public static MemberResult from(Member member) {
        return new MemberResult(member.getId(), member.getName(), member.getRole());
    }
}
