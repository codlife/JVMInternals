#JVM Internals
This article explains the internal architecture of the Java Virtual Machine (JVM).
The following diagram show the key internal components of a typical JVM that conforms to The Java Virtual Machine Specification Java SE 7 Edition.

![image](https://github.com/codlife/JVMInternals/tree/master/Pictures)

The components shown on this diagram are each explained below in two sections. First section covers the components that are created for each thread and the second section covers the components that are created independently of threads.

1. Threads
 - JVM System Threads

2. Per Thread

 - program Counter (PC)
 - Stack
 - Native Stack
 - Stack Restrictions
 - Frame
 - Local Variables Array
 - Operand Stack
 - Dynamic Linking

3. Shared Between Threads
 - Heap
 - Memory Management
 - Non-Heap Memory
 - Just In Time (JIT) Compilation
 - Method Area
 - Where Is The Method Area
 - Classloader Reference
 - Run Time Constant Pool
 - Exception Table
 - Symbol Table

4. Class file & Class loader
  - Class File Structure
  - Classloader
  - Faster Class Loading

