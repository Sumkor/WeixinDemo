package com.po;
import java.util.List;
/**
 * ͼ����Ϣ���
 * @author Sumkor
 *
 */
public class NewsMessage extends BaseMessage{
	private int ArticleCount;//ͼ����Ϣ����
	private List<News> Articles;//ͼ����Ϣ��
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
