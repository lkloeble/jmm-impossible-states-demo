# JVM Impossible State Demo

A minimal Java demo showing that the Java Memory Model can legally produce an apparently impossible state:

r1 = 0
r2 = 0

Two threads.
Two variables.
No synchronization on the shared data.

And yet the JVM is allowed to produce a result that looks impossible.

---

# The scenario

Thread 1:

x = 1;  
r1 = y;

Thread 2:

y = 1;  
r2 = x;

At first glance, many developers assume that the following result cannot happen:

r1 = 0  
r2 = 0

After all, both threads perform a write before a read.

So at least one read should observe the other thread's write.

Except that under the **Java Memory Model**, this outcome is perfectly legal when there is **no happens-before relationship** between the threads.

---

# Why this can happen

Without synchronization, the JVM and the CPU are free to perform optimizations that preserve correctness **within a single thread**, but not necessarily from the perspective of another thread.

That means:

- **instruction reordering** may occur
- **writes may remain temporarily invisible** to other threads
- both reads may observe `0`
    

Nothing is broken.

This is simply the memory model doing exactly what it allows.

Each thread still observes its own instructions as if they were executed in order.

The paradox only appears when another thread observes the result.

---

# The demo

The program repeatedly executes the scenario until the following state is observed:

r1 = 0  
r2 = 0

When it happens, the program stops and prints something like:

Observed the "impossible" state:  
r1 = 0, r2 = 0  
First observed after 26053 iterations  
Elapsed time: 1315 ms

This means that the program detected the outcome **for the first time after 26,053 attempts**.

It does **not** mean the outcome occurred 26,053 times.

---

# Run the demo

Compile:

javac ImpossibleStateDemo.java

Run:

java ImpossibleStateDemo

The program loops until it observes the `0 / 0` outcome.

Depending on your machine, this may happen quickly or take longer.

---

# Why results vary

The probability of observing this state depends on many factors:

- CPU architecture
- JVM version
- system load
- thread scheduling
- pure timing luck
    
Sometimes the result appears in a few thousand iterations.  
Sometimes it takes much longer.

This variability is normal.

---

# Why this matters

In real systems, concurrency bugs often appear only:

- under load
- on a different machine
- after a JVM update
- after an innocent-looking refactoring
    

When there is no proper **happens-before relationship**, code that looks obviously correct can still produce surprising outcomes.

Understanding these rules is essential when diagnosing strange multithreaded behavior in Java systems.

---

# Related concepts

This demo is closely related to:

- the **Java Memory Model**
- **happens-before**
- **instruction reordering**
- **visibility**
- `volatile`
- `synchronized`
- CPU **store buffers**
    
- cache coherence and propagation delays
