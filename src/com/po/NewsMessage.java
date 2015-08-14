package com.po;
import java.util.List;
/**
 * 图文消息外层
 * @author Sumkor
 *
 */
public class NewsMessage extends BaseMessage{
	private int ArticleCount;//图文消息数量
	private List<News> Articles;//图文消息体
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<News> getArticles() {
		return Articles;
	}
	public void setArticles(List<News> articles) {
		Articles = articles;
	}
}
