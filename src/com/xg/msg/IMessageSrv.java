package com.xg.msg;

public interface IMessageSrv {
	
	public void perSussess(String filePath);
	
	public void perFailed(String filePath);
	
	public void perOther(String filePath);
}
