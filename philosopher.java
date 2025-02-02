// Philosopher.java
public class Philosopher extends Thread {
    public boolean think;
    public boolean eat;
    private Fork leftFork;
    private Fork rightFork;
    private boolean[] spaghetti;  // Shared spaghetti array
    private int philosopherNumber;  // 1-5 for philosopher number
    
    public Philosopher(Fork leftFork, Fork rightFork, int philosopherNumber, boolean[] spaghetti) {
        think = true;
        eat = false;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.spaghetti = spaghetti;
        this.philosopherNumber = philosopherNumber;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                startThink();
                startEat();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void startThink() throws InterruptedException {
        think = true;
        eat = false;
        System.out.println("Philosopher " + philosopherNumber + " has started thinking");
        Thread.sleep(5000 + (long)(Math.random() * 5000));
        think = false;
    }
    
    public synchronized void startEat() throws InterruptedException {
        // Convert philosopher number to array index (0-4)
        int arrayIndex = philosopherNumber - 1;
        
        // Check if all other philosophers have eaten before eating again
        if (spaghetti[arrayIndex]) {
            boolean allOthersHaveEaten = true;
            for (int i = 0; i < spaghetti.length; i++) {
                if (i != arrayIndex && !spaghetti[i]) {
                    allOthersHaveEaten = false;
                    break;
                }
            }
            if (!allOthersHaveEaten) {
                return; // Wait for others to eat first
            }
        }
        
        // Try to pick up forks
        if (!leftFork.pickedUp && !rightFork.pickedUp) {
            pickUpFork(leftFork);
            pickUpFork(rightFork);
            
            eat = true;
            System.out.println("Philosopher " + philosopherNumber + " has started eating");
            spaghetti[arrayIndex] = true; // Mark that this philosopher has eaten
            
            // Check if all philosophers have eaten
            boolean allHaveEaten = true;
            for (boolean eaten : spaghetti) {
                if (!eaten) {
                    allHaveEaten = false;
                    break;
                }
            }
            
            // If all have eaten, reset the spaghetti array
            if (allHaveEaten) {
                System.out.println("All philosophers have eaten! Resetting...");
                for (int i = 0; i < spaghetti.length; i++) {
                    spaghetti[i] = false;
                }
            }
            
            Thread.sleep(5000 + (long)(Math.random() * 5000));
            eat = false;
            putDownFork(leftFork);
            putDownFork(rightFork);
        }
    }
    
    public synchronized void pickUpFork(Fork fork) {
        fork.pickUpFork();
    }
    
    public synchronized void putDownFork(Fork fork) {
        fork.putDownFork();
    }
}
