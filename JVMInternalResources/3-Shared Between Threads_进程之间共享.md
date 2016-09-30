#Shared Between Threads
First we can have a look about the JVM_Architecture
![image](Pictures/JVM_Internal_Architecture.png)
##Heap
The Heap is used to allocate class instances and arrays at runtime. Arrays and objects can never be stored on the stack **because a frame is not designed to change in size after it has been created**. The frame only stores references that point to objects or arrays on the heap. Unlike primitive variables and references in the local variable array (in each frame) objects are always stored on the heap so they are not removed when a method ends. Instead objects are only removed by the garbage collector.
To support garbage collection the heap is divided into three sections:
 - Young Generation
  + Often split between Eden and Survivor
 - Old Generation (also called Tenured Generation)
 - Permanent Generation

##Memory Management
Objects and Arrays are never explicitly de-allocated instead the garbage collector automatically
reclaims them.</br>
Typically this works as follows:

1: New objects and arrays are created into the young generation.
2: Minor garbage collection will operate in the young generation. Objects, that are still alive,
will be moved from the eden space to the survivor space.
3: Major garbage collection, which typically causes the application threads to pause, will move objects
between generations. Objects, that are still alive, will be moved from the young generation to the old generation.
4: The permanent generation is collected every time the old generation is collected. They are both collected when either
 becomes full. **But notes: The permanent generation is in the non-heap space**.

##Non-Heap Memory
Objects that are logically considered as part of the JVM mechanics are not created on the Heap.</br>
The non-heap memory includes:
 - **Permanent Generation** that contains
   + the method area
   + interned strings
   + [oracle blog about permanent generation](https://blogs.oracle.com/jonthecollector/entry/presenting_the_permanent_generation)
 - **Code Cache** used for compilation and storage of methods that have been compiled to native code by the JIT compiler.
