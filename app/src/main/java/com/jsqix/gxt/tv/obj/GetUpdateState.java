package com.jsqix.gxt.tv.obj;

import org.json.JSONObject;

public class GetUpdateState {

	// "ID":532,"APP_TYPE":1002,"DOWN_URL":"e1fffe0c8eb04e2faabfed2609f7cb88.apk",
	// "APP_VERSION":"V1.0.0","FORCED":1,"UP_DT":1426675870000,"INFO":"点窝APP"}

	private String appVersion;
	private String app_url;
	private String is_forced;
	private String appInfor;

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getApp_url() {
		return app_url;
	}

	public void setApp_url(String app_url) {
		this.app_url = app_url;
	}

	public String getIs_forced() {
		return is_forced;
	}

	public void setIs_forced(String is_forced) {
		this.is_forced = is_forced;
	}

	public String getAppInfor() {
		return appInfor;
	}

	public void setAppInfor(String appInfor) {
		this.appInfor = appInfor;
	}

	public static GetUpdateState parse(String str) {
		GetUpdateState updateState =null;
		try {
			JSONObject jsonObject = new JSONObject(str);
			if ("000".equals(jsonObject.getString("code"))) {
				JSONObject obj = jsonObject.getJSONObject("obj");
				String version = obj.getString("APP_VERSION");
				String is_force = obj.getString("FORCED").equals("1")?"true":"false";
				String infor = obj.getString("INFO");
				String downUrl = jsonObject.getString("url")
						+ obj.getString("DOWN_URL");
				updateState=new GetUpdateState();
				updateState.setAppVersion(version);
				updateState.setIs_forced(is_force);
				updateState.setAppInfor(infor);
				updateState.setApp_url(downUrl);
			}
		} catch (Exception e) {
		}
		return updateState;
	}

	@Override
	public String toString() {
		return "GetUpdateState [appVersion=" + appVersion + ", app_url="
				+ app_url + ", is_forced=" + is_forced + ", appInfor="
				+ appInfor + "]";
	}
	
}