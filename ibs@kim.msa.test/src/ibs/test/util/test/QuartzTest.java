package ibs.test.util.test;

import java.util.HashSet;
import java.util.Set;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest implements Job {
	
	Scheduler scheduler;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println(System.currentTimeMillis());
	}
	
	private static final class test {
		public static void main(String[] args) {
			try {
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
				scheduler.start();
				
				JobDetail job = JobBuilder.newJob(QuartzTest.class)
						.withIdentity("FcstInfo_job","FcstInfo")
						.build();
				// 실행 시점을 결정하는 Trigger 생성
				Set<Trigger> triggerSet = new HashSet<Trigger>();
				CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
						.withIdentity("FcstInfo_trigger","FcstInfo")
						.withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression("0 0,10,20,30,40,50 * * * ?")))
						.build();
				triggerSet.add(trigger);
				// 스케줄러 실행 및 JobDetail과 Trigger 정보로 스케줄링
				scheduler.scheduleJob(job, triggerSet, false);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
