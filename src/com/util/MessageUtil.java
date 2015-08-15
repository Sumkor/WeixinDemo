package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.po.Image;
import com.po.ImageMessage;
import com.po.Music;
import com.po.MusicMessage;
import com.po.News;
import com.po.NewsMessage;
import com.po.TextMessage;
import com.thoughtworks.xstream.XStream;
/**
 * 消息的格式转换
 * @author Sumkor
 *
 */
public class MessageUtil {
	
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE= "scancode_push";
	
	/**
	 * xml数据解析为Map集合
	 * @param request
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws DocumentException, IOException{
		Map<String,String> map=new HashMap<String, String>();
		SAXReader reader=new SAXReader();
		InputStream ins=request.getInputStream();//从request中获取输入流
		Document doc=reader.read(ins);//doc=reader.read(new File(fileURL));
		Element root=doc.getRootElement();
		List<Element> list =root.elements();
		for(Element e:list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	/**
	 * 文本消息对象转换成XML
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass());//将xml根元素改为"xml"
		return xstream.toXML(textMessage);
	}
	/**
	 * 组装文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text=new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}
	
	/**
	 * 图文消息转为xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());//消息体位于<item></item>之间
		return xstream.toXML(newsMessage);
	}
	/**
	 * 组装图文消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message = null;
		List<News> newsList = new ArrayList<News>();//用于接收消息体的集合
		NewsMessage newsMessage = new NewsMessage();
		
		News news = new News();
		news.setTitle("About GitHub");
		news.setDescription("Build software better, together.");
		news.setPicUrl("http://mmbiz.qpic.cn/mmbiz/6CMAND4ZqARicEWIRGBZHqCeicZwL6c7TtUJqaVv5DKn5icM8W5nAPNx7KbdgKLFrqYJohJlo322PHVVian8A9EyBw/0");
		news.setUrl("https://github.com/");
		
		newsList.add(news);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	/**
	 * 图片消息转为xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		Image image = new Image();
		image.setMediaId("Q7aGw5fsZb-E7FdRsdZ_d5miQO6OIqAQKjfeUe-TM70P2xj4nXSvYwUYXqveZHte");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	
	/**
	 * 音乐消息转为xml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	/**
	 * 组装音乐消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("Q7aGw5fsZb-E7FdRsdZ_d5miQO6OIqAQKjfeUe-TM70P2xj4nXSvYwUYXqveZHte");
		music.setTitle("3nd-untroubled terror");
		music.setDescription("post rock");
		music.setMusicUrl("http://sumtest.tunnel.mobi/WeixinDemo/music/3nd-untroubled terror.mp3");
		music.setHQMusicUrl("http://sumtest.tunnel.mobi/WeixinDemo/music/3nd-untroubled terror.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}
	
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb=new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		sb.append("1、文字消息\n");
		sb.append("2、图文消息\n");
		sb.append("3、图片消息\n");
		sb.append("4、音乐消息\n\n");
		sb.append("回复？调出此菜单");
		return sb.toString();
	}
	public static String firstMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("这是一条文字消息。");
		return sb.toString();
	}
}
