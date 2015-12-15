package com.yunxiang.shopkeeper.model;

/**
 *  配送类 Created by jiely on 2015/11/26.
 */
public class Deliver {
    private String deliverFee;//配送费
    private String pockFee;   //包装费
    private String sendingFee;//起送费
    private boolean isInvoice;//是否开具发票
    private boolean isCOD;//是否支持货到付款
    private boolean isCallingMan;//是否叫配送员

    public String getDeliverFee() {
        return deliverFee;
    }

    public void setDeliverFee(String deliverFee) {
        this.deliverFee = deliverFee;
    }

    public String getPockFee() {
        return pockFee;
    }

    public void setPockFee(String pockFee) {
        this.pockFee = pockFee;
    }

    public String getSendingFee() {
        return sendingFee;
    }

    public void setSendingFee(String sendingFee) {
        this.sendingFee = sendingFee;
    }

    public boolean isInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    public boolean isCOD() {
        return isCOD;
    }

    public void setIsCOD(boolean isCOD) {
        this.isCOD = isCOD;
    }

    public boolean isCallingMan() {
        return isCallingMan;
    }

    public void setIsCallingMan(boolean isCallingMan) {
        this.isCallingMan = isCallingMan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deliver deliver = (Deliver) o;

        if (isInvoice != deliver.isInvoice) return false;
        if (isCOD != deliver.isCOD) return false;
        if (isCallingMan != deliver.isCallingMan) return false;
        if (deliverFee != null ? !deliverFee.equals(deliver.deliverFee) : deliver.deliverFee != null)
            return false;
        if (pockFee != null ? !pockFee.equals(deliver.pockFee) : deliver.pockFee != null)
            return false;
        return !(sendingFee != null ? !sendingFee.equals(deliver.sendingFee) : deliver.sendingFee != null);

    }

    @Override
    public int hashCode() {
        int result = deliverFee != null ? deliverFee.hashCode() : 0;
        result = 31 * result + (pockFee != null ? pockFee.hashCode() : 0);
        result = 31 * result + (sendingFee != null ? sendingFee.hashCode() : 0);
        result = 31 * result + (isInvoice ? 1 : 0);
        result = 31 * result + (isCOD ? 1 : 0);
        result = 31 * result + (isCallingMan ? 1 : 0);
        return result;
    }
}
