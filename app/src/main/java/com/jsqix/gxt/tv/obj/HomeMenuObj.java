package com.jsqix.gxt.tv.obj;

public class HomeMenuObj {
	private String name;
	private String tag;
	private int icon;
	private int color;
	private Class<?> context;
	public HomeMenuObj(Class<?> context,String name,String tag,int icon,int color) {
		this.context = context;
		this.name = name;
		this.tag = tag;
		this.icon = icon;
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public Class<?> getContext() {
		return context;
	}
	public void setContext(Class<?> context) {
		this.context = context;
	}
	
}
