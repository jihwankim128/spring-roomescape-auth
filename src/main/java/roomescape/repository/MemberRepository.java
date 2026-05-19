package roomescape.repository;

import java.util.Optional;
import roomescape.domain.Member;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(long id);

    boolean existsByName(String name);
}
