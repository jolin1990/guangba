package com.yunxiang.shopkeeper.utils;


import com.yunxiang.shopkeeper.TApplication;

/**
 * desc: Created by jiely on 2015/9/1.
 */
public class DictionaryUtil {
    /**
     *
     * author：jiely
     * Date：2015年4月10日
     * Title: write
     * Description: 向数据库字典表中写入一个value，字段名为key
     * param @param key
     * param @param value
     * return void    返回类型
     * throws
     */
    public static void write(String key, String value){
        TApplication.dictionaryService.addValue(key, value);
    }

    /**
     *
     * author：jiely
     * Date：2015年4月10日
     * Title: read
     * Description: 从数据库字典表中根据字段读取一个字符串
     * param @param key
     * param @return
     * @return String    返回类型
     * throws
     */
    public static String read(String key){
        return TApplication.dictionaryService.getValue(key);
    }
}
