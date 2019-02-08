package com.interswitch.redemptionapi.Repository;


import com.interswitch.redemptionapi.Domain.Redemption;

import java.util.Date;
import java.util.List;

public interface IRedemptionRepository<T extends Redemption> {

    public T insert(T model);

    public boolean update(T model);

    public T findByCode(String code);

    public T findByDate(Date date);

    public List<T> findAll();

    public boolean delete(T model);

    Redemption insertRedemptionDetails(Redemption redemption);
}
