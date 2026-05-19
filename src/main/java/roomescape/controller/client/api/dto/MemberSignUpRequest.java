package roomescape.controller.client.api.dto;

import jakarta.validation.constraints.NotBlank;
import roomescape.service.command.MemberSignUpCommand;

public record MemberSignUpRequest(
        @NotBlank(message = "멤버 이름은 비어있을 수 없습니다.")
        String name
) {

    public MemberSignUpCommand toCommand() {
        return new MemberSignUpCommand(name);
    }
}
