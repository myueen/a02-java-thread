public class Fork {
    boolean pickedUp; 
    
    public Fork() {
        this.pickedUp = false;
    }
    
    public synchronized void pickUpFork() {
        this.pickedUp = true; 
    }
    
    public synchronized void putDownFork() {
        this.pickedUp = false;
    }
}