

import java.io.IOException;

import com.omnibuscode.utils.FileUtil;
import com.omnibuscode.utils.HttpURLConnectionUtil;

public class HttpRequestFileDownloadTest {

    public static void main(String args[]) throws IOException {
        
        
        String reqURL = "http://[FILESERVER]/FrontControllerServlet.do?service=xupservice&domain=NEXTp&model=CF_NFileMM_S01&format=xml&version=xplatform&param_fm_seq=244524&param_request_file_name=&nexaversion=0";
        String resData = HttpURLConnectionUtil.requestPost(reqURL, null, null);
        FileUtil.writeFile("F:/test1.txt", resData, null);   
        
        
/**
        URL url = new URL(" http://[FILESERVER]/FrontControllerServlet.do?service=xupservice&domain=NEXTp&model=CF_NFileMM_S01&format=xml&version=xplatform&param_fm_seq=244519&param_request_file_name=&nexaversion=0");
        
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
     
        // 서버로부터 메세지를 받을 수 있도록 한다. 기본값은 true이다.
        con.setDoInput(true);
     
        // 헤더값을 설정한다.
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
     
        // 전달 방식을 설정한다. POST or GET, 기본값은 GET 이다.
        con.setRequestMethod("POST");
     
        // 서버로 데이터를 전송할 수 있도록 한다. GET방식이면 사용될 일이 없으나, true로 설정하면 자동으로 POST로 설정된다. 기본값은 false이다.
        con.setDoOutput(true);
     
        //POST방식이면 서버에 별도의 파라메터값을 넘겨주어야 한다.
        //String param    = "ID=rQ+g4R8qmTlAey1Wn/PwUA==&cust_no=vBiSI2BWVsu6lK03U7dsfA==&prom_no=";
        String param = "CMD=SYNC_BATCH_START&ID=08050602&PASS=xnql890-";
     
        OutputStream out_stream = con.getOutputStream();
        out_stream.write( param.getBytes("UTF-8") );
        out_stream.flush();
        out_stream.close();
     
        InputStream is      = null;
        BufferedReader in   = null;
        String data         = "";
     
        is  = con.getInputStream();
        in  = new BufferedReader(new InputStreamReader(is), 8 * 1024);
     
        String line = null;
        StringBuffer buff   = new StringBuffer();
     
        while ( ( line = in.readLine() ) != null )
        {
            buff.append(line + "\n");
        }
        data    = buff.toString().trim();

        FileUtil.writeFile("F:/test.txt", data, null);        
**/
        
        
        
        
        
        
        
        

//        URL url = new URL(" http://[FILESERVER]/FrontControllerServlet.do?service=xupservice&domain=NEXTp&model=CF_NFileMM_S01&format=xml&version=xplatform&param_fm_seq=244519&param_request_file_name=&nexaversion=0");
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setDoInput(true);
////        conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        conn.setRequestMethod("POST");
//        conn.setDoOutput(true);
//        conn.setUseCaches(false);
//
////        JSONObject jsonObj = new JSONObject();
////        jsonObj.put("CMD", "SYNC_BATCH_START");
////        jsonObj.put("ID", "08050602");
////        jsonObj.put("PASS", "xnql890-");
//        
//        String param1 = "CMD=SYNC_BATCH_START&ID=08050602&PASS=xnql890-";
//        
//        OutputStream out_stream1 = null;
//        try {
//            out_stream1 = conn.getOutputStream();
////            out_stream.write(jsonObj.toJSONString().getBytes("UTF-8"));
//            out_stream1.write(param1.getBytes("UTF-8"));
//            out_stream1.flush();
//            out_stream1.close();
//            System.out.println("-- RD SYNC_BATCH_START");
//            
//            
//            InputStream is      = null;
//            BufferedReader in   = null;
//            String data         = "";
//         
//            is  = conn.getInputStream();
//            in  = new BufferedReader(new InputStreamReader(is), 8 * 1024);
//         
//            String line = null;
//            StringBuffer buff   = new StringBuffer();
//         
//            while ( ( line = in.readLine() ) != null )
//            {
//                buff.append(line + "\n");
//            }
//            data    = buff.toString().trim();
//            
//        } catch (IOException ioe) {
//            System.out.println("RD 접속이 불가합니다 - "+ioe);
//        } finally {
//            out_stream1 = null;
//            conn = null;
//            url = null;
//        }

    }
    
    
    

}
