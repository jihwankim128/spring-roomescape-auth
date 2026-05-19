package roomescape.controller.client.api.dto;

import roomescape.domain.MemberRole;
import roomescape.service.result.MemberResult;

public record MemberResponse(
        long id,
        String name,
        MemberRole role
) {

    public static MemberResponse from(MemberResult result) {
        return new MemberResponse(result.id(), result.name(), result.role());
    }
}
