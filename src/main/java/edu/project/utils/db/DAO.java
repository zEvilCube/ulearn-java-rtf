package edu.project.utils.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.List;

public class DAO<T> {
    private final Dao<T, String> dao;

    public DAO(Class<T> daoClass) {
        try {
            dao = DaoManager.createDao(DB.getConnectionSource(), daoClass);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Dao<T, String> getDao() {
        return dao;
    }

    public T get(String columnName, Object value) {
        try {
            return dao.queryBuilder().where().eq(columnName, value).queryForFirst();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public List<T> getAll() {
        try {
            return dao.queryBuilder().query();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void update(T daoObject) {
        try {
            dao.createOrUpdate(daoObject);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void updateAll(List<T> daoObjects) {
        daoObjects.forEach(this::update);
    }
}
