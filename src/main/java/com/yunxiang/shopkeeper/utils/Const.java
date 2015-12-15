package com.yunxiang.shopkeeper.utils;

import android.os.Environment;

import com.yunxiang.shopkeeper.TApplication;

import java.io.File;

/**
 * desc: Created by jiely on 2015/9/1.
 */
public class Const {

    //sqlite
    public static final int    TEST_VERTION = 1;
    public static final int    TEST_VERTION1 = 4;
    public static final int    DEBUG_VERTION = 2;
    public static final int    RELEASE_VERTION =3;
    public static final int    SQLITE_VERTION = 1;                  //数据库版本
    public static final String SQLITE_DBNAME = "shopkeeper";		//数据库名

    //url
    //public static final String URL_STRING = "http://192.168.1.11:8080/laiba-app/";
    //public static final String URL_STRING = "http://192.168.1.25/laiba-app/";


    //发送消息
    public static final int MSG_SUCCESS = 3;
    public static final int MSG_FAILURE = 4;
    public static final int MSG_RECEIVE_PACKET_SUCCESS = 1;
    public static final int MSG_USED_PACKET_SUCCESS = 2;
    public static final int MSG_RECOMMEND_SUCCESS = 11;
    public static final int MSG_SOLDOUT_SUCCESS = 21;
    public static final int MSG_UPDATE_CATEGORY_SUCCESS =12 ;
    public static final int MSG_DELETE_CATEGORY_SUCCESS =13 ;
    public static final int MSG_ADD_CATEGORY_SUCCESS =14 ;
    public static final int MSG_OBTAIN_MERCHANDISE_SUCCESS =15;
    public static final int MSG_ADD_MERCHANDISE_SUCCESS =16 ;
    public static final int MSG_DOWNLOAD_PROGRESS = 1;
    public static final int MSG_DOWNLOAD_OK = 2;
    public static final int MSG_ORDER_REFUSE_SUCCESS=21;//拒单成功
    public static final int MSG_ORDER_RECEIVE_SUCCESS=22;//接单成功
    public static final int MSG_ORDER_DELIVER_SUCCESS=23;//送货
    public static final int MSG_PACKET_SUCCESS = 24;//获取普通红包
    public static final int MSG_COUPONS_SUCCESS = 25;//获取抵用券红包
    public static final int MSG_INSERT_SHOP_SUCCESS = 26;
    public static final int MSG_UPDATE_SHOP_SUCCESS = 27;
    public static final int MSG_INSERT_DELIVER_SUCCESS = 28;
    public static final int MSG_UPDATE_DELIVER_SUCCESS = 29;
    //获取商品类别列表成功
    public static final int MSG_SHOPITEM_CLASS_LIST = 8;
    //得到sdcard的root
    public static final File SDCARD_ROOT= Environment.getExternalStorageDirectory();//.getAbsolutePath();
    //public static final String PATH_SHOP_PICTURE = SDCARD_ROOT  +"/shopkeeper/spanner/";
    public static final String NAME_SUFFIX = ".jpg";  //文件后缀名



