package com.po;
/**
 * �ı���Ϣ
 * @author Sumkor
 *
 */
public class TextMessage extends BaseMessage{
	private String Content;
	private String MsgId;//�����ı���Ϣ����id
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
