package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.global.auth.MemberPrincipal;
import roomescape.domain.EntityNotFoundException;
import roomescape.domain.Member;
import roomescape.repository.MemberRepository;
import roomescape.service.command.LoginCommand;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberPrincipal login(LoginCommand command) {
        Member member = memberRepository.findByName(command.name())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 멤버입니다."));

        return new MemberPrincipal(member.getName());
    }
}
