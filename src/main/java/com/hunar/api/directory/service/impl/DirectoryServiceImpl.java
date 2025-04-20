package com.hunar.api.directory.service.impl;


import com.hunar.api.directory.service.DirectoryService;
import com.hunar.api.directory.util.DirectoryResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;

@Service
public class DirectoryServiceImpl implements DirectoryService {

//	@Value("${local.directory.path}")
	private String dirPath;

	public static Logger logger = LogManager.getLogger();

	@Override
	public String createDirectoryForDocInOut(String webPath) {

		// webPath = "E:\\XaltoFiles\\XaltoExchange\\";

		webPath = dirPath;

		LocalDate dateString = LocalDate.now();

		int year = dateString.getYear();

		int month = dateString.getMonthValue();

		int day = dateString.getDayOfMonth();

		String path = webPath + DirectoryResource.DIRECTORY_INOUT;

		String yearPathString = path + "\\" + year;

		String monthPathString = yearPathString + "\\" + month;

		String dayPathString = monthPathString + "\\" + day;

		File pathDir = new File(path);

		File yearDir = new File(yearPathString);

		File monthDir = new File(monthPathString);

		File dayDir = new File(dayPathString);

		try {

			boolean result = false;

			try {
				dayDir.mkdirs();
				logger.info("DIR created");
				result = true;
				return dayDir.getPath();
			} catch (Exception se) {
				se.printStackTrace();
			}
		} catch (SecurityException e) {
		}

		return dayDir.getPath();
	}

	@Override
	public String createDirectoryForPrinter(String path) {
		return null;
	}

	@Override
	public String createDirectoryForWebService(String path) {
		return null;
	}

}
