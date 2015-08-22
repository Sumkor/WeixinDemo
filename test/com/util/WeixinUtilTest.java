package com.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import net.sf.json.JSONObject;

import org.apache.http.ParseException;
import org.junit.Test;

import com.menu.Menu;
import com.po.AccessToken;

public class WeixinUtilTest {

	@Test
	public void testGetAccessToken() {
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			System.out.println("Ʊ��"+token.getToken());
			System.out.println("��Чʱ��"+token.getExpiresIn());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpload(){	
		String path="D:/TOOLS/MyEclipse/Workspaces/WeixinDemo/WebRoot/music/3nd-untroubled terror.jpg";
		String path_perm="D:/TOOLS/MyEclipse/Workspaces/WeixinDemo/WebRoot/image/github.jpg";
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			String imageId=WeixinUtil.upload_temp(path, token.getToken(), "thumb");
			String URL=WeixinUtil.upload_perm(path_perm, token.getToken());
			System.out.println("��ʱͼƬid "+imageId);
			System.out.println("����ͼƬurl "+URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateMenu(){
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			Menu menu=WeixinUtil.initMenu();
			String menuStr=JSONObject.fromObject(menu).toString();
			int result=WeixinUtil.createMenu(token.getToken(), menuStr);
			if(result==0){
				System.out.println("�����˵��ɹ�");
			}else{
				System.out.println("������"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryMenu(){
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			JSONObject jsonObject = WeixinUtil.queryMenu(token.getToken());
			System.out.println(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
