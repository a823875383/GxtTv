package com.jsqix.gxt.tv.recieve;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jsqix.gxt.tv.utils.ACache;
import com.jsqix.gxt.tv.utils.ProcessUtils;
import com.jsqix.gxt.tv.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[JPushReceiver] onReceive - " + intent.getAction()
				+ ", extras: " + printBundle(bundle));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
			// send the Registration Id to your server...

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG,
					"[JPushReceiver] 接收到推送下来的自定义消息: "
							+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			processCustomMessage(context, bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知");
			int notifactionId = bundle
					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			Log.d(TAG, "[JPushReceiver] 用户点击打开了通知");

			// 打开自定义的Activity

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
			Log.d(TAG,
					"[JPushReceiver] 用户收到到RICH PUSH CALLBACK: "
							+ bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction())) {
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[JPushReceiver]" + intent.getAction()
					+ " connected state change to " + connected);
		} else {
			Log.d(TAG,
					"[JPushReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	// send msg to MainActivity
	private void processCustomMessage(Context mContext, Bundle bundle) {
		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		System.out.println("Jpush 自定义消息:" + message);
		JSONObject jsonObject;
		try {
			boolean isman = StringUtils.toBool(ACache.get(mContext, "Screen")
					.getAsString("main"));
			int count = StringUtils.toInt(ACache.get(mContext, "Screen")
					.getAsString("count"));
			jsonObject = new JSONObject(message);
			String type = jsonObject.getString("type");
			if (mContext != null)
				if (ProcessUtils.isTopActivity(mContext)) {
					if ("alert".equals(type)) {
						// JSONObject object = jsonObject.getJSONObject("obj");
						// LotteryFragment.istances.getCallback().dispPrize(mContext,
						// object.getString("imgUrl"));
					} else if ("TC_001".equals(type)) {
						// CampaignFragment.istances.getCallback().dispQRcode(
						// mContext,
						// jsonObject.getJSONObject("obj").toString());
						// MainActivity.istances.dispQRcode(mContext, "");
					} else if ("TC_002".equals(type)) {
						// MainActivity.istances.getCallback().changeFragment(
						// new LotteryUserFragment(jsonObject.getJSONObject(
						// "obj").toString()));
						// TicketActivity.istances
						// .changeFragment(new LotteryUserFragment(
						// jsonObject.getJSONObject("obj")
						// .toString()));
						// MainActivity.istances.setQRcode();
					} else if ("TC_003".equals(type)) {
						// MainActivity.istances.getCallback().changeFragment(
						// new LotteryFragment());
						// TicketActivity.istances
						// .changeFragment(new LotteryFragment());
					} else if ("TC_004".equals(type)) {
						// if (!isman) {
						// LotteryFragment.istances.getCallback().startPrize(
						// jsonObject.getJSONObject("obj").toString());
						// }
					} else if ("TC_005".equals(type)) {
						// JSONObject obj = jsonObject.getJSONObject("obj");
						// if (!isman) {
						// LotteryFragment.istances.getCallback().dispPrize(
						// mContext, obj.toString());
						// }
						// // 封装中奖信息
						// JSONObject lotteryData = obj.getJSONObject("obj");
						// Lottery lottery = new Gson().fromJson(
						// lotteryData.toString(), Lottery.class);
						// StringBuffer buffer = new StringBuffer();
						// buffer.append("恭喜用户");
						// buffer.append(lottery.getACCT().substring(0, 4));
						// buffer.append("****");
						// buffer.append(lottery.getACCT().substring(7,
						// lottery.getACCT().length()));
						// buffer.append("抽中了");
						// if (lottery.getPRIZE_TYPE().equals("63")) {
						// buffer.append(lottery.getNAME());
						// buffer.append(lottery.getTPNAME());
						// } else {
						// buffer.append(lottery.getTPNAME());
						// buffer.append(lottery.getNAME());
						// }
						// MainActivity.istances.getLotteryInforCallback().setWinText(
						// buffer.toString());
						// TicketActivity.istances.setWinText(buffer.toString());
					} else if ("TC_006".equals(type)) {
						// if (!isman) {
						// LotteryFragment.istances.getCallback()
						// .confirmPrize();
						// }
					} else if ("TC_007".equals(type)) {
						// MainActivity.istances.getCallback().changeFragment(
						// new CampaignFragment());
						// MainActivity.istances.dispQRcode(mContext, "");
						// TicketActivity.istances
						// .changeFragment(new ADSpreadFragment());
					} else if ("TC_008".equals(type)) {
//						AuthenticateActivity.istances.getBindScreen()
//								.onComplete(
//										jsonObject.getJSONObject("obj")
//												.toString());
					} else if ("TC_009".equals(type)) {
						// Fragment fragment1 = null, fragment2 = null;
						// fragment1 = new TicketSpreadFragment();
						// fragment2 = new ProductActivity(jsonObject
						// .getJSONObject("obj").getJSONObject("obj"));
						// if (count != 0) {
						// fragment1 = null;
						// }
						// if (TicketActivity.istances != null)
						// TicketActivity.istances.changeFragment(fragment1,
						// fragment2);
					} else if ("TC_010".equals(type)) {
						// Fragment fragment = null;
						// JSONObject object = jsonObject.getJSONObject("obj")
						// .getJSONObject("obj");
						// int order_type = StringUtils.toInt(object
						// .getString("order_type"));
						// if (order_type == 0) {
						// fragment = new TrainPayFragment(object);
						// } else if (order_type == 1) {
						// fragment = new BusPayFragment(object);
						// } else if (order_type == 2) {
						// fragment = new AirPayFragment(object);
						// } else if (order_type == 3) {
						// fragment = new CostPayFragment(object);
						// }
						//
						// if (TicketActivity.istances != null)
						// TicketActivity.istances.changeFragment(null,
						// fragment);
					} else if ("TC_011".equals(type)) {
						// Fragment fragment1 = null, fragment2 = null;
						// fragment1 = new ADSpreadFragment();
						// if (count != 0) {
						// fragment1 = null;
						// }
						// double random = Math.random();
						// if (random < 0.333333) {
						// fragment2 = new SceneryListFragment();
						// } else if (random > 0.666666) {
						// fragment2 = new HotelListFragment();
						// } else {
						// fragment2 = new IntroductionActivity();
						// }
						//
						// if (TicketActivity.istances != null) {
						// TicketActivity.istances.changeFragment(fragment1,
						// fragment2);
						// }
					} else if ("TC_012".equals(type)) {
						// Log.d("push", "" + jsonObject.getJSONObject("obj"));
						// if (TicketActivity.istances != null) {
						// Log.d("push", "" + jsonObject.getJSONObject("obj"));
						// SceneryListFragment fragment = new
						// SceneryListFragment(
						// jsonObject.getJSONObject("obj"));
						// Fragment fragment1 = new ADSpreadFragment();
						// TicketActivity.istances.changeFragment(fragment1,
						// fragment);
						// }
					} else if ("TC_013".equals(type)) {
						// if (TicketActivity.istances != null) {
						// HotelListFragment fragment = new HotelListFragment(
						// jsonObject.getJSONObject("obj"));
						// Fragment fragment1 = new ADSpreadFragment();
						// TicketActivity.istances.changeFragment(fragment1,
						// fragment);
						// }
					} else if ("TC_014".equals(type)) {
						// if (TicketActivity.istances != null) {
						// SceneryOrderInfoFragment fragment = new
						// SceneryOrderInfoFragment(
						// jsonObject.getJSONObject("obj"));
						// Fragment fragment1 = new TicketSpreadFragment();
						// TicketActivity.istances.changeFragment(fragment1,
						// fragment);
						// }
					} else if ("TC_015".equals(type)) {
						// if (TicketActivity.istances != null) {
						// HotelOrderInfoFragment fragment = new
						// HotelOrderInfoFragment(
						// jsonObject.getJSONObject("obj"));
						// Fragment fragment1 = new TicketSpreadFragment();
						// TicketActivity.istances.changeFragment(fragment1,
						// fragment);
						// }
					}
					/*
					 * else if ("TC_016".equals(type)) { if
					 * (TicketActivity.istances != null) {
					 * TicketActivity.istances.change2FSad(); } }
					 */
				}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
