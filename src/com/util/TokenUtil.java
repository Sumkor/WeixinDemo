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
 * 定期读取并保存AccessToken
 */
public class TokenUtil {
	public static String getExistsToken(){
		//读取XML中已有的token及其创建时间
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
				
				long createTime = Long.parseLong(createTimeMillis);//将String转换成long
				long currentTime = System.currentTimeMillis();
				//如果已经超过7200秒，则获取新的token，并修改xml
				if((currentTime-7200000L) > createTime ){
					try {
						AccessToken accessToken = WeixinUtil.getAccessToken();
						token = accessToken.getToken();
						root.element("Token").setText(accessToken.getToken());//修改xml中Token节点的值
						root.element("CreateTimeMillis").setText(String.valueOf(currentTime));//修改xml中CreateTimeMillis节点的值
						XMLWriter writer = new XMLWriter(new FileWriter(new File(fileURL)));//将document中的内容写入文件中
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
