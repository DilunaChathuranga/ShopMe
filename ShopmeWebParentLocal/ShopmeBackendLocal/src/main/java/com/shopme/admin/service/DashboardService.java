package com.shopme.admin.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.dashboard.DashboardInfo;

@Service
public class DashboardService {

	@Autowired
	private EntityManager entityManager;
	
	public DashboardInfo loadSummary() {
		
		DashboardInfo summary = new DashboardInfo();
		
		Query query = entityManager.createQuery("SELECT "
				+ "(SELECT COUNT(DISTINCT u.id) AS totalUsers FROM User u),"
				+ "(SELECT COUNT(DISTINCT c.id) AS totalCategories FROM Category c), "
				+ "(SELECT COUNT(DISTINCT b.id) AS totalBrands FROM Brand b), "
				+ "(SELECT COUNT(DISTINCT p.id) AS totalProducts FROM Product p), "
				+ "(SELECT COUNT(DISTINCT q.id) AS totalQuestions FROM Question q), "
				+ "(SELECT COUNT(DISTINCT r.id) AS totalReviews FROM Review r), "
				+ "(SELECT COUNT(DISTINCT cu.id) AS totalCustomers FROM Customer cu), "
				+ "(SELECT COUNT(DISTINCT o.id) AS totalOrders FROM Order o), "
				+ "(SELECT COUNT(DISTINCT sr.id) AS totalShippingRates FROM ShippingRate sr), "
				
				+ "(SELECT COUNT(DISTINCT u.id) AS enabledUsersCount FROM User u WHERE u.enabled=1 ), "
				+ "(SELECT COUNT(DISTINCT c.id) AS rootCategoriesCount FROM Category c WHERE c.parent=null ), "
				+ "(SELECT COUNT(DISTINCT c.id) AS enabledCategoriesCount FROM Category c WHERE c.enabled=1 ), "
				+ "(SELECT COUNT(DISTINCT p.id) AS enabledProductsCount FROM Product p WHERE p.enabled=1 ), "
				+ "(SELECT COUNT(DISTINCT p.id) AS inStockProductsCount FROM Product p WHERE p.inStock=1 ), "
				+ "(SELECT COUNT(DISTINCT p.id) AS outOfStockProductsCount FROM Product p WHERE p.inStock=0 ), "
				+ "(SELECT COUNT(DISTINCT q.id) AS approvedQuestionsCount FROM Question q WHERE q.approved=1), "
				+ "(SELECT COUNT(DISTINCT q.id) AS unapprovedQuestionsCount FROM Question q WHERE q.approved=0), "
				+ "(SELECT COUNT(DISTINCT q.id) AS answeredQuestionsCount FROM Question q WHERE q.answerer != null), "
				+ "(SELECT COUNT(DISTINCT q.id) AS unansweredQuestionsCount FROM Question q WHERE q.answerer=null) "
				//+ "st.value as siteName"
				+ "FROM Setting st WHERE st.key='site_name'"
				);
		
		List<Object[]> entityCounts = query.getResultList();
		Object[] arrayCounts = entityCounts.get(0);
		
		int count = 0;
		
		summary.setTotalUsers((Long) arrayCounts[count++]); //0
		summary.setTotalCategories((Long) arrayCounts[count++]); //1
		summary.setTotalBrands((Long) arrayCounts[count++]); //2
		summary.setTotalProducts((Long) arrayCounts[count++]); //3
		summary.setTotalQuestions((Long) arrayCounts[count++]); //4
		summary.setTotalReviews((Long) arrayCounts[count++]); //5
		summary.setTotalCustomers((Long) arrayCounts[count++]); //6
		summary.setTotalOrders((Long) arrayCounts[count++]); //7
		summary.setTotalShippingRates((Long) arrayCounts[count++]); //8
		summary.setEnabledUsersCount((Long) arrayCounts[count++]); //9
		summary.setRootCategoriesCount((Long) arrayCounts[count++]); //10
		summary.setEnabledCategoriesCount((Long) arrayCounts[count++]); //11
		summary.setEnabledProductsCount((Long) arrayCounts[count++]); //12
		summary.setInStockProductsCount((Long) arrayCounts[count++]); //13
		summary.setOutOfStockProductsCount((Long) arrayCounts[count++]); //14
		summary.setApprovedQuestionsCount((Long) arrayCounts[count++]);
		summary.setUnapprovedQuestionsCount((Long) arrayCounts[count++]);
		summary.setAnsweredQuestionsCount((Long) arrayCounts[count++]);
		summary.setUnansweredQuestionsCount((Long) arrayCounts[count++]);
		
		//logic -> diableUsers=totalUsers - enabledUsers
		summary.setDisabledUsersCount(summary.getTotalUsers()- summary.getEnabledUsersCount());
		summary.setDisabledCategoriesCount(summary.getTotalCategories() -  summary.getEnabledCategoriesCount());
		summary.setDisabledProductsCount(summary.getTotalProducts()- summary.getEnabledProductsCount());
				
	
		return summary;
	}
}
