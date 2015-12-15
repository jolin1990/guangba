package com.yunxiang.shopkeeper.service.interFace;

import com.yunxiang.shopkeeper.model.Shop;

/**
 * ************************************************************************************************************
 * @author：jiely
 * Date：2015-2-12
 * Description: User表的业务接口
 * Company：中交讯通
 * @version 0.1 抛出异常是为了在调试代码时能定位到显示层
 **************************************************************************************************************
 */
public interface IShopService {
	
	/**
	 *
	* Title: clear
	* Description: 清除User表 ,用于退出登录

	 */
	public void clear();
	
	/**
	 * 
	* author：jiely
	* Title: refresh
	* Description: 刷新表，从后台获取数据对表进行清除和更新
	* throws
	 */
	public void refresh(Shop shop);
	
	/**
	* Title: getUser
	* Description:  查询user表用户数据，用户唯一
	 */
	public Shop getShop();
	
	/**
	 *
	* Title: modify
	* Description: 更新用户数据
	 */
	public void modify(Shop shop);
}
