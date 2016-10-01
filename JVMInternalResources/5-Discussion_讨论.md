##Discussion
1. **Program Counter(PC)**
  Address of the current instruction(of opcode) unless it's native. **If the current method is native then
  the PC is undefined.(but why this?)**
2. **Method code**
       + Per method
           - Bytecodes
           - Operand stack size
           - Local variable size
           - Local variable table
           - Exception table
               + Per execution handler
               + Start point
               + End point
               + PC offset for handle code
               + Constant pool index for execution class being caught
    **(Why? we can see that there are local variables in stack frame, what's the difference)**
3. **Difference of the interned tables and constant pool in method area**.
we  can see the picture below
