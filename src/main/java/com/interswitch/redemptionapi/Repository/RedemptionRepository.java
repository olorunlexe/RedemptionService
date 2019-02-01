package com.interswitch.redemptionapi.Repository;

import com.interswitch.redemptionapi.Domain.Redemption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class RedemptionRepository extends BaseRepository<Redemption> {


    protected SimpleJdbcCall findByCode;

    @Autowired
    @Override
    public void setDataSource(@Qualifier(value = "dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        insert = new SimpleJdbcCall(dataSource).withProcedureName("usp_InsertRedemptionDetails").withReturnValue();
        // update = new SimpleJdbcCall(jdbcTemplate).withProcedureName("update_agent").withReturnValue();
        findByCode = new SimpleJdbcCall(jdbcTemplate).withProcedureName("usp_GetRedemptionDetailsByCode")
                .returningResultSet(SINGLE_RESULT, new BeanPropertyRowMapper<>(Redemption.class));
        //findAll = new SimpleJdbcCall(jdbcTemplate).withProcedureName("find_all_agents").returningResultSet(RESULT_COUNT, new RowCountMapper())
        // .returningResultSet(MULTIPLE_RESULT, new BeanPropertyRowMapper<>(Redemption.class));
        findByDate = new SimpleJdbcCall(jdbcTemplate).withProcedureName("usp_GetRedemptionDetailsByDate")
                .returningResultSet(SINGLE_RESULT, new BeanPropertyRowMapper<>(Redemption.class));
    }

}
