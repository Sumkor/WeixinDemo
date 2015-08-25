package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.menu.Button;
import com.menu.ClickButton;
import com.menu.Menu;
import com.menu.ViewButton;
import com.po.AccessToken;
import com.weather.Data;
import com.weather.Forecast;
import com.weather.WeatherResult;

//import com.Data;
//import com.Parts;
//import com.trans.Symbols;
//import com.trans.TransResult;

/**
 * ��ȡ΢�ż�����ƽ̨�ӿڵ���ƾ֤
 * @author Sumkor
 *
 */
public class WeixinUtil {
	private static final String APPID="wx02611a7655e39458";
	private static final String APPSECRET="6aa9fa520e5ad1414b4bacb63af95a2c";
	
	private static final String BAIDU_APIKEY="e5qeHZf77p4bGogKI6IZOdRl";
	
	//�ӿڵ�������
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";	
	private static final String UPLOAD_URL_TEMP = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	private static final String UPLOAD_URL_PREM = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";	
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	

	/**
	 * ��ȡaccessToken
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException{
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = HttpMethodUtil.doGetStr(url);
//		url = url.replaceAll(" ", "%20");
		if(jsonObject!=null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	/**
	 * �ϴ���ʱ�ز�
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload_temp(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("�ļ�������");
		}
		String url = UPLOAD_URL_TEMP.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);		
		
		String result = HttpRequestUtil.httpRequest(file, url);

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){//����������Ĳ���ͼƬ����
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	
	/**
	 * �ϴ������ز�
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload_perm(String filePath, String accessToken) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("�ļ�������");
		}
		String url = UPLOAD_URL_PREM.replace("ACCESS_TOKEN", accessToken);
		
		String result = HttpRequestUtil.httpRequest(file, url);

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "url";
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	
	/**
	 * ��װ�˵�
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("click�ظ�");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton button21 = new ViewButton();
		button21.setName("view����");
		button21.setType("view");
		button21.setUrl("https://github.com/");
		
		ClickButton button31 = new ClickButton();
		button31.setName("ɨ���¼�");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("����λ��");
		button32.setType("location_select");
		button32.setKey("32");
		
		Button button = new Button();
		button.setName("�˵�");
		button.setSub_button(new Button[]{button31,button32});
		
		menu.setButton(new Button[]{button11,button21,button});
		return menu;
	}
	/**
	 * �Զ���˵�-�����ӿ�
	 * @param accessToken
	 * @param menu
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int createMenu(String accessToken,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpMethodUtil.doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	public static JSONObject queryMenu(String token) throws ParseException, IOException{
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = HttpMethodUtil.doGetStr(url);
		return jsonObject;
	}
	
	public static int deleteMenu(String token) throws ParseException, IOException{
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = HttpMethodUtil.doGetStr(url);
		int result = 0;
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	/**
	 * �ٶȷ���API
	 * @param source
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String translate(String source) throws ParseException, IOException{
		String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=BAIDU_APIKEY&q=KEYWORD&from=auto&to=auto";
		url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8")).replace("BAIDU_APIKEY", BAIDU_APIKEY);
		JSONObject jsonObject = HttpMethodUtil.doGetStr(url);
		StringBuffer sb = new StringBuffer();
		List<Map> list = (List<Map>) jsonObject.get("trans_result");
		for(Map map : list){
			sb.append(map.get("dst"));//dstΪtrans_result�����е�����
		}
		return sb.toString();
	}
	
	/**
	 * �л�����������
	 * @param source
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String weather(String source) throws ParseException, IOException{
		String url = "http://wthrcdn.etouch.cn/weather_mini?city=CITYNAME";
		url = url.replace("CITYNAME",URLEncoder.encode(source, "UTF-8"));
		JSONObject jsonObject = HttpMethodUtil.doGetStr(url);
		StringBuffer sb = new StringBuffer();
		
		int status = jsonObject.getInt("status");
		String desc = jsonObject.getString("desc");		
		if(status==1000 && "OK".equals(desc)){ 
			WeatherResult weatherResult= (WeatherResult) jsonObject.toBean(jsonObject, WeatherResult.class);
			Data data = weatherResult.getData();
			String wendu = data.getWendu();
			String ganmao = data.getGanmao();
			String city = data.getCity();
			String aqi = data.getAqi();
			Forecast[] forecast = data.getForecast();
			sb.append(city).append(" �����¶�:").append(wendu).append("\r\n")
				.append(" ��������ָ��:").append(aqi).append("\r\n")
				.append(ganmao).append("\r\n");
			for(Forecast forecasts:forecast){
				sb.append(forecasts.getDate()+":"+forecasts.getHigh()+" "+forecasts.getLow()+" ")
					.append(forecasts.getFengxiang()+" "+forecasts.getFengli()).append("\r\n");
			}
			
		}
		return sb.toString();
	}
}
