package com.wangjunneil.schedule.activemq;

import java.io.IOException;

/**
 * Created by yangwanbin on 2016-12-14.
 */
public class MQTransportListener implements org.apache.activemq.transport.TransportListener {

    private boolean transportFlag ;

    @Override
    public void onCommand(Object command) {

    }

    @Override
    public void onException(IOException error) {

    }

    @Override
    public void transportInterupted() {

    }

    @Override
    public void transportResumed() {

    }

    public void setTransportFlag(boolean bool){
        this.transportFlag = bool;
    }
}
