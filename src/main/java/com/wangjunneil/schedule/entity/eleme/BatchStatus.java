package com.wangjunneil.schedule.entity.eleme;


import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/20.
 */
public class BatchStatus {
    @SerializedName("batch_status")
    private Map<String, Status> batchstatus;
    @SerializedName("failed")
    private List<Integer> failed;

    public Map<String, Status> getBatchstatus() {
        return batchstatus;
    }

    public void setBatchstatus(Map<String, Status> batchstatus) {
        this.batchstatus = batchstatus;
    }

    public List<Integer> getFailed() {
        return failed;
    }

    public void setFailed(List<Integer> failed) {
        this.failed = failed;
    }
}
