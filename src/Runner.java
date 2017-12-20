import java.util.Scanner;

/**
 * Runner class initializes the data, processes user input
 * and contains the main method.
 */
public class Runner {
    private static int threadsCount;
    private static MyProcess[] processes;
    public static MySemaphore[] semaphores;
    public static Logger logger = new Logger();
	private static Scanner scanner;

    public static void main(String[] args) {
        init();

        for (MyProcess process : processes) {
            process.start();
        }

        try {
            Thread.sleep(20000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.flush();
        System.exit(0);
    }

    /**
     * Initialize semaphores and processes.
     * They are stored in arrays but have the binary tree elements behavior.
     * So the process could make "step" only to the "parent" semaphore.
     */
    private static void init() {
        threadsCount = getThreadsCount();

        processes = new MyProcess[threadsCount];
        semaphores = new MySemaphore[threadsCount - 1];

        for (int i = 0; i < processes.length; i++) {
            processes[i] = new MyProcess(i);
        }

        for (int i = 0; i < semaphores.length; i++) {
            semaphores[i] = new MySemaphore(i);
        }

        int j = semaphores.length / 2;
        for (int i = 0; i < processes.length; i++) {
            processes[i].setCurrentSemaphore(semaphores[j]);
            if (i > 0 && i % 2 == 0) {
                j++;
            }
        }
    }

    /**
     * Get threads count from user.
     * Handle common input mistakes.
     * @return the proper number of threads.
     */
    private static int getThreadsCount() {
        int threadsCount = 0;

        while (threadsCount == 0) {
            try {
                threadsCount = Integer.parseInt(getLine());

                if (threadsCount < 1) {
                    System.out.println("Threads count should be a positive number.");
                    threadsCount = 0;
                }
                else if (!isCorrectThreadsCount(threadsCount)) {
                    System.out.println("Threads count should be 2, 4, 8, 16...");
                    threadsCount = 0;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("You should enter a number.");
            }
        }

        return threadsCount;
    }

    /**
     * Check if number of threads is power of 2 number.
     * @param threadsCount number to check.
     * @return true if threadsCount is power of 2 number
     * and false otherwise.
     */
    private static boolean isCorrectThreadsCount(int threadsCount) {
        while (threadsCount % 2 == 0) {
            threadsCount /= 2;
        }

        if (threadsCount != 1) {
            return false;
        }

        return true;
    }

    /**
     * Get one line from the user input.
     * @return String line.
     */
    private static String getLine() {
        System.out.println("Please enter the number of threads: ");
        System.out.print(" > ");
        scanner = new Scanner(System.in);
		return scanner.nextLine();
    }
}
