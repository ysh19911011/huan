package dailyTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * s md5加密类 　
 * @author kangfukang 　
 * @date 2013-4-18 　
 * @version 1.0
 */
public class MD5Util {
	

	/**
	 * @Comments 输入明文字符串,返回加密的字符串. MD5算法.
	 * @param empID
	 * @param status
	 * @Vsersion: 1.0
	 */
	public static String encrypt(final Object object) {
		String result = null;
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(((String) object).getBytes());
			final byte bytes[] = messageDigest.digest();
			int index;
			final StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < bytes.length; offset++) {
				index = bytes[offset];
				if (index < 0)
					index += 256;
				if (index < 16)
					buf.append("0");
				buf.append(Integer.toHexString(index));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return result;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(decrypt("80%81%79%82%77%82%77%82%91%140%140%73%126%138%136%"));

		System.out.println(encrypt("56472727@qq.com"));
	}

	/**
	 * 解密
	 * 
	 * @param ssoToken 字符串
	 * @return String 返回加密字符串
	 */
	public static String decrypt(String ssoToken) {
		try {
			String name = new String();
			java.util.StringTokenizer st = new java.util.StringTokenizer(ssoToken, "%");
			while (st.hasMoreElements()) {
				int asc = Integer.parseInt((String) st.nextElement()) - 27;
				name = name + (char) asc;
			}

			return name;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param ssoToken 字符串
	 * @return String 返回加密字符串
	 */
	public static String encryptMailT(String ssoToken) {
		try {
			byte[] _ssoToken = ssoToken.getBytes("ISO-8859-1");
			String name = new String();
			for (int i = 0; i < _ssoToken.length; i++) {
				int asc = _ssoToken[i];
				_ssoToken[i] = (byte) (asc + 27);
				name = name + (asc + 27) + "%";
			}
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
