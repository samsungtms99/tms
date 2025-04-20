package com.hunar.api.exceptionHandling.service.impl;


import com.hunar.api.exceptionHandling.bean.ExceptionHandlerBean;
import com.hunar.api.exceptionHandling.service.ExceptionHandlerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;

@Service
public class ExceptionHandlerServiceImpl implements ExceptionHandlerService {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public ExceptionHandlerBean composeExceptionBody(Exception e) {

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionStackTrace = sw.toString();
		logger.error(exceptionStackTrace);

		Throwable rootExc = getCause(e);
		ExceptionHandlerBean exceptionHandlerBean = new ExceptionHandlerBean(rootExc.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		return exceptionHandlerBean;
	}

	Throwable getCause(Throwable e) {
		Throwable cause = null;
		Throwable result = e;

		while (null != (cause = result.getCause()) && (result != cause)) {
			result = cause;
		}
		return result;
	}

}
