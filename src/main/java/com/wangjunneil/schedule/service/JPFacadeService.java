package com.wangjunneil.schedule.service;

import com.wangjunneil.schedule.service.jp.JPInnerService;
import com.wangjunneil.schedule.service.jp.JpApiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JPFacadeService {

    private static Logger log = Logger.getLogger(JPFacadeService.class.getName());

    @Autowired
    private JPInnerService jpInnerService;

    @Autowired
    private JpApiService jpApiService;

}
