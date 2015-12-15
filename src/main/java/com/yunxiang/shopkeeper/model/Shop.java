package com.yunxiang.shopkeeper.model;



/**
 * @desc: Created by jiely on 2015/10/21.
 */
public class Shop{
    private long shopId;
    private String shopName;
    private String MasterName;    //负责人姓名
    private String MasterPhone;   //负责人电话
    private String smallImageURL; //商铺头像
    private String shopPhone;     //店铺电话
    private String payPsw;        //支付密码
    private String province;      //百度地图的省市信息
    private String city;          //百度地图的省市信息
    private String address;       //店铺地址
    private String openingHours;  //店铺营业时间
    private String describe;
    private int typeId;            //店铺类型
    private long[] bannerIds;       //轮播图ID数组
    private double longitude;       //经纬度
    private double latitude;

    private Deliver deliver;//配送属性

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public Deliver getDeliver() {
        return deliver;
    }

    public void setDeliver(Deliver deliver) {
        this.deliver = deliver;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getMasterName() {
        return MasterName;
    }

    public void setMasterName(String masterName) {
        MasterName = masterName;
    }

    public String getMasterPhone() {
        return MasterPhone;
    }

    public void setMasterPhone(String masterPhone) {
        MasterPhone = masterPhone;
    }

    public String getPayPsw() {
        return payPsw;
    }

    public void setPayPsw(String payPsw) {
        this.payPsw = payPsw;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long[] getBannerIds() {
        return bannerIds;
    }

    public void setBannerIds(long[] bannerIds) {
        this.bannerIds = bannerIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (typeId != shop.typeId) return false;
        if (!shopName.equals(shop.shopName)) return false;
        if (!MasterName.equals(shop.MasterName)) return false;
        if (!MasterPhone.equals(shop.MasterPhone)) return false;
        if (!address.equals(shop.address)) return false;
        return describe.equals(shop.describe);

    }

    @Override
    public int hashCode() {
        int result = shopName.hashCode();
        result = 31 * result + MasterName.hashCode();
        result = 31 * result + MasterPhone.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + describe.hashCode();
        result = 31 * result + typeId;
        return result;
    }


}
