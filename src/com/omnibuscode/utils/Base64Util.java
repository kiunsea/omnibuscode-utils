package com.omnibuscode.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author KIUNSEA
 */
public class Base64Util {

	/**
	 * 원본파일을 base64로 인코딩된 파일로 생성한다
	 * 
	 * @param inputFileName
	 * @param outputFileName
	 * @throws IOException
	 */
	public static void encodeFiletoFile(String inputFileName, String outputFileName) throws IOException {
		byte[] fileBytes = Files.readAllBytes(Paths.get(inputFileName));
		String encodedString = Base64.getEncoder().encodeToString(fileBytes);

		try (OutputStream out = new FileOutputStream(new File(outputFileName))) {
			out.write(encodedString.getBytes("UTF-8"));
		}
	}

	/**
	 * 원본파일을 base64인코딩 문자열로바꾸어준다.
	 * 
	 * @param inputFileName
	 * @throws IOException
	 */
	public static void encodeFiletoString(String inputFileName) throws IOException {
		byte[] fileBytes = Files.readAllBytes(Paths.get(inputFileName));
		String encodeString = Base64.getEncoder().encodeToString(fileBytes);
		System.out.println(encodeString);
	}

	/**
	 * 인코딩된 파일을 다시 디코딩해서 원본파일로 만들어준다
	 * 
	 * @param inputFileName
	 * @param outputFileName
	 * @throws IOException
	 */
	public static void decodeFiletoFile(String inputFileName, String outputFileName) throws IOException {
		byte[] encodedBytes = Files.readAllBytes(Paths.get(inputFileName));
		String encodedString = new String(encodedBytes, "UTF-8");
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);

		try (OutputStream out = new FileOutputStream(new File(outputFileName))) {
			out.write(decodedBytes);
		}
	}

	/**
	 * 인코딩된 문자열을 다시 디코딩해서 원본파일을 만들어준다
	 * 
	 * @param encodesString
	 * @param outputFileName 파일이 저장될 경로와 파일명을 지정
	 * @throws IOException
	 */
	public static void decodeStringtoFile(String encodesString, String outputFileName) throws IOException {
		byte[] decodedBytes = Base64.getDecoder().decode(encodesString);

		try (BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(outputFileName))) {
			outStream.write(decodedBytes);
		}
	}

	/**
	 * 문자열을 base64로 인코딩한다
	 * 
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String s) throws UnsupportedEncodingException {
		byte[] encodeBytes = s.getBytes("UTF-8");
		return Base64.getEncoder().encodeToString(encodeBytes);
	}

	/**
	 * 인코딩된 문자열을 base64로 디코딩한다.
	 * 
	 * @param s
	 * @throws Exception
	 */
	public static String decodeString(String s) throws Exception {
		byte[] decodedBytes = Base64.getDecoder().decode(s);
		return new String(decodedBytes, "UTF-8");
	}

	/**
	 * Base64Encoding을 수행한다. (Binary to Ascii String)
	 * 
	 * @param encodeBytes encoding할 byte array
	 * @return encoding 된 String
	 */
	public static String encode(byte[] encodeBytes) throws Exception {
		return Base64.getEncoder().encodeToString(encodeBytes);
	}

	/**
	 * Base64Decoding 수행한다. (Ascii String to Binary)
	 * 
	 * @param strDecode decoding할 String
	 * @return decoding 된 byte array
	 */
	public static byte[] decode(String strDecode) throws Exception {
		return Base64.getDecoder().decode(strDecode);
	}

}
