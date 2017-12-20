import java.util.ArrayList;
import java.util.Random;

/**
 * MyProcess class is used to simulate the task processes.
 * As the Thread extension it has the number, the current semaphore
 * and the trace - all semaphores visited from the leaf to the root (critical section).
 */
public class MyProcess extends Thread {
    private int number;
    private MySemaphore currentSemaphore;
    private ArrayList<MySemaphore> trace;

    /**
     * Default constructor.
     * @param number is a process number.
     */
    public MyProcess(int number) {
        this.number = number;
        trace = new ArrayList<>();
    }

    /**
     * Setter for the current semaphore.
     * Also adds the new semaphore to trace.
     * @param semaphore is a new semaphore.
     */
    public void setCurrentSemaphore(MySemaphore semaphore) {
        this.currentSemaphore = semaphore;
        trace.add(currentSemaphore);
    }

    @Override
    public void run() {
        Runner.logger.writeLine("Starting thread id: p" + number);
        randomSleep(100, 1000);

        while (true) {
            while (currentSemaphore != Runner.semaphores[0]) {
                blockCurrentSemaphore();
            }

            handleCriticalResource();
        }
    }

    /**
     * The process behavior in the critical section.
     * Process blocks the critical section semaphore, waits for a random time (500 - 1500)
     * and release all semaphores in trace.
     */
    private void handleCriticalResource() {
        boolean success = currentSemaphore.tryAcquire();
        if (success) {
            Runner.logger.writeLine("Thread p" + number + " in critical section now." +
                    "Will be in critical section for " + randomSleep(500, 1500) + " milliseconds...");

            releaseSemaphores();
        }
    }

    /**
     * Release all semaphores in a process trace and return the process to
     * initial semaphore (proper semaphores tree leaf).
     * Sleep 250-500 millis after releasing.
     */
    private void releaseSemaphores() {
        for (int i = trace.size() - 1; i >= 0; i--) {
            Runner.logger.writeLine("Thread p" + number + " releasing lock on semaphore s" + trace.get(i).getNumber());
            trace.get(i).release();
        }

        randomSleep(250, 500);
        currentSemaphore = trace.get(0);
        trace.clear();
        trace.add(currentSemaphore);
    }

    /**
     * Process tries to block the current semaphore. In case of success - print massage and
     * go to the parent semaphore. Otherwise print the failure message and wait until the semaphore
     * is released.
     */
    private void blockCurrentSemaphore() {
        String msg = "Thread p" + number + " trying to lock semaphore s" + currentSemaphore.getNumber() + " : ";
        String scs;

        boolean success = currentSemaphore.tryAcquire();
        if (success) {
            scs = "success";
            setCurrentSemaphore(Runner.semaphores[currentSemaphore.getParentSemaphoreNumber()]);
        }
        else {
            scs = "failure [waiting]";
            randomSleep(1400, 1800);
        }

        msg += scs;
        Runner.logger.writeLine(msg);
    }

    /**
     * Let the thread sleep for the random amount of time.
     * @param minTime minimum time to sleep.
     * @param maxTime maximum time to sleep.
     * @return the amount of sleeping time (in millisecond).
     */
    private int randomSleep(int minTime, int maxTime) {
        Random random = new Random();

        int time = random.nextInt(maxTime - minTime) + minTime;

        try {
            sleep(time);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return time;
    }
}
