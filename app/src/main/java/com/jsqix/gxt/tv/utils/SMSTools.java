package com.jsqix.gxt.tv.utils;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

public class SMSTools extends ContentObserver {

	public static final String SMS_URI_INBOX = "content://sms/inbox";
	private Activity activity = null;
	private String smsContent = "";
	private EditText verifyText = null;

	public SMSTools(Activity activity, Handler handler, EditText verifyText) {
		super(handler);
		this.activity = activity;
		this.verifyText = verifyText;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Cursor cursor = null;// 光标
		// 读取收件箱中指定号码的短信
		cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[] {
				"_id", "address", "body", "read" }, "address=? and read=?",
				new String[] { "1069024867018", "0" }, "date desc");
		if (cursor != null) {// 如果短信为未读模式
			cursor.moveToFirst();
			if (cursor.moveToFirst()) {
				String smsbody = cursor
						.getString(cursor.getColumnIndex("body"));
				System.out.println("smsbody=======================" + smsbody);
				// String regEx = "[^0-9]";
				// Pattern p = Pattern.compile(regEx);
				// Matcher m = p.matcher(smsbody.toString());
				// smsContent = m.replaceAll("").trim().toString();
				// if(smsContent.length()>6)
				// smsContent=smsContent.substring(0, 6);
				String[] strs = smsbody.split("[^0-9]{1,}");//提取连续数序
				for (String str : strs) {
					if (str.length() >= 4) {
						smsContent = str;
						break;
					}
				}
				verifyText.setText(smsContent);
			}
		}
	}

}
