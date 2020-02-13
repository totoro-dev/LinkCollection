package utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ScheduledService {
    public static final ScheduledExecutorService LOCAL_SERVICE = Executors.newScheduledThreadPool(5);
}
