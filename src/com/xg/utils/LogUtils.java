package com.xg.utils;

import java.io.File;

public class LogUtils {

	public static void removeLog() {
		String classPath = LogUtils.class.getClassLoader().getResource("")
				.getFile();
		File file = new File(classPath);
		String logPath = file.getParent()+ File.separator + "log";
		deleteFile(new File(logPath));
	}

	public static void deleteFile(File file) {
		if (file.isFile()) {// ��ʾ���ļ������ļ���
			file.delete();
		} else {
			// ���ȵõ���ǰ��·��
			String[] childFilePaths = file.list();
			for (String childFilePath : childFilePaths) {
				File childFile = new File(file.getAbsolutePath() + "\\"
						+ childFilePath);
				deleteFile(childFile);
			}
			file.delete();
		}
	}

}
