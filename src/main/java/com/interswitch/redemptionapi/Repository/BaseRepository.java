package com.interswitch.redemptionapi.Repository;


import com.interswitch.redemptionapi.Domain.Redemption;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;


public abstract class BaseRepository<T extends Redemption> implements IRedemptionRepository {
    protected static final String RESULT_COUNT = "count";
    protected final String SINGLE_RESULT = "object";
    protected final String MULTIPLE_RESULT = "list";
    protected JdbcTemplate jdbcTemplate;
    protected JdbcTemplate readOnlyJdbcTemplate;
    protected SimpleJdbcCall insert, update, delete, findByCode, findByDate, findAll;

    public abstract void setDataSource(DataSource dataSource);

    @Override
    public Redemption insert(Redemption model) {
        SqlParameterSource in = new BeanPropertySqlParameterSource(model);
        Map<String, Object> m = insert.execute(in);
        long id = (long) m.get("RedemptionId");
        model.setId(id);
        return model;
    }

    @Override
    public boolean update(Redemption model) {
        return false;
    }

    public T findByCode(String code) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("Code", code);
        Map<String, Object> m = findByCode.execute(in);
        List<T> list = (List<T>) m.get(SINGLE_RESULT);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public T findByDate(Date date) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("Date", date);
        Map<String, Object> m = findByDate.execute(in);
        List<T> list = (List<T>) m.get(SINGLE_RESULT);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

//    public List<T> findAll() {
//        return null;
//    }

    public List<T> findAll() {
//        SqlParameterSource in = new MapSqlParameterSource().addValue("pageNumber", pageNumber).addValue("pageSize", pageSize);
        Map<String, Object> m = findAll.execute();
        List<T> content = (List<T>) m.get(MULTIPLE_RESULT);
        List<Long> countList = (List<Long>) m.get(RESULT_COUNT);

//        long count = 0;
//        if (Objects.nonNull(countList) && !countList.isEmpty()) {
//            count = countList.get(0);
//        }
        return content;
    }

    @Override
    public boolean delete(Redemption model) {
        return false;
    }

    @Override
    public Redemption insertRedemptionDetails(Redemption redemption) {
        return null;
    }

}

