package roomescape.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Member;
import roomescape.domain.MemberRole;

public final class MemberEntityMapper {

    public static final RowMapper<Member> MEMBER_MAPPER = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("name"),
            MemberRole.valueOf(rs.getString("role"))
    );

    private MemberEntityMapper() {
    }
}
