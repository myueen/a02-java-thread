import java.util.Arrays;

public class Main {
    public static void main (String[] args) {
        Fork f1 = new Fork();
        Fork f2 = new Fork();
        Fork f3 = new Fork();
        Fork f4 = new Fork();
        Fork f5 = new Fork();
        Philosopher p1 = new Philosopher(f2, f1);
        Philosopher p2 = new Philosopher(f3, f2);
        Philosopher p3 = new Philosopher(f4, f3);
        Philosopher p4 = new Philosopher(f5, f4);
        Philosopher p5 = new Philosopher(f1, f5);
        Philosopher[] table = {p1, p2, p3, p4, p5};


        // Initialize a spaghetti class that false represents the 
        // corresponding philosopher has not eaten the corresponding
        // spaghetti. 
        boolean[] spaghetti = {false, false, false, false, false};

        if (Arrays.asList(spaghetti).contains(false)) {
        }
    }
}
