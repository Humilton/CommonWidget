package com.github.Humilton.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
	public static final String ICON_URL = "http://vbox-bucket-001.oss-cn-shanghai.aliyuncs.com/%1$s/BYGHeadUrl";
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}
	public static String getIconUrl(String userId) {
		return String.format(ICON_URL, userId);
	}
	public static boolean checkNickName(String nickName) {
		// char nickName.toCharArray();
		boolean isCheck = true;
		for (char c : nickName.toCharArray()) {
			// System.out.println((int)c);
			int code = (int) c;
			if (code <= 122) {
			} else if ((c >= 0x4e00) && (c <= 0x9fbb)) {
			} else {
				isCheck = false;
				break;
			}
		}
		return isCheck;
	}

	public static String join(String[] pre, String sep){
		String reStr = pre[0];
		for (int i = 1; i < pre.length; i ++){
			reStr = reStr + sep + pre[i];
		}

		return reStr;
	}
	public static String join(String[] pre){
		String reStr = "";
		for (int i = 0; i < pre.length; i ++){
			reStr = reStr + pre[i];
		}

		return reStr;
	}
	public static String getStringByNULL(Object str) {
		if (str == null || str.equals("null")) {
			return "";
		} else {
			return str.toString();
		}
	}
	// 去除特殊字符，类似'
	public static String stringFilter(String str){
		if(str==null){
			return "";
		} else{
			return str.replaceAll("'","");
		}
		
	}

	/**
	 * 对象转化成string
	 * 
	 * @param str
	 * @return
	 */
	public static int parseInt(Object str) {
		try {
			if (str == null || "".equals(str)) {
				return 0;
			} else {
				return Integer.parseInt(str.toString());
			}
		} catch (Exception e) {
			return 0;
		}
	}
	public static long parseLong(Object str) {
		try {
			if (str == null || "".equals(str)) {
				return 0;
			} else {
				return Long.parseLong(str.toString());
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static float parseFloat(Object str) {
		try {
			if (str == null || "".equals(str)) {
				return 0;
			} else {
				return Float.parseFloat(str.toString());
			}
		} catch (Exception e) {
			return 0;
		}
	}
	public static double parseDouble(Object str) {
		try {
			if (str == null || "".equals(str)) {
				return 0;
			} else {
				return Double.parseDouble(str.toString());
			}
		} catch (Exception e) {
			return 0;
		}
	}
	public static int parseInt(Object str, int defaultInt) {
		try {
			if (str == null || "".equals(str)) {
				return defaultInt;
			} else {
				return Integer.parseInt(str.toString());
			}
		} catch (Exception e) {
			return defaultInt;
		}
	}
	@SuppressWarnings("unused")
	public static boolean isNum3(String str) {
		try {
			int n = Integer.parseInt(str);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}

	/**
	 * 验证字符串的长度 是否超过num
	 * 
	 * @param strName
	 * @param num
	 * @return
	 */
	public static boolean checkStringLength(String strName, int num) {
		try {
			int count = 0;
			char[] ch = strName.toCharArray();

			for (int i = 0; i < ch.length; i++) {
				char c = ch[i];
				if (isChinese(c)) {
					count += 2;
				} else {
					count += 1;
				}
			}

			return count <= num;
		} catch (Exception ex) {

			ex.printStackTrace();
			return false;
		}
	}

	public static boolean checkNum(String str) {
		boolean bl = true; // 存放是否全为数字
		char[] c = str.toCharArray(); // 把输入的字符串转成字符数组
		for (int i = 0; i < c.length; i++) {
			if (!Character.isDigit(c[i])) { // 判断是否为数字
				bl = false;
				break;
			}
		}
		return bl;
	}

	public static String getExceptionDetail(Exception e) {

		StringBuffer msg = new StringBuffer("null");

		if (e != null) {
			msg = new StringBuffer("");

			String message = e.toString();

			int length = e.getStackTrace().length;

			if (length > 0) {

				msg.append(message + "\n");

				for (int i = 0; i < length; i++) {

					msg.append("\t" + e.getStackTrace()[i] + "\n");

				}
			} else {

				msg.append(message);
			}

		}
		return msg.toString();

	}

	public static String getStringByInteger(Object str) {

		if (str == null) {
			return null;
		} else {
			return str.toString();
		}
	}

	public static String trim(Object str) {

		if (str == null) {
			return null;
		} else {
			return str.toString().trim();
		}
	}

	/**
	 * 判断字符串是不是为空或空�?
	 * 
	 * @param str
	 * @return 为空或空串，则返回false
	 */
	public static boolean stringIf(String str) {
		boolean flag = false;
		if (!(str == null || "".equals(str.trim()))) {
			flag = true;
		}
		return flag;
	}

	/**
	 * str中以,分割，分割后是否含有targetStr，忽略大小写，如 "abc,bcd"是否含有 "bc"
	 * 
	 * @param str
	 * @param targetStr
	 * @return
	 */
	public static boolean containsForDH(String str, String targetStr) {
		boolean flag = false;
		String str02[] = str.split(",");
		for (int i = 0; i < str02.length; i++) {
			if (targetStr.equalsIgnoreCase(str02[i])) {
				flag = true;
			}
		}
		return flag;

	}
	/**
	 * 验证是手机号码
	 * @param str
	 * @return
	 */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
    
    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
    
	/**
	 * 验证是中文
	 * @param name
	 * @return
	 */
	public static boolean isChineseName4(String name) {
		Pattern pattern = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,5}$");
		Matcher matcher = pattern.matcher(name);
		boolean b = false;
		if(matcher.find()){
			return true;
		}else{
			b = isChineseName5(name);
		}
		return b;
	}
	
	public static boolean isChineseName5(String name) {
		Pattern pattern = Pattern.compile("^([\u4E00-\u9FA5]){2,5}((\\.|:)([\u4E00-\u9FA5]){2,5})$");

		//Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*");
		Matcher matcher = pattern.matcher(name);
		return matcher.find();
	}

	/**
	 * 校验身份证
	 *
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	//[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*
	public static void main(String[] args) {
		System.out.println(isChineseName4("夏拉排提.得到是试试"));
	}

	/**
	 * 模糊化手机号码
	 * 将手机号的第四位到第八位替换为*，如：188****8888
	 * @param phoneNum
	 * @return
	 */
	public static String fuzzifyPhoneNum(String phoneNum){
		if(isMobile(phoneNum)){
			phoneNum = phoneNum.substring(0,3)+"****"+phoneNum.substring(7,phoneNum.length());
		}

		return phoneNum;
	}
}
