package com.app.service;

import java.util.List;

import com.app.dto.Paging;
import com.app.entity.Drug;

public interface DrugService {
	void add(Drug drug) throws Exception;
	void delete(Drug drug) ;
	void update(Drug drug) throws Exception;
	List<Drug> getAll(Drug drug , Paging paging);
	List<Drug> getAllByProperty(String property , Object object);
	Drug findById(Long id);
}
