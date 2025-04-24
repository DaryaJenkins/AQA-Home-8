package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner queryrunner = new QueryRunner();

    private SQLHelper() {}

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static String getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            return queryrunner.query(connection, codeSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var connection = getConnection()) {
            queryrunner.execute(connection, "DELETE FROM auth_codes");
            queryrunner.execute(connection, "DELETE FROM cards");
            queryrunner.execute(connection, "DELETE FROM card_transactions");
            queryrunner.execute(connection, "DELETE FROM users");
        }
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        try (var connection = getConnection()) {
            queryrunner.execute(connection, "DELETE FROM auth_codes");
        }
    }
}