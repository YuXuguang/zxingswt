package com.xg.core;

import com.xg.msg.IMessageSrv;

public interface IRenameSrv {
	
	public String rename(String inPutPath,String outPutPath,int cover);
	
	public void registerMsg(IMessageSrv msgSrv);
	
}
