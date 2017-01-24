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
		if (file.isFile()) {// 表示该文件不是文件夹
			file.delete();
		} else {
			// 首先得到当前的路径
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
