package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.DuplicateEntityException;
import roomescape.domain.Member;
import roomescape.repository.MemberRepository;
import roomescape.service.command.MemberSignUpCommand;
import roomescape.service.result.MemberResult;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResult signUp(MemberSignUpCommand command) {
        validateDuplicateName(command.name());

        Member member = new Member(command.name());
        return MemberResult.from(memberRepository.save(member));
    }

    private void validateDuplicateName(String name) {
        if (memberRepository.existsByName(name)) {
            throw new DuplicateEntityException("이미 존재하는 멤버입니다. 멤버 명: %s", name);
        }
    }
}
