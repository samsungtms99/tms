package com.hunar.api.exceptionHandling.util;

import com.hunar.api.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Errors {

	public static Logger logger = LogManager.getLogger(Errors.class);
	private static final String BUNDLE_NAME = "com.hunar.api.exceptionHandling.util.message";

//	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,Locale.getDefault());
static ResourceBundle messages = null;
	public static ResourceBundle getMessages(Locale locale) {


		try {
			// Try to load the resource bundle for the specific locale
			messages = ResourceBundle.getBundle(BUNDLE_NAME, locale);
		} catch (MissingResourceException e) {
			// Log the exception and fall back to default locale (en_US)
			logger.warn("Resource bundle for locale {} not found. Falling back to default locale.", locale);
			messages = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ENGLISH); // Fallback to en_US
		}

		return messages;
	}
	private Errors() {
	}

	public static String getString(String key) {
		try {
			return messages.getString(key);
		} catch (MissingResourceException e) {
			// Log the error or provide a more informative message
			System.err.println("Error: Missing resource for key '" + key + "'");
			return "Error: Missing resource for key '" + key + "'";
		}
	}

	public static String getValue(String key, String[] args) {
		return MessageFormat.format(getString(key), args);
	}

	public static String getValue(String key, Object[] arguments) {
		return MessageFormat.format(getString(key), arguments);
	}

	public static String getValue(String key) {
		return getString(key);
	}

//	public static String getString(String key) {
//		try {
//			return RESOURCE_BUNDLE.getString(key);
//		} catch (MissingResourceException e) {
//			return '!' + key + '!';
//		}
//	}
//
//	public static String getValue(String key, String[] args) {
//		return MessageFormat.format(getString(key), args);
//	}
//
//	public static String getValue(String key, Object[] arguments) {
//		return MessageFormat.format(getString(key), arguments);
//	}
//
//	public static String getValue(String key) {
//		return getString(key);
//	}
}
