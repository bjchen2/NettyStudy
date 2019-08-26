package com.study.ichat.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * MD5相关工具类
 * @author cxd27419
 */
public class MD5Utils {

	/**
	 * 对字符串进行md5加密
	 */
	public static String getMD5Str(String strValue) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
	}

	public static void main(String[] args) {
		try {
			String md5 = getMD5Str("hello");
			System.out.println(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
