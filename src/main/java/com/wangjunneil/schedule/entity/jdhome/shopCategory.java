package com.wangjunneil.schedule.entity.jdhome;

/**
 * @author yangyongbing
 * @since 2016/11/15.
 */
public class ShopCategory {
    Long id;//店内分类id
    Long pid;//父分类ID
    String shopCategoryName;
    Integer shopCategoryLevel;
    Integer sort;
    String createPin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    public Integer getShopCategoryLevel() {
        return shopCategoryLevel;
    }

    public void setShopCategoryLevel(Integer shopCategoryLevel) {
        this.shopCategoryLevel = shopCategoryLevel;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCreatePin() {
        return createPin;
    }

    public void setCreatePin(String createPin) {
        this.createPin = createPin;
    }
}
