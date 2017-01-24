package com.xg.core.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.zxing.NotFoundException;
import com.xg.core.IRenameSrv;
import com.xg.msg.IMessageSrv;
import com.xg.utils.ImgDecode;

public class RenameImpl implements IRenameSrv {

	private final Logger logger = LogManager.getLogger(RenameImpl.class
			.getName());

	private List<IMessageSrv> msgSrvList;

	private final static Set<String> set = new HashSet<String>();

	static {
		// 设置可解析文件
		set.add(".jpg");
		set.add(".png");
	}

	public RenameImpl() {
		msgSrvList = new ArrayList<>();
	}

	public void registerMsg(IMessageSrv msgSrv) {
		msgSrvList.add(msgSrv);
	}

	@Override
	public String rename(String inPutPath, String outPutPath,int cover) {
		/**
		 * 算法简述： 从某个给定的需查找的文件夹出发，搜索该文件夹的所有子文件夹及文件，
		 * 若为文件，则进行匹配，匹配成功则加入结果集，若为子文件夹，则进队列。 队列不空，重复上述操作，队列为空，程序结束，返回结果。
		 */
		// 判断目录是否存在
		File baseDir = new File(inPutPath);
		File outPutPathDir = new File(outPutPath);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			return "输入目录不正确";
		} else if(!outPutPathDir.exists() || !outPutPathDir.isDirectory()){
			return "输出目录不正确";
		} else {
			String[] filelist = baseDir.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(inPutPath + File.separator
						+ filelist[i]);
				String outPutFullPath = outPutPath + File.separator
						+ filelist[i];

				// System.out.println(readfile.getName());
				if (!readfile.isDirectory()) {
					String fullPath = readfile.getPath();
					if (chekPath(fullPath)) {
						try {
							doRename(fullPath, outPutFullPath,cover);
							// 成功
							this.perSussess(fullPath);
						} catch (Exception e) {
							logger.error("rename exception",e);
							// 失败
							this.perFailed(fullPath);
						}
					} else {
						// 不是可解析文件
						this.perOther(fullPath);
					}
				} else if (readfile.isDirectory()) {
					rename(inPutPath + File.separator + filelist[i], outPutPath
							+ File.separator + filelist[i],cover);
				}
			}
		}
		return "执行完成";
	}

	private boolean chekPath(String fullPath) {
		String fullPathLower = fullPath.trim().toLowerCase();
		for (String ends : set) {
			if (fullPathLower.endsWith(ends)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 执行文件的重命名
	 * 
	 * @param imgPath
	 * @throws IOException
	 * @throws NotFoundException
	 */
	private void doRename(String imgPath, String outPutPath,int cover) throws Exception {
		String result = ImgDecode.decode(imgPath);
		File file = new File(imgPath);
		File outfile = new File(outPutPath);
		String outParentFileName = outfile.getParent();
		String extensionName = getExtensionName(imgPath);
		File newFile = new File(outParentFileName + File.separator + result
				+ "." + extensionName);
		if(1==cover){
			newFile = renameFile(newFile);
		}
		// 创建目录
		mkdirs(outParentFileName);
		// 拷贝文件
		nioTransferCopy(file, newFile);
	}

	public static void mkdirs(String path) {
		File fd = null;
		try {
			fd = new File(path);
			if (!fd.exists()) {
				fd.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fd = null;
		}
	}

	private void nioTransferCopy(File source, File target)
			throws IOException {
		
		FileChannel in = null;
		FileChannel out = null;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		try {
			inStream = new FileInputStream(source);
			outStream = new FileOutputStream(target);
			in = inStream.getChannel();
			out = outStream.getChannel();
			in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inStream != null) {
				inStream.close();
			}
			if (in != null) {
				in.close();
			}
			if (outStream != null) {
				outStream.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	
	private File renameFile(File target){
		int count = 1;
		if(target.exists()){
			String fileName = target.getPath();
			String extensionName = this.getExtensionName(fileName);
			String newSubPath = fileName.substring(0,fileName.lastIndexOf(extensionName)-1);
			while(true){
				String newFullName = newSubPath + "("+count+")."+extensionName;
				target = new File(newFullName);
				if(target.exists()){
					count++;
				}else{
					return target;
				}
			}
		}
		return target;
	}
	

	/**
	 * 取得文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	private void perSussess(String fullPath) {
		for (IMessageSrv msgImpl : msgSrvList) {
			msgImpl.perSussess(fullPath);
		}
	}

	private void perOther(String fullPath) {
		for (IMessageSrv msgImpl : msgSrvList) {
			msgImpl.perOther(fullPath);
		}
	}

	private void perFailed(String fullPath) {
		for (IMessageSrv msgImpl : msgSrvList) {
			msgImpl.perFailed(fullPath);
		}
	}

}
