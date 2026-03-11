# JVM Impossible State Demo

A minimal Java demo showing that the Java Memory Model can legally produce an apparently impossible state:

r1 = 0
r2 = 0

Two threads.
Two variables.
No synchronization on the shared data.
