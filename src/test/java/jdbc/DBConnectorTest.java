package jdbc;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

public class DBConnectorTest {
	@Test
	public void connection() {
		Connection con = DBConnector.getConnection();
		assertNotNull(con);
	}
}