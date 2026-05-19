package roomescape.service.fake;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.DuplicateEntityException;
import roomescape.domain.Member;
import roomescape.repository.MemberRepository;

public class FakeMemberRepository implements MemberRepository {

    private final Map<Long, Member> members = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Member save(Member member) {
        if (existsByName(member.getName())) {
            throw new DuplicateEntityException("이미 존재하는 멤버입니다.");
        }

        long id = idGenerator.getAndIncrement();
        Member savedMember = new Member(id, member.getName(), member.getRole());
        members.put(id, savedMember);
        return savedMember;
    }

    @Override
    public Optional<Member> findById(long id) {
        return Optional.ofNullable(members.get(id));
    }

    @Override
    public boolean existsByName(String name) {
        return members.values().stream()
                .anyMatch(member -> member.getName().equals(name));
    }
}
