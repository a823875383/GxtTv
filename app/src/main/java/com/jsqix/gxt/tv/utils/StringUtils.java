package com.jsqix.gxt.tv.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {

	static DecimalFormat df = new DecimalFormat(".00");

	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	public static boolean isNum(Object obj) {
		if (obj == null)
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(obj.toString()).matches();
	}

	public static String toFormat(Object obj) {
		String format = df.format(toFloat(obj));
		if (format.length() == 3) {
			format = "0" + format;
		}
		return format;
	}

	public static double toDouble(Object obj) {
		if (obj == null)
			return 0;
		return toDouble(obj.toString(), 0);
	}

	private static double toDouble(String str, double defValue) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static float toFloat(Object obj) {
		if (obj == null)
			return 0;
		return toFloat(obj.toString(), 0);
	}

	private static float toFloat(String str, float defValue) {
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isPhone(String paramString) {
						/*          移动                                                   */    /*   电信            */   /*           联通                         */   /*  电信           */ /* 数据号段     */   /* 17号段     */
		String regExp = "^1(3[4-9]|5[012789]|8[23478])\\d{8}$|^18[019]\\d{8}$|^1(3[0-2]|5[56]|8[56])\\d{8}$|^1[35]3\\d{8}$|^14[57]\\d{8}$|^17[0678]\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(paramString);
		return m.matches();
	}

	public static boolean isEmail(String paramString) {
		String regExp = "^([\\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(paramString);
		return m.matches();
	}
	
//	/**
//	 * 得到 全拼
//	 * 
//	 * @param src
//	 * @return
//	 */
//	public static String getPingYin(String src) {
//		char[] t1 = null;
//		t1 = src.toCharArray();
//		String[] t2 = new String[t1.length];
//		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
//		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
//		String t4 = "";
//		int t0 = t1.length;
//		try {
//			for (int i = 0; i < t0; i++) {
//				// 判断是否为汉字字符
//				if (java.lang.Character.toString(t1[i]).matches(
//						"[\\u4E00-\\u9FA5]+")) {
//					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
//					t4 += t2[0];
//				} else {
//					t4 += java.lang.Character.toString(t1[i]);
//				}
//			}
//			return t4;
//		} catch (BadHanyuPinyinOutputFormatCombination e1) {
//			e1.printStackTrace();
//		}
//		return t4;
//	}

//	/**
//	 * 得到首字母
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public static String getHeadChar(String str) {
//
//		String convert = "";
//		char word = str.charAt(0);
//		String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
//		if (pinyinArray != null) {
//			convert += pinyinArray[0].charAt(0);
//		} else {
//			convert += word;
//		}
//		return convert.toUpperCase();
//	}
	
//    /**
//     * 得到中文首字母缩写
//     * 
//     * @param str
//     * @return
//     */
//    public static String getPinYinHeadChar(String str) {
//
//        String convert = "";
//        for (int j = 0; j < str.length(); j++) {
//            char word = str.charAt(j);
//            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
//            if (pinyinArray != null) {
//                convert += pinyinArray[0].charAt(0);
//            } else {
//                convert += word;
//            }
//        }
//        return convert.toUpperCase();
//    }
}
