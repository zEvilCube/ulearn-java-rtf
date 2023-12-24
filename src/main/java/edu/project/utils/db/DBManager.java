package edu.project.utils.db;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public final class DBManager {
    private DBManager() {}

    public static ConnectionSource connectionSource;

    public static ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public static void setConnectionSource(String url) {
        try {
            connectionSource = new JdbcConnectionSource(url);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void createAll(DAOManager<?>... daoList) {
        try {
            for (var dao: daoList) {
                TableUtils.createTableIfNotExists(connectionSource, dao.getDao().getDataClass());
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void clearTable(DAOManager<?> dao) {
        try {
            TableUtils.clearTable(connectionSource, dao.getDao().getDataClass());
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
