# a02-java-thread
COMP 590 Assignment 02 Java Thread Dining Philosophers problem

In the Dining Philosophers problem, there are five philosopher sitting around a table. Each philospher can have one plate of spagehetti. However, there are only five forks on the table. All of the philosphers are either in the thinking or eating state. 

In this assignment, we will represent fork, spagehetti, table, and philospher as follows. 

1. Fork - Java Class 
    - Fields: 
        - inUse: boolean

    - Methods:
        - pickUpFork()
        - putDownFork()
        - isInUsed()


2. Spagehetti - Boolean array
    - true: spaghetti has not emptied
    - false: spaghetti has emptied (i.e., eaten by a philosopher)


3. Table 
    - all five philosopher. 


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
        - startThinking()
        - tryToEat()
        - canEat()
        - allSpaghettiEmpty()
        - resetSpaghetti()
        - startEat()



In the dining philospher problem, we have a Philosopherr class, a Fork class, and Main.java file. Our goal is to have more than one philosopher eating at the same time. The eating phase of the philosopher is when the method startEat() is called, and the thinking phase is the waiting to eat phase. 

### Race Condition
When two philosopher trying to grab the same fork, it results in a race condition. To avoid this, we use synchronized keywords in methods like tryingToEat() and startEat() to make sure only one thread (philosopher) execute the method at a time. 

### Deadlock 
When a philosopher grabs the left fork and the neighboring two philosophers also grabs the left fork, none of the three philosopher can start eating because they all need a fork from each other. To avoid such deadlock, we make sure each philosopher must pick up both left fork and right fork when they are able to eat the spaghetti. Therefore, either a philosopher is eating with both forks, or thinking where both forks are on the table available to be pickuped. 

### Starvation
Since every philosopher thinks a random amount of time and then starts eating, it is possible that some philosopher always get to eat with both forks while the other never have a chance to eat. To avoid starving some philosopher, we use Stack to store philosopher that are not able to eat and a spaghetti boolean array to make sure all philosophers have a chance to eat. Specifically, when one philosopher is eating, the left philosopher and right philosopher are not able to eat. Thus they are put into the Stack to wait. Futhermore, if one philosopher has eaten the spaghetti (i.e., spaghetti become false), it is not allowed to eat again unless everyone else has finished eating the spaghetti. This philosopher is put into the Stack as well. When everyone is done eating the spaghetti, the spaghetti boolean array all become true (i.e., spaghetti is refilled). And the cycles continues. This allows all philosopher to have eat the spaghetti without starving any philosopher.
