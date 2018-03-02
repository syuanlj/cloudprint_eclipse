/*
 * 
 * 
 * 
 */
package com.nfet.service;

import org.springframework.web.multipart.MultipartFile;

import com.nfet.FileInfo.FileType;

/**
 * Service - 文件
 * 
 * 
 * 
 */
public interface FileService {

	/**
	 * 文件验证
	 * 
	 * @param fileType
	 *            文件类型
	 * @param multipartFile
	 *            上传文件
	 * @return 文件验证是否通过
	 */
	boolean isValid(FileType fileType, MultipartFile multipartFile);

	/**
	 * 写入文件
	 * 
	 * @param content
	 *            文件类容
	 */
	public void write(String content);
}