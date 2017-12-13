package swa.job.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.job.JobInfo;

/**
 * Created by jinyan on 12/12/17 9:45 PM.
 */
public class JobScheduleParser {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduleParser.class);
    public static class ScheduleMsgBody {
        //标示：ADD，MODIFY
        private String code;
        private JobInfo newJob;
        private JobInfo oldJob;

        public JobInfo getNewJob() {
            return newJob;
        }

        public void setNewJob(JobInfo newJob) {
            this.newJob = newJob;
        }

        public JobInfo getOldJob() {
            return oldJob;
        }

        public void setOldJob(JobInfo oldJob) {
            this.oldJob = oldJob;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ScheduleMsgBody{");
            sb.append("code='").append(code).append('\'');
            sb.append(", newJob=").append(newJob);
            sb.append(", oldJob=").append(oldJob);
            sb.append('}');
            return sb.toString();
        }
    }
}