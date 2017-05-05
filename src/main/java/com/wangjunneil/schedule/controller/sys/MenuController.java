package com.wangjunneil.schedule.controller.sys;

import com.alibaba.fastjson.JSON;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.jd.JdAccessToken;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Menu;
import com.wangjunneil.schedule.entity.tm.TmallAccessToken;
import com.wangjunneil.schedule.entity.z8.Z8AccessToken;
import com.wangjunneil.schedule.service.SysFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by wangjun on 7/28/16.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private SysFacadeService sysFacadeService;

    @RequestMapping(value = "/policy.php", method = RequestMethod.GET)
    public String getToken(PrintWriter out, HttpServletResponse resp, HttpServletRequest req) {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=UTF-8");

        // 菜单链定义
        List<Menu> menus = new ArrayList<>();
        // 查询所有配置项
        List<Cfg> cfgs = sysFacadeService.findAllCfg();
        // 若有任何配置信息
        if (cfgs.size() > 0) {
            Cfg jdCfg = getPlatformCfg(cfgs, Constants.PLATFORM_JD);
            if (jdCfg != null) {
                Menu jdMenu = adJustJdMenu(jdCfg);   // 京东菜单
                menus.add(jdMenu);
            }

            Cfg tmCfg = getPlatformCfg(cfgs, Constants.PLATFORM_TM);
            if (tmCfg != null) {
                Menu tmallMenu = adJustTmallMenu(tmCfg); // 天猫菜单
                menus.add(tmallMenu);
            }

            Cfg jpCfg = getPlatformCfg(cfgs, Constants.PLATFORM_JP);
            if (jpCfg != null) {
                Menu jpMenu = adJustJPMenu(jpCfg); // 卷皮菜单
                menus.add(jpMenu);
            }

            Cfg z8Cfg = getPlatformCfg(cfgs, Constants.PLATFORM_Z800);
            if(z8Cfg != null){
                Menu z8Menu = adJustZ8Menu(z8Cfg);
                menus.add(z8Menu);
            }

            Cfg elemeCfg = getPlatformCfg(cfgs, Constants.PLATFORM_WAIMAI_ELEME);
            if(elemeCfg != null){
                Menu elemeMenu = adJustElemeMenu(elemeCfg);  //饿了么菜单
                menus.add(elemeMenu);
            }

            Menu dashboardMenu = new Menu();    // 主控制面板
            dashboardMenu.setMenuName("数据中心");
            dashboardMenu.setMenuAction("#/");
            dashboardMenu.setMenuIcon("fa fa-dashboard");
            menus.add(0, dashboardMenu);
        }
        //外卖订单菜单
        Menu wmMenu = adWmOrderMenu();  //饿了么菜单
        menus.add(wmMenu);

        // 配置中心菜单始终存在
        Menu configMenu = new Menu("配置管理", "#/configure", "fa fa-gears");
        menus.add(configMenu);

        out.println(JSON.toJSONString(menus));
        out.close();
        return null;
    }

    private Menu adJustJPMenu(Cfg cfg) {
        Menu jpMenu = new Menu();

        jpMenu.setMenuName("卷皮平台");
        jpMenu.setMenuIcon("fa fa-forumbee");

        Menu orderMenu = new Menu("在线订单", "#/jpOrder");
        Menu fixOrderMenu = new Menu("离线补单", "#/jpFixOrder");

        List<Menu> jpMenus = new ArrayList<>();
        jpMenus.add(orderMenu);
        jpMenus.add(fixOrderMenu);
        jpMenu.setMenu(jpMenus);

        return jpMenu;
    }

    private  Menu adJustZ8Menu(Cfg cfg){
        // 获取折800token信息
        Z8AccessToken z8AToken = sysFacadeService.getZ8Token();
        Menu z8Menu = new Menu();

        // 若没有则根据appId生成授权连接
        if(z8AToken == null){
            z8Menu.setMenuName("折八百授权");
            z8Menu.setMenuIcon("fa fa-expeditedssl");

            String action = MessageFormat.format(Constants.Z8_REQUEST_TOKEN_URL, cfg.getAppKey(), cfg.getCallback(), Constants.PLATFORM_Z800);
            z8Menu.setMenuAction(action);
        }else {
            z8Menu.setMenuName("折八百平台");
            z8Menu.setMenuIcon("fa fa-empire");

            Menu controlMenu = new Menu("控制台", "#/z8Control");
            Menu orderMenu = new Menu("在线订单", "#/z8Order");

            List<Menu> z8Menus = new ArrayList<>();
            z8Menus.add(controlMenu);
            z8Menus.add(orderMenu);

            z8Menu.setMenu(z8Menus);
        }
        return z8Menu;
    }

    private Menu adJustJdMenu(Cfg cfg) {
        // 获取京东token信息
        JdAccessToken jdAToken = sysFacadeService.getJdToken();
        Menu jdMenu = new Menu();

        // 若没有则根据appId生成授权连接
        if (jdAToken == null) {
            jdMenu.setMenuName("京东授权");
            jdMenu.setMenuIcon("fa fa-expeditedssl");

            String action = MessageFormat.format(Constants.JD_LINK_TOKEN_URL, cfg.getAppKey(), cfg.getCallback(), Constants.PLATFORM_JD);
            jdMenu.setMenuAction(action);
        } else {
            jdMenu.setMenuName("京东平台");
            jdMenu.setMenuIcon("fa fa-connectdevelop");

            Menu storeMenu = new Menu("控制台", "#/jdControl");
            Menu orderMenu = new Menu("在线订单", "#/jdOrder");
            //Menu fixOrderMenu = new Menu("离线补单", "#/jdFixOrder");
            Menu partyMenu = new Menu("会员列表", "#/jdParty");

            List<Menu> jdMenus = new ArrayList<>();
            jdMenus.add(storeMenu);
            jdMenus.add(orderMenu);
            //jdMenus.add(fixOrderMenu);
            jdMenus.add(partyMenu);

            jdMenu.setMenu(jdMenus);
        }

        return jdMenu;
    }

    private Menu adJustTmallMenu(Cfg cfg) {
        // 获取天猫token信息
        TmallAccessToken tmallAccessToken = sysFacadeService.getTmToken();
        Menu tmMenu = new Menu();

        // 若没有授权的token信息则生成授权连接
        if (tmallAccessToken == null) {
            tmMenu.setMenuName("天猫授权");
            tmMenu.setMenuIcon("fa fa-expeditedssl");

            String action = MessageFormat.format(Constants.TMALL_LINK_TOKEN_URL, cfg.getAppKey(), cfg.getCallback(), Constants.PLATFORM_TM);
            tmMenu.setMenuAction(action);
        } else {
            tmMenu.setMenuName("天猫平台");
            tmMenu.setMenuIcon("fa fa-slideshare");

            // child menu
            Menu orderMenu = new Menu("在线订单", "#/tmallOrder");
            Menu controlMenu = new Menu("控制台", "#/tmallControl");
            Menu refundMenu = new Menu("退款单", "#/tmallRefund/all");

            List<Menu> tmMenus = new ArrayList<>();
            tmMenus.add(controlMenu);
            tmMenus.add(orderMenu);
            tmMenus.add(refundMenu);
            tmMenu.setMenu(tmMenus);
        }
        return tmMenu;
    }
    private Menu adJustElemeMenu(Cfg cfg) {
        // 获取天猫token信息
        Menu emMenu = new Menu();
        emMenu.setMenuName("饿了么平台");
        emMenu.setMenuIcon("fa fa-bitbucket-square");

        // child menu
        Menu orderMenu = new Menu("在线订单", "#/elemeOrder");
        Menu controlMenu = new Menu("控制台", "#/elemeControl");

        List<Menu> emMenus = new ArrayList<>();
        emMenus.add(controlMenu);
        emMenus.add(orderMenu);
        emMenu.setMenu(emMenus);
        return emMenu;
    }

    private Menu adWmOrderMenu() {
        // 一级菜单
        Menu wmMenu = new Menu();
        wmMenu.setMenuName("外卖订单管理");
        wmMenu.setMenuIcon("fa fa-calculator");

        // child menu
        Menu orderMenu = new Menu("今日订单", "#/nowDayOrder");
        Menu controlMenu = new Menu("历史订单", "#/historyOrder");

        List<Menu> wmMenus = new ArrayList<>();
        wmMenus.add(controlMenu);
        wmMenus.add(orderMenu);
        wmMenu.setMenu(wmMenus);
        return wmMenu;
    }


    private Cfg getPlatformCfg(List<Cfg> cfgs, String platform) {
        List<Cfg> tempCfgs = cfgs.stream().filter(p -> platform.equals(p.getPlatform())).collect(Collectors.toList());
        if (tempCfgs == null || tempCfgs.size() == 0)
            return null;
        return tempCfgs.get(0);
    }
}
