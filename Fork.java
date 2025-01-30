public class Fork {
    boolean pickedUp;

    public Fork() {
        this.pickedUp = false;
    }

    synchronized public void pickUpFork() {
        this.pickedUp = true;
    }

    synchronized public void putDownFork() {
        this.pickedUp = false;
    }
}