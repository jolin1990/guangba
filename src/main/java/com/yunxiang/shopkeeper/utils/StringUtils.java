package com.yunxiang.shopkeeper.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: Created by jiely on 2015/10/10.
 */
public class StringUtils {

    /**
     * url=http://103.17.41.7:9999/huaku-manager/swfupload/getPictureFile?pictureFile=810.jpg
     * 获得url的图片文件名
     *
     * @param name ？号后面的文件名称
     * @param url  图片链接
     * @return 图片名称
     */
    public static String getParameter(String name, String url) {
        int index = url.indexOf("?");
        if (index < 0) {
            return "";
        }
        String parameterUrl = url.substring(index);
        String[] parameter = parameterUrl.split("&");
        for (int i = 0; i < parameter.length; i++) {
            if (parameter[i].contains(name)) {
                return parameter[i].split("=")[1];
            }
        }
        return "";
    }

    /**
     * desc: 字符串逗号拆分，拆分为字符串List
     * auther:jiely
     * create at 2015/12/3 10:29
     */
    public static List<String> splitComma(String str) {
        String[] info = str.split(",");
        List<String> myList = new ArrayList<String>();
        for (int i = 0; i < info.length; i++) {
            myList.add(info[i]);
        }
        return myList;
    }

    /**
     * desc: 字符串逗号拆分，拆分为字符串List
     * auther:jiely
     * create at 2015/12/3 10:29
     */
    public static long[] splitCommaLong(String str) {
        String[] info = str.split(",");
        long[] ids = new long[info.length];
        for (int i = 0; i < info.length; i++) {
            ids[i] = Long.parseLong(info[i]);
        }
        return ids;
    }

    /**
     * desc: 字符串逗号拆分，拆分为字符串List
     * auther:jiely
     * create at 2015/12/3 10:29
     */
    public static String longsToString(long[] ids) {
       StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            sb.append(String.valueOf(ids[i]));
            if(i != ids.length-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * path="http://192.168.1.11:8080/laiba-app/icons/shop/imgs/1.jpg"
     * desc: 通过url获取图片文件名称，如1.jpg
     * auther:jiely
     * create at 2015/10/10 20:44
     */
    public static String getImageFileName(String path) {
        String fileName = "";
        char[] chs;
        chs = path.toCharArray();
        StringBuilder sb = new StringBuilder();
        int i = path.length() - 1;
        for (; i > 0; i--) {
            if (chs[i] == '/') {
                break;
            }
        }
        fileName = path.substring(i);
        return fileName;
    }

    /**
     * desc:把字符串中的数字提取出来，顺序组成数字
     * auther:jiely
     * create at 2015/10/10 19:53
     */
    public static int getNumber(String str) {
        if (str == null) {
            return 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 0x30 && str.charAt(i) <= 0x39) {
                stringBuilder.append(str.charAt(i));
            }
        }

        String strNumber = stringBuilder.toString();

        return Integer.parseInt(strNumber);
    }

    /**
     * desc:把字符串中的时间提取出来，返回日期 如 2015-10-26 17:27 返回 2015-10-26
     * auther:jiely
     * create at 2015/10/10 19:53
     */
    public static String getYMD(String date) {
        if(date == null || date.length() == 0){
            return "";
        }
        String[] info = date.split(" ");
        return info[0];
    }

    /**
     * desc:把字符串中的时间提取出来，返回日期 如 10-26 17:27 返回 2015-10-26
     * auther:jiely
     * create at 2015/10/10 19:53
     */
    public static String getMD(String date) {
        if(date == null || date.length() == 0){
            return "";
        }
        String[] info = date.split(" ");
        String md = info[0];
        md = md.substring(5);
        return md;
    }

    /**
     * desc:把字符串中的时间提取出来，返回时分 如 2015-10-26 17:27 返回 17:27
     * auther:jiely
     * create at 2015/10/10 19:53
     */
    public static String getHM(String date) {
        if(date == null || date.length() == 0){
            return "";
        }
        String[] info = date.split(" ");
        return info[1];
    }

}
