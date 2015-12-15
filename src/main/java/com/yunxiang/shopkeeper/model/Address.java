package com.yunxiang.shopkeeper.model;

/**
 * "addressId": 1,"telPhone": "18720095146",zipCode": 336000 "street": "",
 * desc: Created by jiely on 2015/12/2.
 */
public class Address {
    private long addressId;
    private String name;
    private String street;
    private String phone;
    private String zipCode;

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
