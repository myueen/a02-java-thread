public class Philosopher {
    public bool think;
    public bool eat;
    public Fork leftFork;
    public Fork rightFork;

    Philosopher(int position, Fork leftFork, Fork rightFork) {
        think = true;
        eat = false;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public startThink() {
        think = true;
        // Thinks for 5-10 seconds
        System.out.println("Philosopher " + position + " has started thinking");
        Thread.sleep(5000 + (long)(Math.random() * 5000));
        think = false;
        startEat();
    }

    public startEat() {
        if (leftFork.pickedUp == false && rightFork.pickedUp == false) {
            pickUpFork(leftFork);
            pickUpFork(rightFork);
            eat = true;
            System.out.println("Philosopher " + position + " has started eating");
            Thread.sleep(5000 + (long)(Math.random() * 5000));
            eat = false;
            putDownForkFork(leftFork);
            putDownFork(rightFork);
            startThink();
        }

    }

    public synchronized void pickUpFork(Fork fork) {
        fork.pickUpFork();
    }
    
    public synchronized void putDownFork(Fork fork) {
        fork.putDownFork();
    }

}
