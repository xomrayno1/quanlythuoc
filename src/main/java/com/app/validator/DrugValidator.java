package com.app.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.entity.Drug;
import com.app.service.DrugService;

@Component
public class DrugValidator implements Validator{

	@Autowired
	DrugService drugService;
	
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.isAssignableFrom(Drug.class);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Drug drug = (Drug) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.required");		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "error.required");
		if(!StringUtils.isEmpty(drug.getName())) {
			List<Drug> lstDrug = drugService.getAllByProperty("name", drug.getName());		
			if(drug.getId() != null) {
				if(lstDrug != null && lstDrug.size()> 0) {
					Drug current = drugService.findById(drug.getId());
					if(!drug.getName().equals(current.getName())) {
						errors.rejectValue("name", "error.exists");
					}
				}
			}else {
				if(lstDrug != null && lstDrug.size()> 0) {
					errors.rejectValue("name", "error.exists");
				}
			}
		}
 
	}
}
