package com.po;
/**
 * ��Ϣ����
 * @author Sumkor
 *
 */
public class BaseMessage {
	private String ToUserName;//���շ�΢�ź�
	private String FromUserName;//���ͷ�΢�ź�
	private long CreateTime;//����ʱ��
	private String MsgType;//��Ϣ����
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}

