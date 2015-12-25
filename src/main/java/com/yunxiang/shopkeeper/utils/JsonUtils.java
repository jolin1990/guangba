package com.yunxiang.shopkeeper.utils;

import android.util.Log;

import com.yunxiang.shopkeeper.model.Active;
import com.yunxiang.shopkeeper.model.Address;
import com.yunxiang.shopkeeper.model.AppVersion;
import com.yunxiang.shopkeeper.model.Category;
import com.yunxiang.shopkeeper.model.Charge;
import com.yunxiang.shopkeeper.model.Customer;
import com.yunxiang.shopkeeper.model.Merchandise;
import com.yunxiang.shopkeeper.model.Order;
import com.yunxiang.shopkeeper.model.Packet;
import com.yunxiang.shopkeeper.model.PacketBatch;
import com.yunxiang.shopkeeper.model.Shop;
import com.yunxiang.shopkeeper.model.ShopCustomer;
import com.yunxiang.shopkeeper.model.User;
import com.yunxiang.shopkeeper.model.UserMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @desc: Created by jiely on 2015/10/15.
 */
public class JsonUtils {


//    //利用Gson实现对象序列化为Json
//    public static String toJson(Object object) {
//        GsonBuilder builder = new GsonBuilder();
//        // 不转换没有 @Expose 注解的字段
//        builder.excludeFieldsWithoutExposeAnnotation();
//        //对Date类型进行注册事件
//        builder.registerTypeAdapter(Date.class, new UtilDateSerializer());
//        Gson gson = builder.create();
//        return gson.toJson(object);
//    }
//
//    static class UtilDateSerializer implements JsonSerializer<Date> {
//
//        @Override
//        public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
//            return new JsonPrimitive("/Date(" + date.getTime()+ "+0800)/");
//        }
//    }

