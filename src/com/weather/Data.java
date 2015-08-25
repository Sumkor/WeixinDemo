package com.weather;

public class Data {
	private String wendu;
	private String ganmao;//感冒指数
	private Forecast[] forecast;
	private String aqi;//空气质量指数
	private String city;
	private Yesterday yesterday;//昨日天气
	public String getWendu() {
		return wendu;
	}
	public void setWendu(String wendu) {
		this.wendu = wendu;
	}
	public String getGanmao() {
		return ganmao;
	}
	public void setGanmao(String ganmao) {
		this.ganmao = ganmao;
	}
	public Forecast[] getForecast() {
		return forecast;
	}
	public void setForecast(Forecast[] forecast) {
		this.forecast = forecast;
	}
	public String getAqi() {
		return aqi;
	}
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Yesterday getYesterday() {
		return yesterday;
	}
	public void setYesterday(Yesterday yesterday) {
		this.yesterday = yesterday;
	}
	
}