    public static String UPDATE_APK_PATH = SDCARD_ROOT+"/shopkeeper/apkDownload/";
    public static String PATH_SHOP_BANNER = SDCARD_ROOT+"/shopkeeper/shopBannerImage/";
    public static String PATH_SHOP_IMAGE = SDCARD_ROOT+"/shopkeeper/shopImage/";
    //检查登录接口
    public static final String URL_IS_LOGIN = TApplication.URL_STRING+"user/isLogin";
    //退出登录接口
    public static final String URL_LOGIN_OUT = TApplication.URL_STRING+"user/logout";
    //登录注册接口
    public static final String URL_LOGIN = TApplication.URL_STRING+"user/login?userName=";
    //修改注册密码
    public static final String URL_CHANGE_PSW = TApplication.URL_STRING+"user/changePwd?userName=";
    //获取验证码接口
    public static final String URL_GET_VERIFY_CODE = TApplication.URL_STRING+"login/getCaptcha?userName=";
    public static final String URL_REGISTER_SHOP = TApplication.URL_STRING+"shop/newShop";
    //发送红包优惠券的接口
    public static final String URL_SEND_PARKET = TApplication.URL_STRING+"shop/coupon";
    //发送红包优惠券的接口
    public static final String URL_SEND_PRIVATE_PARKET = TApplication.URL_STRING+"shop/privateCoupon";
    //发送红包\优惠券记录
    public static final String URL_PACKET_RECORD = TApplication.URL_STRING+"shop/coupon/record?shopId=";
    //获取红包\优惠券的批次记录
    public static final String URL_GET_PACKET_RECORD = TApplication.URL_STRING+"shop/coupon/getRecordCouponList";
    //获取红包或优惠券的使用记录-根据批次
    public static final String URL_GET_USED_PACKET_BY_BATCH = TApplication.URL_STRING+"shop/coupon/getBatchCouponUseList?batchId=";
    //获取红包或优惠券的领取记录-根据批次
    public static final String URL_GET_RECEIVE_PACKET_BY_BATCH = TApplication.URL_STRING+"shop/coupon/getBatchCouponReceiveList?batchId=";
    //获取红包或优惠券-发送的客户列表
    public static final String URL_GET_CUSTOMERS = TApplication.URL_STRING+"shopcustomer/getCustomerList";
    //商品类别的列表
    public static final String URL_GET_SHOPITEM_CLASS_LIST = TApplication.URL_STRING+"category?shopId=";
    //删除商品类别
    public static final String URL_DELETE_CATEGORY = TApplication.URL_STRING+"category/";
    //添加分类
    public static final String URL_ADD_CLASS = TApplication.URL_STRING+"category/newCategory";
    //修改商品类别
    public static final String URL_UPDATE_CLASS = TApplication.URL_STRING+"category/update";
    //根据类别获取商品列表
    public static final String URL_SHOP_ITEM_LIST = TApplication.URL_STRING+"shop/items?";
   //修改商品是否下架或是否推荐（传不同的参数）
    public static final String URL_UPDATE_SHOPITME_ISSEAL = TApplication.URL_STRING+"shop/items/feature";
    //店铺的订单
    public static final String URL_SHOP_ORDERS = TApplication.URL_STRING+"order/getOrderList?page=";
    //客户列表
    public static final String URL_CUSTOMER_LIST=TApplication.URL_STRING+"shopcustomer/customerList?shopId=";
    //用户留言列表
    public static final String URL_USER_MESSAGE_LIST=TApplication.URL_STRING+"message/getMessageList?type=0";
    //用户消费记录
    public static final String URL_USER_ORDER=TApplication.URL_STRING+"shopcustomer/userOrder?customerId=";
    //店铺信息
    public static final String URL_SHOP_INFO=TApplication.URL_STRING+"shop/detail/";
    //修改店铺信息
    public static final String URL_UPDATA_SHOP_INFO=TApplication.URL_STRING+"upload";
   //店铺活动列表
    public static final String URL_GET_SHOP_ACTIVITY=TApplication.URL_STRING+"shop/activity/getList?shopId=";
    //店铺活动详情
    public static final String URL_SHOP_ACTIVITY_DETAIL=TApplication.URL_STRING+"shop/activity/getActivity?activityId=";
    //添加、修改商品
    public static final String URL_ADD_OR_UPDATA_SHOPITEM=TApplication.URL_STRING+"upload";
    //获取最新版本
    public static final String URL_SHOPKEEPER_APP_VERSION = TApplication.URL_STRING+"version/getSellerLastVersion";
    //订单拒单原因提交
    public static final String URL_ORDER_REFUSE = TApplication.URL_STRING+"order/shopToday?shopId=";
    //订单接单
    public static final String URL_ORDER_RECEIVE = TApplication.URL_STRING+"order/shopToday?shopId=";
    //订单送货
    public static final String URL_ORDER_DELIVER = TApplication.URL_STRING+"order/shopToday?shopId=";
    //图片地址
    public static final String URL_PICTURE_PATH = TApplication.URL_STRING+"getPictureFile?fileEntryId=";
    //public static final String URL_IMG1 = TApplication.URL_STRING+"getPictureFile?fileEntryId=";
    //充值
    public static final String URL_RECHARGE=TApplication.URL_STRING+"recharge/new";
    //修改充值状态
    public static final String URL_UPDATA_RECHARGE=TApplication.URL_STRING+"recharge/update";
    //字典
    public static final String VAL_SPANNER_MODE = "spanner_mode";
    public static final String VAL_SPANNER_PIC = "spanner_pic";
    public static final String PATH_SHOP_PICTURE = SDCARD_ROOT+"/shopkeeper/shopping/";

    //红包类型
    public static final int PACKET_COMMON = 0;//"common";//普通红包
    public static final int PACKET_FIGHT_LUCK = 1;//"fightLuck";//拼手气红包
    public static final int PACKET_VOUCHERS = 3;//"vouchers";//代金券
    public static final int PACKET_DISCOUNT_COUPON = 2;//"discountcoupons";//折扣券

    //缓存 键值对 key value
    public static final int MAP_KEY_NAME = 1;  //优惠券的类名 "抵用券" or "折扣券"
    public static final int MAP_KEY_SLECT = 2;
    public static final int MAP_KEY_ACITIVITY = 3;
    public static final String MAP_VAL_VOUCHERS = "抵用券";
    public static final String MAP_VAL_DISCOUNT = "折扣券";
    public static final String MAP_VAL_CROWD_POCKET = "群红包";
    public static final String MAP_VAL_PERSONAL_POCKET = "个人红包";
    public static final String MAP_VAL_SELECTED = "selected";
    public static final String MAP_VAL_SELECT_ALL = "selectedall";

}
