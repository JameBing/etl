package com.wangjunneil.schedule.service.jd;

import com.wangjunneil.schedule.entity.sys.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Created by wangjun on 8/4/16.
 */
@Service
public class JobService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Job getJob(String oprType) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("oprType").is(oprType), Criteria.where("status").is("success"));

        Query query = new Query(criteria).with(new Sort(Sort.Direction.DESC, "executeTime"));
        Job job = mongoTemplate.findOne(query, Job.class);
        return job;
    }

    public void addJob(Job job) {
        mongoTemplate.insert(job);
//        Query query = new Query(Criteria.where("platform").is("jd"));
//        Update update = new Update()
//            .set("platform", "jd")
//            .set("oprType", job.getOprType())
//            .set("executeTime", job.getExecuteTime())
//            .set("status", job.getStatus())
//            .set("msg", job.getMsg());
//        mongoTemplate.upsert(query, update, Job.class, "job_detail");
    }

    public void delJob(Job job){
        mongoTemplate.remove(job);
    }
}
