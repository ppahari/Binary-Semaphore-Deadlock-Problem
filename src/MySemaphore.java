import java.util.concurrent.Semaphore;

/**
 * MySemaphore class extends Semaphore with number field
 * and method to get the number of parent semaphore in semaphores binary tree.
 */

public class MySemaphore extends Semaphore {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int number;

    /**
     * Default constructor.
     * @param number is a semaphore number.
     */
    public MySemaphore(int number) {
        super(1);
        this.number = number;
    }

    /**
     * Get a number of a parent semaphore in a binary tree.
     * @return the number of semaphore in a parent node.
     */
    public int getParentSemaphoreNumber() {
        if (number % 2 == 1) {
            return number / 2;
        }
        else {
            return number / 2 - 1;
        }
    }

    /**
     * Getter for number.
     * @return number.
     */
    public int getNumber() {
        return number;
    }
}
