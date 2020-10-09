package grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {

    private static int interval = 0;
    private static final File WAY = new File("./src/main/resources/rabbit.properties");

    public static void main(String[] args) {
        interval = readIntervalFile(WAY);
        if (interval <= 0) {
            throw new IllegalArgumentException();
        }
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    public static int readIntervalFile(File file) {
        int result = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String parameter = reader.readLine().split("=")[1];
            result = Integer.parseInt(parameter);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }
}