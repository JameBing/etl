package com.wangjunneil.schedule.entity.sys;

import java.util.List;

/**
 * Created by wangjun on 7/28/16.
 */
public class Menu {
    private String menuName;
    private String menuAction;
    private String menuIcon;
    private List<Menu> menus;

    public Menu() { }

    public Menu(String menuName, String menuAction) {
        this.menuName = menuName;
        this.menuAction = menuAction;
    }

    public Menu(String menuName, String menuAction, String menuIcon) {
        this.menuAction = menuAction;
        this.menuName = menuName;
        this.menuIcon = menuIcon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuAction() {
        return menuAction;
    }

    public void setMenuAction(String menuAction) {
        this.menuAction = menuAction;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenu(List<Menu> menus) {
        this.menus = menus;
    }
}
