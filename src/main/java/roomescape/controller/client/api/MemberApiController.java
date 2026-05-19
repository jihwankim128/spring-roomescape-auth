package roomescape.controller.client.api;

import static org.springframework.http.HttpStatus.CREATED;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.client.api.dto.MemberResponse;
import roomescape.controller.client.api.dto.MemberSignUpRequest;
import roomescape.service.MemberService;
import roomescape.service.result.MemberResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Validated
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> signUp(@Valid @RequestBody MemberSignUpRequest request) {
        MemberResult result = memberService.signUp(request.toCommand());
        return ResponseEntity.status(CREATED)
                .body(MemberResponse.from(result));
    }
}
