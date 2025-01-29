package com.omnibuscode.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @author KIUNSEA
 * 
 */
public class HttpURLConnectionUtil {
    
    /**
     * text 문서의 url 에서 문서 내용을 읽어 반환한다.
     * @param urlString
     * @return 문서내용 String
     * @throws IOException
     */
    public static String readUrlTextFile(String urlString) throws IOException {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);

            // URL 연결을 열고 HTTP GET 요청 수행
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // InputStreamReader를 통해 URL에서 데이터를 읽음
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                // 한 줄씩 읽어서 StringBuilder에 추가
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine).append("\n");
                }

                // 스트림 닫기
                in.close();

                // 읽어온 내용을 String에 저장
                String fileContent = content.toString();

                // 출력 (읽어온 내용)
                return fileContent;

            } else {
                System.out.println("HTTP 응답 코드: " + responseCode);
                return null;
            }

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * HTTP POST DATA 전송
     * <br/>Content-Type 헤더 기본값은 "application/x-www-form-urlencoded;charset=utf-8"
     * @param reqUrl(required)
     * @param props - Request Property(option) HTTP 헤더에 파라미터 추가
     * @param params - Request Parameter(option) HTTP 본문에 파라미터 추가
     * @return
     * @throws IOException
     */
    public static String requestPostData(String reqUrl, Map<String, String> props, Map<String, String> params)
            throws IOException {

        StringBuffer resStrBuff = null;

        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        if (props != null && props.size() > 0) {
            Iterator<String> propIter = props.keySet().iterator();
            String key = null;
            String value = null;
            while (propIter.hasNext()) {
                key = propIter.next().toString();
                value = props.get(key).toString();
                conn.setRequestProperty(key, value);
            }
        }
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        StringBuffer paramSb = null;
        if (params != null) {
            paramSb = new StringBuffer();
            Iterator<String> paramIter = params.keySet().iterator();
            String key = null;
            String value = null;
            int pCnt = 0;
            while (paramIter.hasNext()) {
                key = paramIter.next().toString();
                value = params.get(key).toString();
                if (pCnt > 0)
                    paramSb.append("&");
                paramSb.append(key + "=" + URLEncoder.encode(value, "UTF-8"));
                pCnt++;
            }
        }

        OutputStream out_stream = null;
        BufferedReader brin = null;
        try {
            if (paramSb != null) {
                out_stream = conn.getOutputStream();
                out_stream.write(paramSb.toString().getBytes("UTF-8"));
                out_stream.flush();
            }

            String cEncode = conn.getHeaderField("Content-Encoding");
            if (cEncode != null
                    && (cEncode.toLowerCase().indexOf("gzip") > -1 
                            || cEncode.toLowerCase().indexOf("deflate") > -1
                            || cEncode.toLowerCase().indexOf("compress") > -1)) {
                // 응답데이터가 파일이 아님에도 전송량을 줄이기 위해 압축되어 있는 경우
                brin = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream())));
            } else {
                brin = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            }

            resStrBuff = new StringBuffer();
            String strIn = null;
            while ((strIn = brin.readLine()) != null) {
                resStrBuff.append(strIn + System.getProperty("line.separator"));
            }
        } catch (IOException ioe) {
            System.out.println("접속이 불가합니다 - " + ioe);
        } finally {
            if (out_stream != null) {
                out_stream.close();
                out_stream = null;
            }
            if (brin != null) {
                brin.close();
                brin = null;
            }
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
            url = null;
        }

        return resStrBuff != null ? resStrBuff.toString() : null;
    }

    /**
     * POST 요청데이터를 전달하고 응답데이터를 반환한다.
     * <br/>Content-Type 헤더값은 "text/xml;charset=UTF-8"
     * @param reqURL(required)
     * @param sessionCookie(option) 세션유지용
     * @param params - Request Parameter(option) HTTP 본문에 파라미터 추가
     * @return response data
     * @throws IOException
     */
    public static String requestPost(String reqURL, String sessionCookie, Map<String, String> params) throws IOException {

        URL url = new URL(reqURL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);

        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        conn.setRequestProperty("Accept", "application/xml, text/xml, */*");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.setRequestProperty("Accept-Language", "ko,ja;q=0.8,en-US;q=0.5,en;q=0.3");
        conn.setRequestProperty("Content-type", "text/xml;charset=UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Pragma", "no-cache");
        if (sessionCookie != null) {
            conn.setRequestProperty("Cookie", sessionCookie);
        }
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        StringBuffer paramSb = null;
        if (params != null) {
            paramSb = new StringBuffer();
            Iterator<String> paramIter = params.keySet().iterator();
            String key = null;
            String value = null;
            int pCnt = 0;
            while (paramIter.hasNext()) {
                key = paramIter.next().toString();
                value = params.get(key).toString();
                if (pCnt > 0)
                    paramSb.append("&");
                paramSb.append(key + "=" + URLEncoder.encode(value, "UTF-8"));
                pCnt++;
            }
        }

        OutputStream out_stream = null;
        if (paramSb != null) {
            out_stream = conn.getOutputStream();
            out_stream.write(paramSb.toString().getBytes("UTF-8"));
            out_stream.flush();
        }

        sessionCookie = conn.getHeaderField("Set-Cookie");
        if (sessionCookie != null) {
            System.out.println("쿠키:" + sessionCookie);
        }

        BufferedReader brin = null;
        String cEncode = conn.getHeaderField("Content-Encoding");
        if (cEncode != null && (cEncode.toLowerCase().indexOf("gzip") > -1
                || cEncode.toLowerCase().indexOf("deflate") > -1 
                || cEncode.toLowerCase().indexOf("compress") > -1)) {
            // 응답데이터가 파일이 아님에도 전송량을 줄이기 위해 압축되어 있는 경우
            brin = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream())));
        } else {
            brin = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        }

        String resStr = null;
        StringBuffer resStrBuff = new StringBuffer();
        try {
            while ((resStr = brin.readLine()) != null) {
                resStrBuff.append(resStr + System.getProperty("line.separator"));
            }
        } finally {
            if (out_stream != null) {
                out_stream.close();
                out_stream = null;
            }
            if (brin != null) {
                brin.close();
                brin = null;
            }
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
            url = null;

        }

        return resStrBuff.toString();
    }
    
    /**
     * 2023.08.09 Base64Util 클래스가 현재 사용중이기 때문에 함수를 비활성화 처리한다(Base64Util를 재개하면 다시 활성화)
     * HTTP GET DATA 전송 auth id,pass 가 null이면 Authorization header를 설정하지 않음
     * 
     * @param serviceUrl 호출URL
     * @param params     파라미터
     * @param authId     인증아이디
     * @param authPass   인증암호
     * @throws IOException
     */
