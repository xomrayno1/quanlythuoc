package com.app.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.InvoiceDAO;
import com.app.entity.Invoice;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDAOImpl extends BaseDAOImpl<Invoice> implements InvoiceDAO<Invoice>{

	public Long save(Invoice invoice) {
		// TODO Auto-generated method stub
		return (Long) factory.getCurrentSession().save(invoice);
	}
 
	
	public List<Map<String, Object>> getBarChartByMonth(int year) {
		// TODO Auto-generated method stub
		String Sql = "select MONTH(i.createDate) month,  sum(i.total_price) totalPrice from invoice i where i.object_type = 1 and YEAR(i.createDate) = ? GROUP by month(i.createDate)";
			 
		org.hibernate.query.Query  query = factory.getCurrentSession().createNativeQuery(Sql);
		query.setParameter(1, year);
		List<Map<String , Object>> barCharts  = 
				query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		
		return barCharts ;
	}
 
}
