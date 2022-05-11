package com.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.DrugDAO;
import com.app.entity.Drug;

@Repository
@Transactional(rollbackFor = Exception.class)
public class DrugDAOImpl extends BaseDAOImpl<Drug> implements DrugDAO<Drug>{

}
