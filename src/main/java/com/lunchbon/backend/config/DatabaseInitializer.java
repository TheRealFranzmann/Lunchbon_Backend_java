package com.lunchbon.backend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

	private final String jdbcUrl = "jdbc:mariadb://localhost:3306/";
	private final String dbName = "lunchbon";
	private final String user = "testUser";
	private final String password = "test";

	@PostConstruct
	public void initDatabase() {
		try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password)) {
			if (!databaseExists(conn)) {
				createDatabase(conn);
			} else if (isDatabaseEmpty()) {
				dropDatabase(conn);
				createDatabase(conn);
			} else {
				logger.info("✅ Database '{}' already exists and has tables. Skipping creation.", dbName);
			}
		} catch (SQLException e) {
			logger.error("❌ Failed to initialize database '{}': {}", dbName, e.getMessage(), e);
		}
	}

	private boolean databaseExists(Connection conn) throws SQLException {
		String query = "SHOW DATABASES LIKE ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, dbName);
			try (ResultSet rs = ps.executeQuery()) {
				boolean exists = rs.next();
				logger.info("🔍 Database '{}' exists: {}", dbName, exists);
				return exists;
			}
		}
	}

	private boolean isDatabaseEmpty() throws SQLException {
		String urlWithDb = jdbcUrl + dbName;
		try (Connection conn = DriverManager.getConnection(urlWithDb, user, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SHOW TABLES")) {

			boolean hasTables = rs.next();
			logger.info("📊 Database '{}' has tables: {}", dbName, hasTables);
			return !hasTables;

		} catch (SQLException e) {
			logger.warn("⚠️ Could not connect to '{}'. Assuming it's missing. Error: {}", dbName, e.getMessage());
			return true;
		}
	}

	private void createDatabase(Connection conn) throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			stmt.execute("CREATE DATABASE " + dbName);
			logger.info("✅ Created database '{}'", dbName);
		}
	}

	private void dropDatabase(Connection conn) throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			stmt.execute("DROP DATABASE " + dbName);
			logger.info("🗑️ Dropped database '{}'", dbName);
		}
	}
}