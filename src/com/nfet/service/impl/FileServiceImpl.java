/*
 * 
 * 
 * 
 */
package com.nfet.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.nfet.Setting;
import com.nfet.FileInfo.FileType;
import com.nfet.service.FileService;
import com.nfet.util.FreemarkerUtils;
import com.nfet.util.SettingUtils;

import freemarker.template.TemplateException;

/**
 * Service - 文件
 * 
 * 
 * 
 */
@Service("fileServiceImpl")
public class FileServiceImpl implements FileService, ServletContextAware {

	/** servletContext */
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public boolean isValid(FileType fileType, MultipartFile multipartFile) {
		if (multipartFile == null) {
			return false;
		}
		Setting setting = SettingUtils.get();
		String[] uploadExtensions = null;
		if (fileType == FileType.product) {
			uploadExtensions = setting.getProductFileExtensions();
		} else if (fileType == FileType.firmware) {
			uploadExtensions = setting.getFirmwareFileExtensions();
		}
		if (ArrayUtils.isNotEmpty(uploadExtensions)) {
			return FilenameUtils.isExtension(multipartFile.getOriginalFilename(), uploadExtensions);
		}
		return false;
	}

	public void write(String content) {
		Setting setting = SettingUtils.get();
		String recordPath = setting.getRecordFilePath();
		String fileExtension = setting.getRecordFileExtension();
		try {
			recordPath = FreemarkerUtils.process(recordPath, null);
			SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
			Date date = new Date(System.currentTimeMillis());
			String fileName = sdf.format(date) + "." + fileExtension;
			String filePath = recordPath + fileName;
			File file = new File(servletContext.getRealPath(filePath));
			FileUtils.write(file, content);
			// if (!file.getParentFile().exists()) {
			// file.getParentFile().mkdirs();
			// }
			// BufferedWriter output = new BufferedWriter(new FileWriter(file));
			// output.write(content);
			// output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}
}