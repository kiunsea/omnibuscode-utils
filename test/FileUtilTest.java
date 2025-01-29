import java.io.IOException;

import com.omnibuscode.utils.FileUtil;

public class FileUtilTest {

    public static void main(String args[]) throws IOException {
        System.out.println(FileUtil.getFileCRC32String("d:/TEMP/123.jpg"));
        System.out.println(FileUtil.getFileCRC32String("d:/TEMP/1234.jpg"));
        System.out.println(FileUtil.getFileCRC32String("d:/TEMP/456.jpg"));
        System.out.println(FileUtil.getFileCRC32String("d:/TEMP/4567.jpg"));
    }
}
