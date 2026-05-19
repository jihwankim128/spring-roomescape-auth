package roomescape.repository.jdbc;

import static roomescape.repository.jdbc.MemberEntityMapper.MEMBER_MAPPER;

import java.sql.PreparedStatement;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Member;
import roomescape.repository.MemberRepository;
import roomescape.repository.util.RepositoryExceptionTranslator;

@Repository
@RequiredArgsConstructor
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Member save(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO member (name, role) VALUES (?, ?)";

        RepositoryExceptionTranslator.execute(
                () -> jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, member.getName());
                    ps.setString(2, member.getRole().name());
                    return ps;
                }, keyHolder), "이미 존재하는 멤버입니다.");

        Long id = keyHolder.getKey().longValue();
        return new Member(id, member.getName(), member.getRole());
    }

    @Override
    public Optional<Member> findById(long id) {
        try {
            String sql = "SELECT * FROM member WHERE id = ?";
            Member member = jdbcTemplate.queryForObject(sql, MEMBER_MAPPER, id);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT EXISTS (SELECT 1 FROM member WHERE name = ?)";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, name);
        return Boolean.TRUE.equals(result);
    }
}
