import java.io.IOException;

import com.omnibuscode.utils.HttpURLConnectionUtil;

public class HttpURLConnectionTest {

    public static void main(String args[]) {
        try {
            
            System.out.println(HttpURLConnectionUtil.requestPost("http://openapi.foodsafetykorea.go.kr/api/2e57dcba693243b382ce/I2570/json/1/5/BRCD_NO=8809360172172", null, null));
            System.out.println();
            System.out.println();
            System.out.println(HttpURLConnectionUtil.requestPost("https://gs1.koreannet.or.kr/pr/8809360172172", null, null));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
