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

##Just in Time(JIT) Compilation
Java byte code is interpreted however this it not as fast as directly executing native code
on the JVM's host cpu, To improve performance the Oracle Hotspot VM looks for "hot" areas of byte
code that are executed regularly and compiles these to native code. The native code is then stored
in the code cache in non-heap memory. **In this way the Hotspot VM tries to choose the most appropriate
way to trade-off the extra time it takes to compile code verses the extra time it takes to execute interpreted
code**.

##Method Area
The method area stores per-class information such as:
 - **Classloader Reference**
 - **Run time Constant Pool**
   + Numeric constants
   + Field references
   + Method References
   + Attributes

 - **Field data**
   + Per field
      - Name
      - Type
      - Modifiers
      - Attributes

 - **Method data**
   + Per method
     - Name
     - Return Type
     - Parameter Types(in order)
     - Modifiers
     - Attributes

 - **Method code**
    + Per method
        - Bytecodes
        - Operand stack size
        - Local variable size
        - Local variable table   **(Why? we can see that there are local variables in stack frame, what's the difference)**
        - Exception table
            + Per execution handler
            + Start point
            + End point
            + PC offset for handle code
            + Constant pool index for execution class being caught

All threads share the same method area, so access to the method area data and the precess of
dynamic linking must be thread safe. If two threads attempt to access a field or method on a class
that has not yet been loaded it must only be loaded once and both thread must not continue execution
until it has been loaded.

##Where is the method area</br>
The Java Virtual Machine Specification Java SE 7 Edition clearly states: “Although the method area is logically part of the heap, simple implementations may choose not to either garbage collect or compact it.” In contradiction to this jconsole for the Oracle JVM shows the method area (and code cache) as being non-heap. The OpenJDK code shows that the CodeCache is a separate field of the VM to the ObjectHeap.

##**Classloader Reference**</br>
All classes that are loaded contain a reference to the classloader that loaded them. In turn the classloader also contains a reference to all classes that it has loaded.

##**Run Time Constant Pool**</br>
The JVM maintains a per-type constant pool, a run time data structure that is similar to a symbol table although it contains more data. Byte codes in Java require data, often this data is too large to store directly in the byte codes, instead it is stored in the constant pool and the byte code contains a reference to the constant pool. The run time constant pool is used in dynamic linking as described above
</br>
Several types of data is stored in the constant pool including
 - numeric literals
 - string literals
 - class references
 - field references
 - method references

For example the following code:

```
Object foo = new Object();
```

Would be written in byte code as follows:
```
 0:	new #2 		    // Class java/lang/Object
 1:	dup
 2:	invokespecial #3    // Method java/ lang/Object "<init>"( ) V
```
The new opcode (operand code) is followed by the #2 operand. This operand is an index into the constant pool and therefore is referencing the second entry in the constant pool. The second entry is a class reference, this entry in turn references another entry in the constant pool containing the name of the class as a constant UTF8 string with the value // Class java/lang/Object. This symbolic link can then be used to lookup the class for java.lang.Object. The new opcode creates a class instance and initializes its variables. A reference to the new class instance is then added to the operand stack. The dup opcode then creates an extra copy of the top reference on the operand stack and adds this to the top of the operand stack. Finally an instance initialization method is called on line 2 by invokespecial. This operand also contains a reference to the constant pool. The initialization method consumes (pops) the top reference off the operand pool as an argument to the method. At the end there is one reference to the new object that has been both created and initialized.
</br>
If you compile the following simple class:

```
package org.jvminternals;

public class SimpleClass {

    public void sayHello() {
        System.out.println("Hello");
    }

}
```

The constant pool in the generated class file would look like:
```
Constant pool:
   #1 = Methodref          #6.#17         //  java/lang/Object."<init>":()V
   #2 = Fieldref           #18.#19        //  java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #20            //  "Hello"
   #4 = Methodref          #21.#22        //  java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Class              #23            //  org/jvminternals/SimpleClass
   #6 = Class              #24            //  java/lang/Object
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lorg/jvminternals/SimpleClass;
  #14 = Utf8               sayHello
  #15 = Utf8               SourceFile
  #16 = Utf8               SimpleClass.java
  #17 = NameAndType        #7:#8          //  "<init>":()V
  #18 = Class              #25            //  java/lang/System
  #19 = NameAndType        #26:#27        //  out:Ljava/io/PrintStream;
  #20 = Utf8               Hello
  #21 = Class              #28            //  java/io/PrintStream
  #22 = NameAndType        #29:#30        //  println:(Ljava/lang/String;)V
  #23 = Utf8               org/jvminternals/SimpleClass
  #24 = Utf8               java/lang/Object
  #25 = Utf8               java/lang/System
  #26 = Utf8               out
  #27 = Utf8               Ljava/io/PrintStream;
  #28 = Utf8               java/io/PrintStream
  #29 = Utf8               println
  #30 = Utf8               (Ljava/lang/String;)V
```

The constant pool contains the following types:</br>
 - Integer:A 4 byte int constant
 - Long:An 8 byte long constant
 - Float:A 4 byte float constant
 - Double:A 8 byte double constant
 - String:A String constant that points at another Utf8 entry in the constant pool which contains the actual bytes
 - Utf8:A stream of bytes representing a Utf8 encoded sequence of characters
 - Class:A Class constant that points at another Utf8 entry in the constant pool which contains the fully qualified class name in the internal JVM format (this is used by the dynamic linking process)
 - NameAndType:A colon separated pair of values each pointing at other entries in the constant pool. The first value (before the colon) points at a Utf8 string entry that is the method or field name. The second value points at a Utf8 entry that represents the type, in the case of a field this is the fully qualified class name, in the case of a method this is a list of fully qualified class names one per parameter.
 - Fieldref, Methodref, InterfaceMethodref:A dot separated pair of values each pointing at other entries in the constant pool. The first value (before the dot) points at a Class entry. The second value points at a NameAndType entry.

##Exception Table
The exception table stores per-exception handler information such as:
 - Start point
 - End point
 - PC offset for handler code
 - Constant pool index for exception class being caught</br>

If a method has defined a try-catch or a try-finally exception handler then an Exception Table will be created. This contains information for each exception handler or finally block including the range over which the handler applies, what type of exception is being handled and where the handler code is.
</br>When an exception is thrown the JVM looks for a matching handler in the current method, if none is found the method ends abruptly popping the current stack frame and the exception is re-thrown in the calling method (the new current frame). If no exception handler is found before all frames have been popped then the thread is terminated. This can also cause the JVM itself to terminate if the exception is thrown in the last non-daemon thread, for example if the thread is the main thread.
</br>Finally exception handlers match all types of exceptions and so always execute whenever an exception is thrown. In the case when no exception is thrown a finally block is still executed at the end of a method, this is achieved by jumping to the finally handler code immediately before the return statement is executed.

##Symbol Table
In addition to per-type run-time constant pools the Hotspot JVM has a symbol table held in the permanent generation. The symbol table is a Hashtable mapping symbol pointers to symbols (i.e. Hashtable<Symbol*, Symbol>) and includes a pointer to all symbols including those held in run time constant pools in each class.
</br>Reference counting is used to control when a symbol is removed from the symbol table. For example when a class is unloaded the reference count of all symbols held in its run time constant pool are decremented. When the reference count of a symbol in the the symbol table goes to zero then the symbol table knows that symbol is not being referenced anymore and the symbol is unloaded from the symbol table. For both the symbol table and the string table (see below) all entries are held in a canonicalized form to improve efficiency and ensure each entry only appears once.

##Interned Strings (String Table)
The Java Language Specification requires that identical string literals, that contain the same sequence of Unicode code points, must refer to the same instance of String. In addition if String.intern() is called on an instance of String a reference must be returned that would be identical to the reference return if the string was a literal. The following therefore holds true:

```
("j" + "v" + "m").intern() == "jvm"
```
In the Hotspot JVM interned string are held in the string table, which is a Hashtable mapping object pointers to symbols (i.e. Hashtable<oop, Symbol>), and is held in the permanent generation. For both the symbol table (see above) and the string table all entries are held in a canonicalized form to improve efficiency and ensure each entry only appears once.
</br>String literals are automatically interned by the compiler and added into the symbol table when the class is loaded. In addition instances of the String class can be explicitly interned by calling String.intern(). When String.intern() is called, if the symbol table already contains the string then a reference to this is returned, if not the string is added to the string table and its reference is returned.