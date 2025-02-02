public class Fork {
    private boolean inUse; 
    
    public Fork() {
        this.inUse = false;
    }
    
    public synchronized void pickUpFork() {
        // If the fork is being use, then the neighbor philosopher has to wait. 
        while (inUse) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.inUse = true; 
    }
    
    public synchronized void putDownFork() {
        this.inUse = false;
        notifyAll();
    }

    public synchronized boolean isInUse() {
        return this.inUse;
    }


}