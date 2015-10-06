package com.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.ParseException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.po.AccessToken;

/**
 * @author Sumkor
 * ���ڶ�ȡ������AccessToken
 */
public class TokenUtil {
	public static String getExistsToken(){
		//��ȡXML�����е�token���䴴��ʱ��
		String fileURL = "src/com/XMLToken.xml";
		File file = new File(fileURL);
		String token = null;
		if(file.exists()){
			SAXReader reader=new SAXReader();
			try{
				Document document=reader.read(file);
				Element root = document.getRootElement();
				token = root.element("Token").getText();
				String createTimeMillis = root.element("CreateTimeMillis").getText();
				
				long createTime = Long.parseLong(createTimeMillis);//��Stringת����long
				long currentTime = System.currentTimeMillis();
				//����Ѿ�����7200�룬���ȡ�µ�token�����޸�xml
				if((currentTime-7200000L) > createTime ){
					try {
						AccessToken accessToken = WeixinUtil.getAccessToken();
						token = accessToken.getToken();
						root.element("Token").setText(accessToken.getToken());//�޸�xml��Token�ڵ��ֵ
						root.element("CreateTimeMillis").setText(String.valueOf(currentTime));//�޸�xml��CreateTimeMillis�ڵ��ֵ
						XMLWriter writer = new XMLWriter(new FileWriter(new File(fileURL)));//��document�е�����д���ļ���
						writer.write(document);  
		                writer.close();
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}catch(DocumentException e){
				e.printStackTrace();
			}
		}
		return token;
	}
}
