package com.yunxiang.shopkeeper;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.WindowManager;

import com.yunxiang.shopkeeper.model.Active;
import com.yunxiang.shopkeeper.model.Category;
import com.yunxiang.shopkeeper.model.Charge;
import com.yunxiang.shopkeeper.model.Merchandise;
import com.yunxiang.shopkeeper.model.Order;
import com.yunxiang.shopkeeper.model.Packet;
import com.yunxiang.shopkeeper.model.PacketBatch;
import com.yunxiang.shopkeeper.model.Shop;
import com.yunxiang.shopkeeper.model.ShopCustomer;
import com.yunxiang.shopkeeper.model.User;
import com.yunxiang.shopkeeper.model.UserMessage;
import com.yunxiang.shopkeeper.service.impl.CategoryService;
import com.yunxiang.shopkeeper.service.impl.DictionaryService;
import com.yunxiang.shopkeeper.service.impl.ShopService;
import com.yunxiang.shopkeeper.service.impl.UserService;
import com.yunxiang.shopkeeper.utils.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * auther:jiely
 * create at 2015/10/14 14:32
 */
public class TApplication extends Application {

    public static User user;
    public static Context context;
    public static Charge charge;
    public static SparseArray<String> map = new SparseArray<String>();
    public static DictionaryService dictionaryService;
    public static DisplayMetrics displayMetrics;
    public static Resources resources;
    public static Shop shop;
    public static int versionType = Const.TEST_VERTION;  //0-text 1-debug, 2-release
    public static String URL_STRING;
    public static List<Category> categories;
    public static LongSparseArray<List<Merchandise>> merchandiseArray;
    public static double orderAmount;           //订单总额
    public static Order order;
    //不同类的订单列表 10，今日订单  0，新订单 2,已接单 4，已完成 6，已取消
    public static LongSparseArray<List<Order>> orderArrays = new LongSparseArray<List<Order>>();
    public static List<PacketBatch> packetBatchList;// = new ArrayList<PacketBatch>();
    public static List<PacketBatch> couponBatchList;// = new ArrayList<PacketBatch>();
    public static List<Packet> packets;// = new ArrayList<Packet>();
    public static List<UserMessage> userMessageList;
    public static List<ShopCustomer> shopCustomerList;// = new ArrayList<ShopCustomer>();  //到店顾客，选发送红包时用到
    public static List<ShopCustomer> selectedCustomers = new ArrayList<ShopCustomer>();     //到店顾客，选发送红包时用到已选择客户
    public static List<Active> activeList;
   public  static String CHANNEL="alipay";//支付的渠道

    @Override
    public void onCreate() {
        super.onCreate();

        switch (versionType){
            case Const.TEST_VERTION:
                URL_STRING ="http://192.168.1.6/yunxiang-seller/";
                break;
            case Const.DEBUG_VERTION:
                URL_STRING = "http://192.168.1.11/yunxiang-seller/";
                break;
            case Const.RELEASE_VERTION:
                URL_STRING = "http://122.115.50.71/yunxiang-seller/";
                break;
            case Const.TEST_VERTION1:
                URL_STRING ="http://192.168.1.19/yunxiang-seller/";
                break;
        }

        context = getApplicationContext();


        dictionaryService = DictionaryService.getInstanse();

        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = new DisplayMetrics();
        //WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(displayMetrics);

        resources = getResources();

        user = UserService.getInstanse().getUser();
        shop = ShopService.getInstanse().getShop();

        categories = CategoryService.getInstanse().getAll();

    }
}
