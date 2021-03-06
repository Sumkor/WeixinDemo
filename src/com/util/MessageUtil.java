package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
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
import com.thoughtworks.xstream.core.util.QuickWriter;  
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;  
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;  
import com.thoughtworks.xstream.io.xml.XppDriver; 
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
		news.setPicUrl("http://mmbiz.qpic.cn/mmbiz/6CMAND4ZqAQkoHJG2cjb07jn2E1IoG0FOqica52Mia3kuYzPqAKFtGAakz6Olp2WHB3fRzYJUlnADcGevcIEOVww/0");
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
		image.setMediaId("lgIxipCznfamNN8iuDp60knuAl3mS2o1D_4e5Geg_AOILzKAH8tPTWJ5SIUZ-f3v");
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
		music.setThumbMediaId("lgIxipCznfamNN8iuDp60knuAl3mS2o1D_4e5Geg_AOILzKAH8tPTWJ5SIUZ-f3v");
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
	
	
//	/**
//	 * 扩展xstream，使其支持CDATA块(无用)
//	 */
//    private static XStream xstream = new XStream(new XppDriver() {  
//        public HierarchicalStreamWriter createWriter(Writer out) {  
//            return new PrettyPrintWriter(out) {  
//                // 对所有xml节点的转换都增加CDATA标记  
//                boolean cdata = true;  
//  
//                @SuppressWarnings("unchecked")  
//                public void startNode(String name, Class clazz) {  
//                    super.startNode(name, clazz);  
//                }  
//  
//                protected void writeText(QuickWriter writer, String text) {  
//                    if (cdata) {  
//                        writer.write("<![CDATA[");  
//                        writer.write(text);  
//                        writer.write("]]>");  
//                    } else {  
//                        writer.write(text);  
//                    }  
//                }  
//            };  
//        }  
//    }); 
	
	
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String mainMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		sb.append("1、文字消息\n");
		sb.append("2、图文消息\n");
		sb.append("3、图片消息\n");
		sb.append("4、音乐消息\n");
		sb.append("5、百度翻译\n");
		sb.append("6、天气预报\n\n");
		sb.append("回复？调出此菜单");
		return sb.toString();
	}
	public static String textMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("这是一条文字消息。");
		return sb.toString();
	}
	public static String transMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("百度翻译使用指南\n\n");
		sb.append("使用示例：\n");
		sb.append("翻译早上好\n");
		sb.append("翻译Good Morning\n\n");
		sb.append("回复？显示主菜单。");
		return sb.toString();
	}
	public static String weatherMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("天气预报使用指南\n\n");
		sb.append("使用示例：\n");
		sb.append("天气广州\n");
		sb.append("回复？显示主菜单。");
		return sb.toString();
	}
}
