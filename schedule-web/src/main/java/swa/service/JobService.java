package swa.service;

import swa.db.entity.JobInfo;

import java.util.List;

/**
 * Created by jinyan on 11/27/17 9:03 PM.
 */
public interface JobService {
    List<JobInfo> queryByAppName(String appName);
}
