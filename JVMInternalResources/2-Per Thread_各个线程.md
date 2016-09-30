#Per Thread
Each thread of execution has the following components

##Program Counter(PC)
Address of the current instruction(of opcode) unless it's native. **If the current method is native then
the PC is undefined.(but why this?)** All CPUs have a PC, typically the pC is incremented after each instruction and therefore
holds the address of the next instruction to be executed. The JVM uses the PC to keep track of
where it's executing instructions, the PC will in fact be pointing at a memory address in the Method
Area.

##Stack
Each thread has its own stack that holds a frame for each method executing on that thread.
The stack is a Last In First Out (LIFO) data structure, so the currently executing method is at the top of the stack.
A new frame is created and added (pushed) to the top of stack for every method invocation. The frame is removed (popped)
when the method returns normally or if an uncaught exception is thrown during the method invocation.
The stack is not directly manipulated, except to push and pop frame objects,
and therefore the frame objects may be allocated in the Heap and **the memory does not need to be contiguous**.

##Native Stack
Not all JVMs support native methods, however, those that do typically create a per thread native method stack.
 If a JVM has been implemented using a C-linkage model for Java Native Invocation (JNI) then the native stack will be a C stack. In this case the order of arguments and return value will be identical in the native stack to typical C program.
**A native method can typically (depending on the JVM implementation) call back into the JVM and invoke a Java method. Such a native to Java invocation will occur on the stack (normal Java stack); the thread will leave the native stack and create a new frame on the stack (normal Java stack)**.

##Stack Restrictions
A stack can be a dynamic or fixed size. If a thread requires a larger stack than allowed a StackOverflowError is thrown,
If a thread requires a new frame and there isn't enough memory to allocate it then an OutOfMemoryError is thrown.

##Frame
**A new frame is created and added (pushed) to the top of stack for every method invocation. The frame is removed (popped) when the method returns normally or if an uncaught exception is thrown during the method invocation**. For more detail on exception handling see the section on Exception Tables below.
</br>
Each frame contains:
 - Local variable array
 - Return value
 - Operand stack
 - Reference to runtime constant pool for class of the current method

##Local Variables Array
 The array of local variables contains all the variables used during the execution of the method, including a reference to this, all method parameters and other locally defined variables. For class methods (i.e. static methods) the method parameters start from zero, however, for instance method the zero slot is reserved for this.
 </br>
 A local variable can be:
  - boolean
  - byte
  - char
  - long
  - short
  - int
  - float
  - double
  - reference
  - returnAddress
 All types take a single slot in the local variable array except long and double which both take two consecutive slots because these types are double width (64-bit instead of 32-bit).


##Operand Stack

The operand stack is used during the execution of byte code instructions in a similar way that general-purpose registers are used in a native CPU. Most JVM byte code spends its time manipulating the operand stack by pushing, popping, duplicating, swapping, or executing operations that produce or consume values. Therefore, instructions that move values between the array of local variables and the operand stack are very frequent in byte code. For example, a simple variable initialization results in two byte codes that interact with the operand stack.
```
int i;
```
Gets compiled to the following byte code:

```
iconst_0	// Push 0 to top of the operand stack
istore_1	// Pop value from top of operand stack and store as local variable 1
```

For more detail explaining interactions between the local variables array, operand stack and run time constant pool see the section on Class File Structure below.