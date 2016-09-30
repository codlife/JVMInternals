#Threads
A thread is a thread of execution in a program. The JVM allows an application to
have multiple threads of execution running concurrently.
In the Hotspot JVM there is a direct mapping between a Java Thread and
a native operating system Thread. **After preparing all of the state for a
Java thread such as thread-local storage, allocation buffers, synchronization objects,
stacks and the program counter, the native thread is created**.
The native thread is reclaimed once the Java thread terminates.
The operating system is therefore responsible for scheduling all threads and
dispatching them to any available CPU. Once the native thread has initialized it
invokes the run() method in the Java thread.
 When the run() method returns, uncaught exceptions are handled,
 then the native thread confirms if the JVM needs to be terminated
 as a result of the thread terminating (i.e. is it the last non-deamon thread).
  When the thread terminates all resources for both the native and Java thread are released.

1. JVM System Threads
 - VM thread
 VM threadThis thread waits for operations to appear that require the JVM to reach a safe-point. The reason these operations have to happen on a separate thread is because they all require the JVM to be at a safe point where modifications to the heap can not occur. The type of operations performed by this thread are "stop-the-world" garbage collections, thread stack dumps, thread suspension and biased locking revocation.

 - Periodic task thread
 This thread is responsible for timer events (i.e. interrupts) that are used to schedule execution of periodic operations

 - GC threads
 These threads support the different types of garbage collection activities that occur in the JVM

 - Compiler threads
 These threads compile byte code to native code at runtime

 - Signal dispatcher threadThis thread receives signals sent to the JVM process and handle them inside the JVM by calling the appropriate JVM methods.

