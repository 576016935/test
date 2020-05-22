package com.qf.dao;

import com.qf.entity.Address;

import java.util.List;

public interface IAddressDao {

    List<Address> queryByUid(Integer uid);

    int addAddress(Address address);

    Address queryById(Integer aid);
}
