package com.github.Humilton.util;

import java.security.MessageDigest;

/**
* @ClassName: Md5Util
* @Description: TODO(MD5加密工具类)
* @author zhangjie
* @date 2015年7月20日 下午5:38:50
*/
public final class Md5Util {
	/**
	 * 十六进制表
	 */
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' }; 
	/**
	* @ClassName: Encodestrategy
	* @Description: TODO(编码策略)
	* @author zhangjie
	* @date 2015年7月20日 下午5:51:36
	*/
	public enum EncodeStrategy {
		/**
		 * 十六进制编码
		 */
		ENCODE_HEX, 
		/**
		 * BASE64编码
		 */
		ENCODE_BASE64
	}
	/**
	* @ClassName: CaseStrategy
	* @Description: TODO(大小写策略)
	* @author zhangjie
	* @date 2015年7月20日 下午5:52:19
	*/
	public enum CaseStrategy {
		AUTO_CASE,
		/**
		 * 大写策略
		 */
		LOWER_CASE, 
		/**
		 * 小写策略
		 */
		UPPER_CASE
	}
	/**
	* @Title: toHexString
	* @Description: TODO(将二进制转换为十六进制字符串)
	* @param bytes
	* @return
	* @author zhangjie
	* @date 2015年7月20日 下午5:42:33
	*/
	private static String toHexString(byte[] bytes) {
	    StringBuilder sb = new StringBuilder(bytes.length * 2);
	    for (int i = 0; i < bytes.length; i++) {  
	        sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >>> 4]);  
	        sb.append(HEX_DIGITS[bytes[i] & 0x0f]);  
	    }  
	    return sb.toString();  
	} 
	/**
	* @Title: MD5ForHex
	* @Description: TODO(MD5加密，并使用十六进制进行编码)
	* @param data
	* @return
	* @author zhangjie
	* @date 2015年7月20日 下午5:54:42
	*/
	private static String MD5ForHex(String data){
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(data.getBytes());  
			byte messageDigest[] = digest.digest();  
			return toHexString(messageDigest);
		} catch (Exception e) {
			throw new RuntimeException("md5 for hex error.", e);// 抛出运行时异常
		}  
	}
	/**
	* @Title: MD5ForHex
	* @Description: TODO(MD5加密，并使用十六进制进行编码)
	* @param data
	* @param caseStrategy
	* @return
	* @author zhangjie
	* @date 2015年7月20日 下午5:58:39
	*/
	private static String MD5ForHex(String data, CaseStrategy caseStrategy){
		String result = MD5ForHex(data);
		return caseStrategy(caseStrategy, result);
	}
	/**
	* @Title: MD5ForBase64
	* @Description: TODO(MD5加密，并使用base64进行编码)
	* @param data
	* @return
	* @author zhangjie
	* @date 2015年7月20日 下午5:59:51
	*/
	private static String MD5ForBase64(String data){
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("md5");
			byte[] md5= messageDigest.digest(data.getBytes());
			return Base64Encoder.encode(md5);
		} catch (Exception e) {
			throw new RuntimeException("md5 for base64 error.", e);// 抛出运行时异常
		}
	}

	public static String encrypt32(String encryptStr,CaseStrategy caseStrategy) {
		MessageDigest md5;
		String res;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(encryptStr.getBytes());
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			encryptStr = hexValue.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (caseStrategy == CaseStrategy.UPPER_CASE){
			res = encryptStr.toUpperCase();
		}else {
			res = encryptStr;
		}

		return res;
	}
	/**
	* @Title: MD5ForBase64
	* @Description: TODO(MD5加密，并使用base64进行编码)
	* @param data
	* @param caseStrategy
	* @return
	* @author zhangjie
	* @date 2015年7月20日 下午6:00:43
	*/
	private static String MD5ForBase64(String data, CaseStrategy caseStrategy){
		String result = MD5ForBase64(data);
		return caseStrategy(caseStrategy, result);
	}
	/**
	* @Title: caseStrategy
	* @Description: TODO(大小写策略)
	* @param caseStrategy
	* @param result
	* @return
	* @author zhangjie
	* @date 2015年7月21日 上午11:03:45
	*/
	private static String caseStrategy(CaseStrategy caseStrategy, String result) {
		caseStrategy = caseStrategy == null ? CaseStrategy.AUTO_CASE : caseStrategy;
		if(caseStrategy ==  CaseStrategy.LOWER_CASE){
			return result.toLowerCase();
		}
		if(caseStrategy ==  CaseStrategy.UPPER_CASE){
			return result.toUpperCase();
		}
		return result;
	}
	/**
	* @Title: encrypt
	* @Description: TODO(MD5加密)
	* @param data
	* @param encodeType
	* @param caseStrategy
	* @return
	* @author zhangjie
	* @date 2015年7月20日 下午6:05:16
	*/
	public static String encrypt(String data, EncodeStrategy encodeType, CaseStrategy caseStrategy){
		encodeType = encodeType == null ? EncodeStrategy.ENCODE_BASE64 : encodeType;
		return encodeType == EncodeStrategy.ENCODE_BASE64 ? MD5ForBase64(data, caseStrategy) : MD5ForHex(data, caseStrategy);
	}
	/**
	* @Title: encrypt
	* @Description: TODO(MD5加密)
	* @param data
	* @param encodeType
	* @return
	* @author zhangjie
	* @date 2015年7月20日 下午6:05:33
	*/
	public static String encrypt(String data, EncodeStrategy encodeType){
		return encrypt(data, encodeType, null);
	}
	/**
	* @Title: encrypt
	* @Description: TODO(MD5加密)
	* @param data
	* @param caseStrategy
	* @return
	* @author zhangjie
	* @date 2015年7月20日 下午6:06:57
	*/
	public static String encrypt(String data, CaseStrategy caseStrategy){
		return encrypt(data, null, caseStrategy);
	}
	/**
	* @Title: encrypt
	* @Description: TODO(MD5加密)
	* @param data
	* @return
	* @author zhangjie
	* @date 2015年7月20日 下午6:05:43
	*/
	public static String encrypt(String data){
		return encrypt(data, null, null);
	}
	
	public static void main(String[] args) {
		String encrypt = Md5Util.encrypt("123456", EncodeStrategy.ENCODE_HEX, CaseStrategy.LOWER_CASE);
		System.out.println(encrypt);
	}
}
