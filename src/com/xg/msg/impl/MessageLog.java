package com.xg.msg.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xg.msg.IMessageSrv;

public class MessageLog implements IMessageSrv {
	
	private final Logger success = LogManager.getLogger("success");
	private final Logger failed = LogManager.getLogger("failed");
	private final Logger other = LogManager.getLogger("other");

	@Override
	public void perSussess(String filePath) {
		success.info(filePath);
	}

	@Override
	public void perFailed(String filePath) {
		failed.info(filePath);
	}

	@Override
	public void perOther(String filePath) {
		other.info(filePath);
	}
}