    /**
     * param  jsonString
     * return String    返回类型
     * author：jiely
     * Date：2015-2-5
     * Title: getToken
     * Description: 从后台获取Token
     */
    public static boolean getIsLogin(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.toString() == null || jsonObject.toString().length() == 0) {
                return false;
            }
            return jsonObject.getBoolean("isLogin");
        } catch (JSONException e) {
            ExceptionUtil.handle(e);
        }
        return false;
    }

    /**
     * param  jsonString
     * return String    返回类型
     * author：jiely
     * Date：2015-2-5
     * Title: getToken
     * Description: 从后台获取Token
     */
    public static boolean getStatus(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.toString() == null || jsonObject.toString().length() == 0) {
                return false;
            }
            return jsonObject.getBoolean("status");
        } catch (JSONException e) {
            ExceptionUtil.handle(e);
        }
        return false;
    }

    public static Boolean getBooleanStatus(String jsonString) {
        try {
            //jsonString = "{\"status\":false,\"description\":\"用户不存在\"}";
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.toString() == null || jsonObject.toString().length() == 0) {
                return null;
            }
            return jsonObject.getBoolean("status");
        } catch (JSONException e) {
            ExceptionUtil.handle(e);
        }
        return null;
    }

    /**
     * desc:
     * auther:jiely
     * create at 2015/12/1 15:47
     */
    public static long getImageId(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.toString() == null || jsonObject.toString().length() == 0) {
                return 0;
            }
            return jsonObject.getLong("fileEntryId");
        } catch (JSONException e) {
            ExceptionUtil.handle(e);
        }
        return 0;
    }

    public static String getDescription(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.toString() == null || jsonObject.toString().length() == 0) {
                return null;
            }
            String desc = jsonObject.optString("description");
            return desc;
        } catch (JSONException e) {
            ExceptionUtil.handle(e);
        }
        return null;
    }


    //获取当日支付总额
    public static double getAmount(String jsonString) {
        try {
            //jsonString = "{\"status\":false,\"description\":\"用户不存在\"}";
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.toString() == null || jsonObject.toString().length() == 0) {
                return 0;
            }
            return jsonObject.getDouble("sumPrice");
        } catch (JSONException e) {
            ExceptionUtil.handle(e);
        }
        return 0;
    }

    public static User getUser(String jsonString){
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject userObj = jsonObject.optJSONObject("user");
            user = new User();
            user.setUserId(userObj.optLong("userId"));
            user.setUserName(userObj.optString("userName"));
            user.setEmailAddress(userObj.optString("emailAddress"));
            user.setStatus(userObj.optInt("status"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static long getShopId(String jsonString) {
        try {
            //jsonString = "{\"status\":false,\"description\":\"用户不存在\"}";
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.toString() == null || jsonObject.toString().length() == 0) {
                return 0;
            }
            return jsonObject.getLong("shopId");
        } catch (JSONException e) {
            ExceptionUtil.handle(e);
        }
        return 0;
    }

    public static Shop getShopByUser(String jsonString){
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONObject userObj = jsonObject.optJSONObject("user");
            JSONObject shopJson = userObj.optJSONObject("shop");
            if(shopJson != null){
                return parseShop(shopJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Shop getShop(String jsonString){
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONObject shopJson = jsonObject.optJSONObject("shop");
            if(shopJson != null){
                return parseShop(shopJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Shop parseShop(JSONObject shopJson){
        Shop shop=new Shop();
        shop.setShopPhone(shopJson.optString("shopPhone"));
        shop.setSmallImageURL(shopJson.optString("smallImageURL"));
        shop.setAddress(shopJson.optString("street"));
        shop.setShopName(shopJson.optString("shopName"));
        shop.setShopId(shopJson.optInt("shopId"));
        shop.setDescribe(shopJson.optString("description"));
        shop.setTypeId(shopJson.optInt("typeId"));
        shop.setMasterName(shopJson.optString("masterName"));
        shop.setMasterPhone(shopJson.optString("masterPhone"));
        shop.setDescribe(shopJson.optString("description"));
        shop.setOpeningHours(shopJson.optString("openingHours"));
        String bannerUrls = shopJson.optString("bannerImages");
        long[] imgIds = StringUtils.splitCommaLong(bannerUrls);
        shop.setBannerIds(imgIds);
        return shop;
    }


    //解析红包记录的列表
    public static List<PacketBatch> getPacketBatchList(String result){
        List<PacketBatch> list=new ArrayList<PacketBatch>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray = jsonObject.optJSONArray("couponList");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonList = (JSONObject) jsonArray.get(i);
                PacketBatch packetBatch =new PacketBatch();
                Double balance = jsonList.optDouble("balance");//金额
                int amount = balance.intValue();
                packetBatch.setAmountMoney(String.valueOf(amount));
                packetBatch.setPatchId(Long.parseLong(jsonList.getString("batchId")));
                packetBatch.setTotalCount(jsonList.optString("count"));//总数
                packetBatch.setName(jsonList.optString("name"));        //红包名称
                balance = jsonList.optDouble("limit");                  //红包限额
                amount = balance.intValue();
                packetBatch.setMoneyLimit(String.valueOf(amount));
                packetBatch.setStartDate(jsonList.optString("startDate"));
                packetBatch.setStopDate(jsonList.optString("stopDate"));
                packetBatch.setCreateDate(jsonList.optString("createTime"));//红包创建时间
                packetBatch.setReceiveCount(jsonList.optString("used"));//红包已领个数
                packetBatch.setStatus(jsonList.optInt("active"));       //红包状态
                packetBatch.setType(jsonList.optInt("type"));
                packetBatch.setDesc(jsonList.optString("description"));
                list.add(packetBatch);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //解析红包记录的列表
    public static List<Packet> getPacketList(String result){
        List<Packet> list=new ArrayList<Packet>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray = jsonObject.optJSONArray("couponList");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonList = (JSONObject) jsonArray.get(i);
                Packet packet =pasePacket(jsonList);
                list.add(packet);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    private static Packet pasePacket(JSONObject jsonObjectItem){
        Packet packet = new Packet();
        packet.setUpdateTime(jsonObjectItem.optString("updateTime"));
        packet.setCustomerName(jsonObjectItem.optString("userName"));
        packet.setHeadImgId(jsonObjectItem.optInt("headImgId"));
        return packet;
    }

    //获取商品的类别的列表
    public static List<Category> getCategories(String result) {
        List<Category> list = new ArrayList<Category>();
        try {
            JSONObject ob = new JSONObject(result);
            JSONArray jsonArray = ob.optJSONArray("categoryList");
            Category category = new Category();
            category.setCategoryId(0);
            category.setName("推荐");
            list.add(category);
            if(jsonArray == null){
                return list;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject js = (JSONObject) jsonArray.get(i);
                category = new Category();
                category.setCategoryId(js.optInt("categoryId"));
                category.setName(js.optString("name"));
                list.add(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取商品
    public static Merchandise getMerchandise(String result){
        Merchandise merchandise = null;
        try {
            JSONObject jsonObject=new JSONObject(result);
            jsonObject = jsonObject.optJSONObject("shopItem");
            merchandise = parseMerchandise(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return merchandise;
    }

    //获取商品的列表
    public static  List<Merchandise> getMerchandises(String result){
        Log.d("TAG", "result=" + result);
        List<Merchandise> list=new ArrayList<Merchandise>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray itemList = jsonObject.optJSONArray("itemList");

            for (int i=0;i<itemList.length();i++){
                JSONObject jsonObjectItem = (JSONObject) itemList.get(i);
                Merchandise merchandise = parseMerchandise(jsonObjectItem);
                list.add(merchandise);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Merchandise parseMerchandise(JSONObject jsonObjectItem){
        int type = jsonObjectItem.optInt("featured_");
        boolean yesOrNo = (type==1);
        Merchandise merchandise =new Merchandise();
        merchandise.setIsRecommend(yesOrNo);
        type = jsonObjectItem.optInt("stockQuantity");
        yesOrNo = (type==1);
        merchandise.setIsSoldOut(yesOrNo);
        merchandise.setCategoryId(jsonObjectItem.optInt("categoryId"));
        merchandise.setPrice(jsonObjectItem.optDouble("price"));
        merchandise.setSmallImageURL(jsonObjectItem.optString("smallImageURL"));
        merchandise.setName(jsonObjectItem.optString("name"));
        merchandise.setId(jsonObjectItem.optInt("itemId"));
        return merchandise;
    }

    //商铺的订单列表
    public static List<Order> getShopOrders(String result){
        List<Order> list=new ArrayList<Order>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray orderList = jsonObject.optJSONArray("orderList");
            if(orderList == null){
                return list;
            }
            for (int i=0;i<orderList.length();i++){
                Order order=new Order();
                JSONObject jsonObjectList= (JSONObject) orderList.get(i);
                order.setOrderId(jsonObjectList.optLong("orderId"));
                order.setPrice(jsonObjectList.optDouble("price"));
                order.setDiscount(jsonObjectList.optDouble("discount"));
                order.setImgUrl(jsonObjectList.optString("headImgUrl"));
                order.setCreatDate(jsonObjectList.optString("createDate"));
                order.setSerialNumber(jsonObjectList.optString("serialNumber"));
                order.setStatus(jsonObjectList.optInt("orderStatus"));
                JSONObject jsonObject1= jsonObjectList.optJSONObject("address");
                if(jsonObject1 != null){
                    Address address = getAddress(jsonObject1);
                    order.setAddress(address);
                }
                list.add(order);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //选择老客户的客户列表
    public static List<ShopCustomer> getShopCustomerList(String result){
        List<ShopCustomer> list=new ArrayList<ShopCustomer>();
        try {

            JSONObject jsonObject=new JSONObject(result);
            JSONArray customerList = jsonObject.optJSONArray("customerList");
            for (int i=0;i<customerList.length();i++){
                JSONObject jsonobject= (JSONObject) customerList.get(i);
                ShopCustomer customer = parseShopCustomer(jsonobject);

                list.add(customer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //用户留言列表
    public static List<UserMessage> getUserMessages(String result){
        List<UserMessage> list=new ArrayList<UserMessage>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray messageList = jsonObject.optJSONArray("messageList");
            if(messageList == null){
                return list;
            }
            for(int i=0;i<messageList.length();i++){
                JSONObject jsonObject1= (JSONObject) messageList.get(i);
                UserMessage userMessage=new UserMessage();
                userMessage.setImgId(jsonObject1.optString("headImgId"));
                userMessage.setCreateDate(jsonObject1.optString("createDate"));
                userMessage.setCustomerName(jsonObject1.optString("customerName"));
                userMessage.setContent(jsonObject1.optString("content"));
                list.add(userMessage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取客户信息
    public static Customer getCustomer(String result){
        Customer customer=null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonCustomer = jsonObject.optJSONObject("shopcustomer");
            if(jsonCustomer == null){
                return null;
            }
            customer = parseCustomer(jsonCustomer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return customer;
    }


    //用户到店消费列表
    public static  List<Customer> getCustomers(String result){
        List<Customer> list= new ArrayList<Customer>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            list=new ArrayList<Customer>();
            JSONArray orderList = jsonObject.optJSONArray("customerList");
            if(orderList == null){
                return list;
            }
            for (int i=0;i<orderList.length();i++){
               JSONObject jsonObjectList= (JSONObject) orderList.get(i);
                Customer customer = parseCustomer(jsonObjectList);
                list.add(customer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //店铺的活动
    public static List<Active> getActives(String result){
        List<Active> list=new ArrayList<Active>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray = jsonObject.optJSONArray("activityList");
            if(jsonArray == null){
                return list;
            }
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1= (JSONObject) jsonArray.get(i);
                Active active = parseAct(jsonObject1);
                list.add(active);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    //店铺活动的详情
    public static Active getActive(String result){
        Active active=new Active();
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONObject activity = jsonObject.optJSONObject("activity");
            if(activity == null){
                return null;
            }
            active = parseAct(activity);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return active;
    }

    //收货地址  "addressId": 1,"telPhone": "18720095146",zipCode": 336000 "street": "",
    private static Address getAddress(JSONObject jsonObject) {
        Address address = new Address();
        address.setAddressId(jsonObject.optLong("addressId"));
        address.setPhone(jsonObject.optString("telPhone"));
        address.setZipCode(jsonObject.optString("zipCode"));
        address.setStreet(jsonObject.optString("street"));
        address.setName(jsonObject.optString("name"));

        return address;
    }
   //活动
    private static Active parseAct(JSONObject jsonObject){
        Active active=new Active();
        active.setStopDate(jsonObject.optString("stopDate"));
        active.setActiveName(jsonObject.optString("activityName"));
        active.setDescription(jsonObject.optString("description"));
        active.setImgUrl(jsonObject.optString("activityImgURL"));
        active.setStartDate(jsonObject.optString("startDate"));
        active.setActiveId(jsonObject.optInt("activityId"));
        active.setIsOverdate(jsonObject.optBoolean("active_"));
        return active;
    }

    //顾客
    private static Customer parseCustomer(JSONObject jsonObject){
        Customer customer=new Customer();
        customer.setName(jsonObject.optString("customerName"));
        return customer;
    }

    //顾客  商店顾客，发红包
    private static ShopCustomer parseShopCustomer(JSONObject jsonObject){
        ShopCustomer customer=new ShopCustomer();
        customer.setCustomerName(jsonObject.optString("customerName"));
        customer.setHeadImgId(jsonObject.optInt("headImgId"));
        customer.setCustomerId(jsonObject.optLong("customerId"));
        customer.setUpdateTime(jsonObject.optString("updateTime"));
        customer.setCreateTime(jsonObject.optString("createTime"));
        customer.setConsumption(jsonObject.optDouble("consumption"));
        customer.setTimes(jsonObject.optInt("times"));
        customer.setIsTakeOut(jsonObject.optBoolean("isTakeOut"));
        return customer;
    }


    /**
     * desc:从后台获取APP版本信息
     * auther:jiely
     * create at 2015/10/10 19:52
     */
    public static AppVersion getAppVersion(String jsonString) {
        AppVersion appVersion = new AppVersion();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonversion = jsonObject.optJSONObject("version");
            int versionId = jsonversion.optInt("versionId");
            appVersion.setId(versionId);
            String strVersion = jsonversion.optString("version");
            appVersion.setVersion(strVersion);
            String url = jsonversion.optString("url");
            appVersion.setUrl(url);
            String createDate = jsonversion.optString("createDate");
            appVersion.setCreateDateString(createDate);
            String des = jsonversion.optString("desc");
            appVersion.setDesc(des);
        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
        return appVersion;
    }


    public static Charge getCharge(String result){
        try {
            JSONObject jsonObject=new JSONObject(result);
            Charge charge=new Charge();
            charge.setId(jsonObject.optString("id"));
//            charge.setOrderNo(jsonObject.optString("order_no"));
            return charge;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
