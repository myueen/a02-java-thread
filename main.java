import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Fork f1 = new Fork();
        Fork f2 = new Fork();
        Fork f3 = new Fork();
        Fork f4 = new Fork();
        Fork f5 = new Fork();
        
        // Initialize spaghetti array - true means spaghetti still on the table.
        boolean[] spaghetti = new boolean[5];
        for (int i = 0; i < 5; i++) {
            spaghetti[i] = true;        
        }

        // Waiting Stack
        Stack<Philosopher> waitingStack = new Stack<>();

        // Create philosophers with numbers 1-5
        
        Philosopher p1 = new Philosopher(f2, f1, 1, spaghetti, waitingStack);
        Philosopher p2 = new Philosopher(f3, f2, 2, spaghetti, waitingStack);
        Philosopher p3 = new Philosopher(f4, f3, 3, spaghetti, waitingStack);
        Philosopher p4 = new Philosopher(f5, f4, 4, spaghetti, waitingStack);
        Philosopher p5 = new Philosopher(f1, f5, 5, spaghetti, waitingStack);
        
        Philosopher[] table = {p1, p2, p3, p4, p5};

        // Start all philosopher threads
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        
        // Let the simulation run for a while
        try {
            Thread.sleep(120000); // Run for 1 minute
            p1.interrupt();
            p2.interrupt();
            p3.interrupt();
            p4.interrupt();
            p5.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