//    public static void requestGet(String serviceUrl, Map<String, String> params, String authId, String authPass) throws IOException {
//        
//        StringBuffer paramSb = new StringBuffer();
//        Iterator<String> paramIter = params.keySet().iterator();
//        String key = null;
//        String value = null;
//        int pCnt = 0;
//        while (paramIter.hasNext()) {
//            key = paramIter.next().toString();
//            value = params.get(key).toString();
//            if (pCnt > 0)
//                paramSb.append("&");
//            paramSb.append(key + "=" + value);
//            pCnt++;
//        }
//        
//        URL url = new URL(serviceUrl + "?" + paramSb.toString());
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setDoInput(true);
//        conn.setRequestMethod("GET");   
//        conn.setUseCaches(false);   
//        conn.setDefaultUseCaches(false);
//        
//        if (authId != null && authPass != null) {
//            String encodedAuth = "Basic " + Base64Util.encode(authId + ":" + authPass);
//            conn.setRequestProperty("Authorization", encodedAuth);
//        }
//        
//        BufferedReader brin = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String resStr = null;
//        StringBuffer resStrBuff = new StringBuffer();
//        try {
//            while ((resStr = brin.readLine()) != null) {
//                resStrBuff.append(resStr);
//            }
//        } finally {
//            if (brin != null) {
//                brin.close();
//                brin = null;
//            }
//            conn = null;
//            url = null;
//            resStr = null;
//        }
//    }
}
