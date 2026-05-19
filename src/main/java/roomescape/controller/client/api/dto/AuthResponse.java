package roomescape.controller.client.api.dto;

import roomescape.domain.Member;
import roomescape.domain.MemberRole;

public record AuthResponse(
        String name,
        MemberRole role
) {

    public static AuthResponse from(Member member) {
        return new AuthResponse(member.getName(), member.getRole());
    }
}
