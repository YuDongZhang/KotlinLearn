package com.cainiaowo.netdemo.support;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.EncryptUtils;
import com.cainiaowo.netdemo.config.CaiNiaoConfigKt;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 解密服务端返回的数据工具,以及将unicode和中文互转工具
 */
public class CaiNiaoUtils {

    private CaiNiaoUtils() {
    }

    /**
     * 中文转 unicode
     *
     * @param string 中文字符串
     * @return unicode字符串
     */
    public static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * unicode 转中文
     *
     * @param string unicode字符串
     * @return 中文字符串
     */
    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }

    /**
     * 解析返回的data数据
     *
     * @param dataStr 未解密的响应数据
     * @return 解密后的数据String
     */
    @Nullable
    public static String decodeData(@Nullable String dataStr) {
        if (dataStr != null) {
            return new String(EncryptUtils.decryptBase64AES(
                    dataStr.getBytes(), CaiNiaoConfigKt.NET_CONFIG_APPKEY.getBytes(),
                    "AES/CBC/PKCS7Padding",
                    "J#y9sJesv*5HmqLq".getBytes()
            ));
        } else {
            return null;
        }
    }
}
