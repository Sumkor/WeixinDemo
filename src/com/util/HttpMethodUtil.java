package com.util;

import java.io.IOException;

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
/**
 * ��װ���ýӿڵ�GET/POST����
 * @author Sumkor
 * https://github.com/Sumkor
 */
public class HttpMethodUtil {
	/**
	 * ��װGET���󷽷��������յ�����ת��json��ʽ
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();//DefaultHttpClient��ʵ��HttpClient�ӿڵ�����
		DecompressingHttpClient httpClient = new DecompressingHttpClient(client);//���ⷵ��compressed��ʽ��Entity��������
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		
		HttpResponse httpResponse = httpClient.execute(httpGet);	
		HttpEntity entity = httpResponse.getEntity();
		
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
//			String result = new String(EntityUtils.toString(entity).getBytes("ISO_8859_1"),"UTF-8");
			System.out.println(result);
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}

	
	/**
	 * POST����
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));//�����ݽ����Ĳ����ύ
		HttpResponse httpResponse = client.execute(httpost);
		HttpEntity entity = httpResponse.getEntity();
		
		String result = EntityUtils.toString(entity,"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
}
