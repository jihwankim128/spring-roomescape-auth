package integration.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.jdbc.core.JdbcTemplate;

@TestComponent
public class MemberDataSource {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void clearTable() {
        jdbcTemplate.execute("TRUNCATE TABLE member");
    }

    public void clearId() {
        jdbcTemplate.execute("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
    }
}
