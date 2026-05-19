package roomescape.controller.client.api.dto;

import jakarta.validation.constraints.NotBlank;
import roomescape.service.command.LoginCommand;

public record LoginRequest(
        @NotBlank(message = "로그인 이름은 비어있을 수 없습니다.")
        String name
) {

    public LoginCommand toCommand() {
        return new LoginCommand(name);
    }
}
