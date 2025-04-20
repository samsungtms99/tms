package com.hunar.api.criteria.service;


import com.hunar.api.criteria.bean.FilterCriteriaBean;

import java.util.List;

public interface FilterCriteriaService<T> {

	public List<?> getListOfFilteredData(Class<?> clazz, List<FilterCriteriaBean> criteriaList, Integer limit);

}
