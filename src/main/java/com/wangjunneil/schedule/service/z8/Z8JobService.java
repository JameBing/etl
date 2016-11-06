package com.wangjunneil.schedule.service.z8;

import com.wangjunneil.schedule.entity.sys.JobZ8;
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
public class Z8JobService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public JobZ8 getJob(String oprType) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("oprType").is(oprType), Criteria.where("status").is("success"));

        Query query = new Query(criteria).with(new Sort(Sort.Direction.DESC, "executeTime"));
        JobZ8 job = mongoTemplate.findOne(query, JobZ8.class);
        return job;
    }

    public void addJob(JobZ8 job) {
        mongoTemplate.insert(job);
    }

    public void delJob(JobZ8 job){
        mongoTemplate.remove(job);
    }
}
