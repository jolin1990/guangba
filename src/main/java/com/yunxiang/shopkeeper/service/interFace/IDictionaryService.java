package com.yunxiang.shopkeeper.service.interFace;


/**
 * ************************************************************************************************************
 * @author：jiely
 * Date：2015-2-12
 * Description: 字符串表的业务接口
 * Company：中交讯通
 * @version 0.1 抛出异常是为了在调试代码时能定位到显示层
 **************************************************************************************************************
 */
public interface IDictionaryService {
	
	
	/**
	 * 
	* @author：jiely
	* @Date：2015-2-12
	* @Title: modify 
	* @Description: 通过key查询一条记录并修改这条记录的值
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void modify(String key, String value)  ;
	
	/**
	 * 
	* @author：jiely
	* @Date：2015-2-12
	* @Title: getValue 
	* @Description:  通过key获取value
	* @param @return    设定文件 
	* @return User    返回类型 
	* @throws
	 */
	public String getValue(String key)  ;
	
	/**
	 * 
	* @author：jiely
	* @Date：2015-2-12
	* @Title: addValue 
	* @Description: 在表中新增一条键值对
	* @param @param key
	* @param @param value
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void addValue(String key, String value)  ;
}
