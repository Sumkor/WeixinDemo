package com.po;
/**
 * 文本消息
 * @author Sumkor
 *
 */
public class TextMessage extends BaseMessage{
	private String Content;
	private String MsgId;//接收文本消息所用id
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
}
