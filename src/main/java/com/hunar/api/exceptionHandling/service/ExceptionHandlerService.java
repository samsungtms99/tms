package com.hunar.api.exceptionHandling.service;


import com.hunar.api.exceptionHandling.bean.ExceptionHandlerBean;

public interface ExceptionHandlerService {

	public ExceptionHandlerBean composeExceptionBody(Exception e);

}
