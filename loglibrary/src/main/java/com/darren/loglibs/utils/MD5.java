package com.darren.loglibs.utils;

import java.security.MessageDigest;

/**
 * 
 * @author Administrator
 * 
 */
public class MD5 {

	public static void main(String[] args) {
		String str = "hello";
		String str1 = "hello";
		String str2 = "hello,jianxiong";
		System.out.println(getMD5(str));
		System.out.println(getMD5(str1));
		System.out.println(getMD5(str2));
	}

	public static String getMD5(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			return getHashString(digest);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getHashString(MessageDigest digest) {
		StringBuilder builder = new StringBuilder();
		for (byte b : digest.digest()) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}
		return builder.toString();
	}
}
