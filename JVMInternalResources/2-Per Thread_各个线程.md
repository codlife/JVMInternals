#Per Thread
Each thread of execution has the following components

##Program Counter(PC)
Address of the current instruction(of opcode) unless it's native. **If the current method is native then
the PC is undefined**.<font size=4>(Why this?)</font> All CPUs have a PC, typically the pC is incremented after each instruction and therefore
holds the address of the next instruction to be executed. The JVM uses the PC to keep track of
where it's executing instructions, the PC will in fact be pointing at a memory address in the Method
Area.

##Stack
Each thread has its own stack that holds a frame for each method executing on that thread.
The stack is a Last In First Out (LIFO) data structure, so the currently executing method is at the top of the stack.
A new frame is created and added (pushed) to the top of stack for every method invocation. The frame is removed (popped)
when the method returns normally or if an uncaught exception is thrown during the method invocation.
The stack is not directly manipulated, except to push and pop frame objects,
and therefore the frame objects may be allocated in the Heap and <font size=4>the memory does not need to be contiguous</font>.

