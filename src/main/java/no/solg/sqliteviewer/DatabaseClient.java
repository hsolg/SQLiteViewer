package no.solg.sqliteviewer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseClient {
    private Connection conn;

    public DatabaseClient(File file) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTableNames() {
        List<String> names = new ArrayList<>();
        try {
            ResultSet rs = conn.getMetaData().getTables(null, null, null, null);
            while (rs.next()) {
                names.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public List<String> getColumnNames(String tableName) {
        List<String> names = new ArrayList<>();
        final String sql = "SELECT * FROM " + tableName + " LIMIT 0";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                names.add(metadata.getColumnLabel(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public int getCount(String tableName, String colName) {
        int count = -1;
        final String sql = "SELECT count(*) FROM " + tableName;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public double getAggregated(String tableName, String colName, String func) {
        double aggregated = -1;
        final String sql = "SELECT " + func + "(" + colName + ") FROM " + tableName;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            aggregated = rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aggregated;
    }
}
