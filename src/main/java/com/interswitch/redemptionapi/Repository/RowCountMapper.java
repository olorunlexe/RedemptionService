package com.interswitch.redemptionapi.Repository;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowCountMapper implements RowMapper {
    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getLong(1);
    }
}
