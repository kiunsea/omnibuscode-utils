package com.omnibuscode.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author KIUNSEA
 *CommandLineInterface 지원 유틸리티
 */
public class CLIUtil {

    /**
     * 윈도우 도스 명령어를 실행 
     * @param command 실행 명령어
     * @param executePath 실행할 위치 (ex> D:\\TEMP\\, 프로그램 실행 위치라면 null)
     * @param charsetName ex> UTF-8, NULL is ms949
     * @return 실행 결과
     * @throws IOException
     * @throws InterruptedException
     */
    public static List<String> runtimeExec(String command[], String executePath, String charsetName) throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder(command);
        List<String> pbCmd = pb.command();
        
        System.out.println("명령어>>");
        Iterator<String> pbCmdIter = pbCmd.iterator();
        while (pbCmdIter.hasNext()) {
            System.out.print(" " + pbCmdIter.next()); // 실행 명령어를 출력
        }
        System.out.println();
        
        if (executePath != null && executePath.trim().length() > 0) {
            pb.directory(new File(executePath));
        }
        pb.redirectErrorStream(true);
        Process proc = pb.start();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(proc.getInputStream(), charsetName != null ? charsetName : "ms949"));
        List<String> strList = new ArrayList<String>();
        String line = null;
        StringBuffer lineSb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            lineSb.append(line + "\n");
            strList.add(line);
        }
        if (lineSb.length() > 0) { // Tomcat 또는 프로그램을 DOS Command 창에서 명령어 수행시 실행 결과 메세지를 수집 가능
            if (lineSb.length() < 10) {
                System.out.println(lineSb); // 실행 결과를 출력
            } else {
                System.out.println(">> too many logs... <<");
            }
        } else { // 프로그램을 OS서비스로 등록하여 수행시 실행 결과 메세지를 취할 수 없으므로 임의로 완료 메세지 로깅
            System.out.println("[CommandLineInterfaceUtil.runtimeExec] 실행 결과 메세지 없음");
        }

        proc.waitFor();

        command = null;
        proc = null;
        br = null;
        line = null;

        return strList;
    }
    
    /**
     * 윈도우 도스 명령어를 실행 
     * @param command 실행 명령어
     * @param executePath 실행할 위치 (ex> D:\\TEMP\\, 프로그램 실행 위치라면 null)
     * @param charsetName ex> UTF-8, NULL is ms949
     * @return 실행 결과
     * @throws IOException
     * @throws InterruptedException
     */
    public static List<String> runtimeExec(List<String> command, String executePath, String charsetName) throws IOException, InterruptedException {

        ProcessBuilder pb = new ProcessBuilder(command);
        List<String> pbCmd = pb.command();
        
        System.out.println("명령어>>");
        Iterator<String> pbCmdIter = pbCmd.iterator();
        while (pbCmdIter.hasNext()) {
            System.out.print(" " + pbCmdIter.next()); // 실행 명령어를 출력
        }
        System.out.println();
        
        if (executePath != null && executePath.trim().length() > 0) {
            pb.directory(new File(executePath));
        }
        pb.redirectErrorStream(true);
        Process proc = pb.start();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(proc.getInputStream(), charsetName != null ? charsetName : "ms949"));
        List<String> strList = new ArrayList<String>();
        String line = null;
        StringBuffer lineSb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            lineSb.append(line + "\n");
            strList.add(line);
        }
        if (lineSb.length() > 0) { // Tomcat 또는 프로그램을 DOS Command 창에서 명령어 수행시 실행 결과 메세지를 수집 가능
            if (lineSb.length() < 10) {
                System.out.println(lineSb); // 실행 결과를 출력
            } else {
                System.out.println(">> too many logs... <<");
            }
        } else { // 프로그램을 OS서비스로 등록하여 수행시 실행 결과 메세지를 취할 수 없으므로 임의로 완료 메세지 로깅
            System.out.println("[CommandLineInterfaceUtil.runtimeExec] 실행 결과 메세지 없음");
        }

        proc.waitFor();

        command = null;
        proc = null;
        br = null;
        line = null;

        return strList;
    }
    
    /**
     * 유효한 파일경로 형태로 변환해준다<br/>
     * 파라미터가 파일 경로가 아닐수도 있으니 주의해야 한다
     * @param path
     * @return replaced path
     */
    public static String convertValidPath(String path) {
        return StringUtil.replaceAll(path, "/", "\\", false);
    }
}
