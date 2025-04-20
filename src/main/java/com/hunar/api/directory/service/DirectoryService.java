package com.hunar.api.directory.service;

// TODO: Auto-generated Javadoc
/**
 * The Interface DirectoryService.
 */
public interface DirectoryService {

	/**
	 * Creates the directory for doc in out.
	 *
	 * @param path the path
	 * @return the string
	 */
	public String createDirectoryForDocInOut(String path);

	/**
	 * Creates the directory for printer.
	 *
	 * @param path the path
	 * @return the string
	 */
	public String createDirectoryForPrinter(String path);

	/**
	 * Creates the directory for web service.
	 *
	 * @param path the path
	 * @return the string
	 */
	public String createDirectoryForWebService(String path);

}
