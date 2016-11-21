package com.wangjunneil.schedule.service;

import com.wangjunneil.schedule.common.BaiDuException;
import com.wangjunneil.schedule.common.JdHomeException;
import com.wangjunneil.schedule.service.baidu.BaiDuApiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by yangwanbin on 2016-11-17.
 */
@Service
public class WMFacadeService {

    private static Logger log = Logger.getLogger(WMFacadeService.class.getName());

    @Autowired
    private BaiDuFacadeService baiDuFacadeService;

    public String orderPost(Map<String,String[]> stringMap,String paltform){
        try {
            baiDuFacadeService.orderPost("");
        }catch (BaiDuException ex_baidu){
         //告知Pos有异常，把日志编号返回给pos
        }
        //catch (JdHomeException ex_jdhome){}
        catch (Exception ex){}
        return null;
    }
}
