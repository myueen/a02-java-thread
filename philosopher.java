// Philosopher.java

import java.util.Stack;

public class Philosopher extends Thread {
    public boolean think;
    public boolean eat;
    private Fork leftFork;
    private Fork rightFork;
    private boolean[] spaghetti;  // Shared spaghetti array
    private int philosopherNumber;  // 1-5 for philosopher number
    private Stack<Philosopher> waitingStack;    // waiting philosopher in line to eat next 
    private final Object lock;
    
    public Philosopher(Fork leftFork, Fork rightFork, int philosopherNumber, boolean[] spaghetti, Stack<Philosopher> waitingStack, Object lock) {
        think = true;
        eat = false;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.spaghetti = spaghetti;
        this.philosopherNumber = philosopherNumber;
        this.waitingStack = waitingStack; 
        this.lock = lock;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                startThink();
                tryToEat();            
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


    public void tryToEat() throws InterruptedException {
        synchronized (spaghetti) {
            if (canEat()) {
                leftFork.pickUpFork();
                rightFork.pickUpFork();
                startEat();
                leftFork.putDownFork();
                rightFork.putDownFork();
                spaghetti[philosopherNumber] = false;   // mark as eaten this round 
                checkWaitingPhilosopher();
            } else {
                System.out.println("Philosopher " + philosopherNumber + " is waiting.");
                if (!waitingStack.contains(this)) {
                    waitingStack.push(this);
                }
                
                // wait until lock is notified
                try {
                    lock.wait();    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // if all philosophers have eaten, reset spaghetti 
            if (allSpaghettiEmpty()) {
                resetSpaghetti();
            }
        }
    }

    public boolean canEat() {
        int leftNeighbor = (philosopherNumber + 4) % 5;
        int rightNeighbor = (philosopherNumber + 1) % 5; 

        // The spaghetti in front of this philosopher has not emptied, and either the left neighbor
        // has not start eating their spaghetti, or they have done eating and the fork is putDown, and 
        //same for the right neighbor. 
        return spaghetti[philosopherNumber] && (!spaghetti[leftNeighbor] || !leftFork.isInUse()) && (!spaghetti[rightNeighbor] || !rightFork.isInUse());
    }

    private void checkWaitingPhilosopher() {
        if (!waitingStack.isEmpty()) {
            // notifying waiting philosophers to check if they can eat. 
            lock.notifyAll();       
        }
    }

    private boolean allSpaghettiEmpty() {
        // Check if all philosophers have eaten
        for (boolean hasSpaghetti: spaghetti) {
            if (hasSpaghetti) return false; 
        }
        return true;
    }

    private void resetSpaghetti() {
        // If all have eaten, reset the spaghetti array
        System.out.println("All philosophers have eaten! Resetting...");
        for (int i = 0; i < spaghetti.length; i++) {
            spaghetti[i] = true;
        }
        lock.notifyAll();
    }

    
    public synchronized void startEat() throws InterruptedException {
        System.out.println("Philospher" + philosopherNumber + " has started eating.");
        try {
            Thread.sleep(5000 + (long)(Math.random() * 5000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }  
    }
}
