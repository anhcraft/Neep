package utils;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class Action {
    private final String name;
    private final NeepRunnable runnable;
    private final int frequency;
    private long avgRunTime;
    private long minRunTime;
    private long maxRunTime;

    public Action(String name, NeepRunnable runnable, int frequency) {
        this.name = name;
        this.runnable = runnable;
        this.frequency = frequency;
    }

    public Action start(){
        for (int i = 0; i < frequency; i++) {
            long start = System.nanoTime();
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            long time = System.nanoTime() - start;
            if(i == 0) {
                avgRunTime = time;
                minRunTime = time;
                maxRunTime = time;
            } else {
                avgRunTime += time / frequency;
                minRunTime = Math.min(minRunTime, time);
                maxRunTime = Math.max(maxRunTime, time);
            }
        }
        return this;
    }

    public void report(){
        System.out.println("[+] " + name + " x" + frequency);
        System.out.println("  - Avg time: " + avgRunTime + " ns (" + MILLISECONDS.convert(avgRunTime, NANOSECONDS) + " ms)");
        System.out.println("  - Min time: " + minRunTime + " ns (" + MILLISECONDS.convert(minRunTime, NANOSECONDS) + " ms)");
        System.out.println("  - Max time: " + maxRunTime + " ns (" + MILLISECONDS.convert(maxRunTime, NANOSECONDS) + " ms)");
    }
}
