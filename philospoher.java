class Philosopher {
    public int position;
    public bool think;
    public bool eat;

    Philosopher(int position) {
        this.position = position;
        think = true;
        eat = false;
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
        eat = true;
        System.out.println("Philosopher " + position + " has started eating");
        Thread.sleep(5000 + (long)(Math.random() * 5000));
        eat = false;
        startThink();
    }
}
