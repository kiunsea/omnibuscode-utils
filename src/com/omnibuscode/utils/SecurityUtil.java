package com.omnibuscode.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author KIUNSEA
 *
 */
public class SecurityUtil {   
  
    /**
     * 입력한 문자열에서 MD5 해시 String 을 추출한다.
     * @param input
     * @param charset 입력된 문자열(str)의 charset 정보, null 입력시 UTF-8 로 설정됨
     * @return 
     */
    public final static String toMD5(String input, String charset) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            input.getBytes();
            md.update(input.getBytes(charset == null ? "UTF-8" : charset));
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            result = null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }
    
    /**
     * 입력값에서 해시 byte[] 를 추출한다.<br/>
     * 사용예> byte[] ret = HashUtil.digest("MD5", "abcd".getBytes());
     * @param algorithm 지원목록 : MD2, MD5, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, SHA-512/256
     * @param input 입력값 (문자열에서 byte배열 추출시엔 String.getBytes() 함수를 이용해야 한다)
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] digest(String algorithm, byte[] input) throws NoSuchAlgorithmException {   
        MessageDigest md = MessageDigest.getInstance(algorithm);   
        return md.digest(input);   
    }
    
    /**
     * 입력값에서 MD5 해시값을 추출후 Base64로 인코딩하여 리턴한다.
     * @param inputValue
     * @return String
     * @throws Exception
     */
//    public static String getCryptoMD5String(String inputValue) throws Exception {   
//        if( inputValue == null ) throw new Exception("Can't conver to Message Digest 5 String value!!");   
//        byte[] ret = digest("MD5", inputValue.getBytes());   
//        String result = Base64Util.encode(ret);
//        return result;
//    }

}