# a02-java-thread
COMP 590 Assignment 02: Java Thread Dining Philosophers Problem

In the Dining Philosophers problem, five philosophers sit around a circular table. Each philosopher has their own plate at the table. Between each pair of philosophers is a single fork, for a total of five forks. Each philosopher alternates between thinking and eating states. To eat their spaghetti, a philosopher needs to acquire both forks adjacent to them. After eating, they put down both forks and return to thinking. A philosopher can only use the forks to their immediate left and right.

In this assignment, we will represent the fork, the spaghetti, the table, and the philosophers as follows. 

1. Fork - Java Class 
    - Fields: 
        - inUse: boolean

    - Methods:
        - pickUpFork()
        - putDownFork()
        - isInUse()

2. Spaghetti - Boolean array
    - true: Spaghetti has not been eaten.
    - false: Spaghetti has been eaten.

3. Table 
    - All five philosophers.

4. Philosopher - Java Class extends Thread
    - Fields: 
        - think: boolean
        - eat: boolean 
        - leftFork: Fork
        - rightFork: Fork
        - spaghetti: boolean array
        - philosopherNumber: int 
        - waitingStack: Stack<Philosopher>
        - lock: Object

    - Methods: 
        - run()
        - startThink()
        - tryToEat()
        - canEat()
        - allSpaghettiEmpty()
        - resetSpaghetti()
        - startEat()

We have a Philosopher class, a Fork class, and a Main.java file. Our goal is to have more than one philosopher eating at the same time. The eating phase of the philosopher is when the method startEat() is called, and the thinking phase occurs whenever the philosopher is not eating. 

### Race Condition
When two philosophers are trying to grab the same fork, it results in a race condition. To avoid this, we use synchronized keywords in methods like tryingToEat() and startEat() to make sure only one thread (philosopher) can execute the method at a time. 

### Deadlock 
When a philosopher grabs the left fork and the neighboring two philosophers also grab the left fork, none of the three philosophers can start eating since they all need a fork from each other. To avoid such a deadlock, we made sure each philosopher had to pick up both the left fork and the right fork when trying to eat. A philosopher is eating with both forks or thinking with both forks put down.

### Starvation
Since every philosopher thinks for a random amount of time and then starts eating, a philosopher may always get to eat with both forks while the others never have a chance to eat. To avoid starving philosophers, we used the Stack to store philosophers who are not able to eat, along with a spaghetti boolean array that keeps track of the philosophers who have eaten. For example, when one philosopher is eating, the left philosopher and the right philosopher cannot eat. Thus, they are put into the Stack to wait. Furthermore, if one philosopher has eaten the spaghetti (i.e., spaghetti becomes false), he/she is not allowed to eat again unless everyone else has finished eating their spaghetti. This philosopher is put into the Stack as well. When everyone is done eating, the entire spaghetti boolean array becomes true (i.e., spaghetti is refilled). Then, the cycle continues. This allows all philosophers to have eaten the spaghetti without starving anybody.
