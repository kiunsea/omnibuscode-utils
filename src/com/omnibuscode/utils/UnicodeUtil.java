package com.omnibuscode.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KIUNSEA
 *
 */
public class UnicodeUtil {

    private static final int HANGEUL_BASE = 0xAC00; // '가'
    private static final int HANGEUL_END = 0xD7AF;
    // 이하 cho, jung, jong은 계산 결과로 나온 자모에 대해 적용
    private static final int CHO_BASE = 0x1100;
    private static final int JUNG_BASE = 0x1161;
    private static final int JONG_BASE = (int) 0x11A8 - 1;
    // 이하 ja, mo는 단독으로 입력된 자모에 대해 적용
    private static final int JA_BASE = 0x3131;
    private static final int MO_BASE = 0x314F;

    /**
     * 단어를 초성,중성,종성으로 분리후 char리스트로 리턴(공백포함됨) <br/>
     * 공백의 경우 charcode 32가 출력됨(160아님)<br/>
     * 받침이 없는 글자의 경우 마지막에 charcode 0이 붙게됨<br/>
     * (참고 : https://gist.github.com/thirdj/5333532 초성 중성 종성 분리 하기)<br/><br/>
     * 유니코드 한글은 0xAC00 으로부터 초성 19개, 중상21개, 종성28개로 이루어지고 이들을 조합한 11,172개의 문자를 갖는다. <br/>
     * 한글코드의 값 = ((초성 * 21) + 중성) * 28 + 종성 + 0xAC00 (0xAC00은 'ㄱ'의 코드값) <br/>
     * 따라서 다음과 같은 계산 식이 구해진다. 유니코드 한글 문자 코드 값이 X일 때, <br/>
     * 초성 = ((X - 0xAC00) / 28) / 21 중성 = ((X - 0xAC00) / 28) % 21 종성 = (X - 0xAC00) % 28 <br/>
     * 이 때 초성, 중성, 종성의 값은 각 소리 글자의 코드값이 아니라 이들이 각각 몇 번째 문자인가를 나타내기 때문에 다음과 같이 다시 처리한다. <br/>
     * 초성문자코드 = 초성 + 0x1100 //('ㄱ') 중성문자코드 = 중성 + 0x1161 // ('ㅏ') 종성문자코드 = 종성 + 0x11A8 - 1 // (종성이 없는 경우가 있으므로 1을 뺌)
     * @param text 입력 문자열
     * @param returnType 2인 경우 초성만, 1인 경우 초성,중성만 0인 경우 초성,중성,종성 모두
     * @return
     */
    public static List<Character> normalizeToChars(String text, int returnType) {

        List<Character> list = new ArrayList<>();

        for (char c : text.toCharArray()) {
            if ((c <= 10 && c <= 13) || c == 32) {
                list.add(c);
                continue;
            } else if (c >= JA_BASE && c <= JA_BASE + 36) {
                list.add(c);
                continue;
            } else if (c >= MO_BASE && c <= MO_BASE + 58) {
                list.add((char) 0);
                continue;
            } else if (c >= HANGEUL_BASE && c <= HANGEUL_END) {
                int choInt = (c - HANGEUL_BASE) / 28 / 21;
                int jungInt = ((c - HANGEUL_BASE) / 28) % 21;
                int jongInt = (c - HANGEUL_BASE) % 28;
                char cho = (char) (choInt + CHO_BASE);
                char jung = (char) (jungInt + JUNG_BASE);
                char jong = jongInt != 0 ? (char) (jongInt + JONG_BASE) : 0;

                if (returnType > 1) {
                    list.add(cho);
                } else if (returnType > 0) {
                    list.add(cho);
                    list.add(jung);
                } else {
                    list.add(cho);
                    list.add(jung);
                    list.add(jong);
                }
            } else {
                list.add(c);
            }

        }
        return list;
    }
    
    /**
     * 단어를 초성,중성,종성으로 분리후 String형태로 리턴(공백제거됨)<br/>
     * (참고 : https://d2.naver.com/helloworld/76650 유니코드와 Java를 이용한 한글 처리)
     * @param text
     * @return
     */
    public static String normalizeToString(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD);
    }
    
    /**
     * a문자열 내에 b문자열과 유사한 문자열이 포함되는지 확인 <br/>
     * 문자열의 초성들만 추출하여 유사 단어가 포함되어 있는지에 대해 검사한다<br/>
     * 예를 들어, "인증서 합치기"라는 문자열에 "이중서 하치기" 라는 문자열과의 매칭 확률이 약 75%라면 true가 반환된다  
     * @param a 확률 연산 대상이 될 문자열
     * @param b 포함 여부를 확인할 문자열
     * @param hasSpace 스페이스를 포함하여 문자열을 비교할지 여부
     * @return
     */
    public static boolean isProbable(String a, String b) {
        List<Character> charListA = UnicodeUtil.normalizeToChars(a, 2);
        List<Character> charListB = UnicodeUtil.normalizeToChars(b, 2);
        
        for (int i = 0; i < charListA.size(); i++) {
            if ((char) charListA.get(i) == (char) charListB.get(0)) {
                int j = NumberUtil.convertInteger(charListB.size() * 0.8); //약 75%의 확률만 가져도 됨
                
                boolean compareRst = false; //비교 결과
                for (int k = 0; k < j; k++) {
                    if ((char) charListA.get(i + k) == (char) charListB.get(k)) {
                        compareRst = true;
                    } else {
                        compareRst = false; //하나라도 다르면 실패
                        break;
                    }
                }
                
                if (compareRst) {
                    //확률적으로 유사한 단어를 찾게되면 검색을 중단하고 true를 반환
                    return true;
                }
            }
        }
        
        return false;
    }
}
