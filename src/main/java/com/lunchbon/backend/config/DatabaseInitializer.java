package com.lunchbon.backend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String user;

	@Value("${spring.datasource.password}")
	private String password;

	private String extractDbName() {
		return dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
	}

	@PostConstruct
	public void initDatabase() {
		String dbName = extractDbName();
		String jdbcRootUrl = dbUrl.replace("/" + dbName, "");

		try (Connection conn = DriverManager.getConnection(jdbcRootUrl, user, password)) {
			if (!databaseExists(conn, dbName)) {
				createDatabase(conn, dbName);
			} else if (isDatabaseEmpty(dbUrl)) {
				dropDatabase(conn, dbName);
				createDatabase(conn, dbName);
			} else {
				logger.info("✅ Database '{}' already exists and has tables. Skipping creation.", dbName);
			}
		} catch (SQLException e) {
			logger.error("❌ Failed to initialize database '{}': {}", dbName, e.getMessage(), e);
		}
	}

	private boolean databaseExists(Connection conn, String dbName) throws SQLException {
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

	private boolean isDatabaseEmpty(String dbUrl) {
		try (Connection conn = DriverManager.getConnection(dbUrl, user, password);
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SHOW TABLES")) {

			boolean hasTables = rs.next();
			logger.info("📊 Database has tables: {}", hasTables);
			return !hasTables;

		} catch (SQLException e) {
			logger.warn("⚠️ Could not connect to database. Assuming it's missing. Error: {}", e.getMessage());
			return true;
		}
	}

	private void createDatabase(Connection conn, String dbName) throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			stmt.execute("CREATE DATABASE " + dbName);
			logger.info("✅ Created database '{}'", dbName);
		}
	}

	private void dropDatabase(Connection conn, String dbName) throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			stmt.execute("DROP DATABASE " + dbName);
			logger.info("🗑️ Dropped database '{}'", dbName);
		}
	}
}
