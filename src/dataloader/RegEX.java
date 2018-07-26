package dataloader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: cccis
 * @Date: 7/17/2018 10:56
 * @Description: 从csv文件的文件名获取message，cipher
 */
public class RegEX {
    public static String[] getMessageCipher(String str) {
        String[] messageandcipher = new String[2];
        String[] strs=str.split("_");
        messageandcipher[0] = getMessage(strs[2]);
        messageandcipher[1] = getCipher(strs[3]);
        return messageandcipher;
    }

    private static String getMessage(String str){
        String regEx = "m=";
        if (str != null && !"".equals(str)) {
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            if (m.find()) {
                return m.replaceAll("");
            } else {
                return str;
            }
        } else {
            return str;
        }
    }

    private static String getCipher(String str){
        String regEx = "c=";
        if (str != null && !"".equals(str)) {
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            if (m.find()) {
                return m.replaceAll("");
            } else {
                return str;
            }
        } else {
            return str;
        }
    }
}
