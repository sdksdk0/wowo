package cn.tf.utils;

import java.security.MessageDigest;  

/**
 *  MD5加密
 *  此类只能将明码加密后的字符串只有16位，如有更高要求的必要，可自行修改算法（只需局部修改）即可
 */
public class MD5Encryption {  
	// 十六进制下数字到字符的映射数组  
	private static String[] hexDigits = new String[]{"0", "f", "2", "e", "4", "a", "6", "d", "3", "9", "5", "8", "c", "7", "b", "1"};	 // 乱序，更好

	/**
	 *  明码加密  
	 * @param plainCode 输入的明码
	 * @return 经MD5加密后的返回的字符串
	 */
	public static String createPassword(String plainCode) {  
		return encryptByMD5(plainCode);  
	}  

	/**
	 *  密码鉴定
	 * @param cryptographicPwd 密码加密后的
	 * @param plainCode 明码
	 * @return 若输入的明码经MD5加密后能与cryptographicPwd equals,则返回true;否则返回false
	 */
	public static boolean authenticatePassword(String cryptographicPwd , String plainCode){  
		if(cryptographicPwd.equals(encryptByMD5(plainCode)))   
			return true;  
		else  
			return false;  
	}  

	// 对字符串进行MD5编码 ，加密
	private static String encryptByMD5(String plainCode) {  
		if(plainCode != null) {  
			try {  
				// 创建具有指定算法名称的信息摘要  
				MessageDigest md5 = MessageDigest.getInstance("MD5");  
				// 获取字节数组并对其完成摘要计算，并得到字节数组
				byte[] resultBytes = md5.digest(plainCode.getBytes());  
				// 将得到的字节数组转为十六进制字符串返回  
				String hexResultString = byteArrayToHexString(resultBytes);  

				return hexResultString.toUpperCase();
			} catch(Exception ex) {
				ex.printStackTrace();
			}  
		}  

		return null;  
	}  

	// 转换字节数组为十六进制字符串  
	private static String byteArrayToHexString(byte[] bytes) {  
		StringBuffer resultBytes = new StringBuffer();  
		int index = 0;  
		for(index=0; index<bytes.length; ++index) {  
			resultBytes.append(byteToHexString(bytes[index]));  
		}

		return resultBytes.toString();  
	}  

	// 将字节转化成十六进制的字符串  
	private static String byteToHexString(byte bt) {  
		int n = bt; 
		if(n < 0) {  
			n = n + 256;  
		}  
		int d = (n/4 + n%8) % 16;  

		return hexDigits[d];  
	}  
}  
