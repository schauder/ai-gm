package de.schauderhaft.roadtoai.trivial;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = DigitalGameMaster.class)
public class VectorDbTests {

	@Autowired
	JdbcTemplate jdbc;

	@Test
	void connectToDb() {
		Integer count = jdbc.queryForObject("select count(*) from information_schema.tables", Integer.class);

		assertThat(count).isGreaterThan(0);
	}
}
