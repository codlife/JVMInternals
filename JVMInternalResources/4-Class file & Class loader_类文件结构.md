#Class file & Class loader</br>
This part is difficult and maybe boring, if this, you can just look the pictures and get a basic understanding.

## Class File Structure
A compiled class file consists of the following structure: </br>
```
ClassFile {
    u4			magic;
    u2			minor_version;
    u2			major_version;
    u2			constant_pool_count;
    cp_info		contant_pool[constant_pool_count – 1];
    u2			access_flags;
    u2			this_class;
    u2			super_class;
    u2			interfaces_count;
    u2			interfaces[interfaces_count];
    u2			fields_count;
    field_info		fields[fields_count];
    u2			methods_count;
    method_info		methods[methods_count];
    u2			attributes_count;
    attribute_info	attributes[attributes_count];
}
```
Explanations
 - magic, minor_version, major_version</br>
 specifies information about the version of the class and the version of the JDK this class was compiled for.
 - constant_pool</br>
 similar to a symbol table although it contains more data this is described in more detail below.
 - access_flags</br>
 provides the list of modifiers for this class.
 - this_class</br>
 index into the constant_pool providing the fully qualified name of this class i.e. org/jamesdbloom/foo/Bar
 - super_class</br>
 index into the constant_pool providing a symbolic reference to the super class i.e. java/lang/Object
 - interfaces </br>
 array of indexes into the constant_pool providing a symbolic references to all interfaces that have been implemented.
 - fields </br>
 array of indexes into the constant_pool giving a complete description of each field.
 - methods</br>
 array of indexes into the constant_pool giving a complete description of each method signature, if the method is not abstract or native then the bytecode is also present.
 - attributes</br>
 array of different value that provide additional information about the class including any annotations with RetentionPolicy.CLASS or RetentionPolicy.RUNTIME
It is possible to view the byte code in a compiled Java class by using the javap command.</br>
If you compile the following simple class:
package org.jvminternals;

```
public class SimpleClass {

    public void sayHello() {
        System.out.println("Hello");
    }

}
```

Then you get the following output if you run:</br>
javap -v -p -s -sysinfo -constants classes/org/jvminternals/SimpleClass.class

```
public class org.jvminternals.SimpleClass
  SourceFile: "SimpleClass.java"
  minor version: 0
  major version: 51
  flags: ACC_PUBLIC, ACC_SUPER
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
{
  public org.jvminternals.SimpleClass();
    Signature: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
        0: aload_0
        1: invokespecial #1    // Method java/lang/Object."<init>":()V
        4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          0      5      0    this   Lorg/jvminternals/SimpleClass;

  public void sayHello();
    Signature: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
        0: getstatic      #2    // Field java/lang/System.out:Ljava/io/PrintStream;
        3: ldc            #3    // String "Hello"
        5: invokevirtual  #4    // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        8: return
      LineNumberTable:
        line 6: 0
        line 7: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          0      9      0    this   Lorg/jvminternals/SimpleClass;
}
```
This class file shows three main sections the constant pool, the constructor and the sayHello method.

 - Constant Pool – this provides the same information that a symbol table typically provides and is described in more detail below.
 - Methods – each containing four areas:
   + signature and access flags
   + byte code
   + LineNumberTable – this provides information to a debugger to indicate which line corresponds to which byte code instruction, for example line 6 in the Java code corresponds to byte code 0 in the sayHello method and line 7 corresponds to byte code 8.
   + LocalVariableTable – this lists all local variables provided in the frame, in both examples the only local variable is this.

The following byte code operands are used in this class file
 - aload_0:This opcode is one of a group of opcodes with the format aload_<n>. They all load an object reference into the operand stack. The <n> refers to the location in the local variable array that is being accessed but can only be 0, 1, 2 or 3. There are other similar opcodes for loading values that are not an object reference iload_<n>, lload_<n>, float_<n> and dload_<n> where i is for int, l is for long, f is for float and d is for double. Local variables with an index higher than 3 can be loaded using iload, lload, float, dload and aload. These opcodes all take a single operand that specifies the index of local variable to load.
 - ldc:This opcode is used to push a constant from the run time constant pool into the operand stack.
 - getstatic:This opcode is used to push a static value from a static field listed in the run time constant pool into the operand stack.
 - invokespecial, invokevirtual:These opcodes are in a group of opcodes that invoke methods these are invokedynamic, invokeinterface, invokespecial, invokestatic, invokevirtual. In this class file invokespecial and invokevirutal are both used the difference between these is that invokevirutal invokes a method based on the class of the object. The invokespecial instruction is used to invoke instance initialization methods as well as private methods and methods of a superclass of the current class.
 - return:This opcode is in a group of opcodes ireturn, lreturn, freturn, dreturn, areturn and return. Each of these opcodes are a typed return statement that returns a different type where i is for int, l is for long, f is for float, d is for double and a is for an object reference. The opcode with no leading type letter return only returns void.
</br>As in any typical byte code the majority of the operands interact with the local variables, operand stack and run time constant pool as follows.
</br>The constructor has two instructions first this is pushed onto the operand stack, next the constructor for the super class is invoked which consumes the value off this and therefore pops it off the operand stack.

![image](Pictures/bytecode_explanation_SimpleClass.png)

The sayHello() method is more complex as it has to resolve symbolic references to actual references using the run time constant pool, as explained in more detail above. The first operand getstatic is used to push a reference to the static field out of the System class on to the operand stack. The next operand ldc pushes the string "Hello" onto the operand stack. The final operand invokevirtual invokes the println method of System.out which pops "Hello" off the operand stack as an argument and creates a new frame for the current thread.

!(image)(Pictures/bytecode_explanation_sayHello_smaller.png)

##Classloader
The JVM starts up by loading an initial class using the bootstrap classloader. The class is then linked and initialized before public static void main(String[]) is invoked. The execution of this method will in turn drive the loading, linking and initialization of additional classes and interfaces as required.
</br>
</br>
**Loading** is the process of finding the class file that represents the class or interface type with a particular name and reading it into a byte array. Next the bytes are parsed to confirm they represent a Class object and have the correct major and minor versions. Any class or interface named as a direct superclass is also loaded. Once this is completed a class or interface object is created from the binary representation.
</br>
</br>
**Linking** is the process of taking a class or interface verifying and preparing the type and its direct superclass and superinterfaces. Linking consists of three steps verifying, preparing and optionally resolving.
</br>
</br>
 - **Verifying** is the process of confirming the class or interface representation is structurally correct and obeys the semantic requirements of the Java programming language and JVM, for example the following checks are performed:
   </br>
    1. consistent and correctly formatted symbol table
    2. final methods / classes not overridden
    3. methods respect access control keywords
    4. methods have correct number and type of parameters
    5. bytecode doesn't manipulate stack incorrectly
    6. variables are initialized before being read
    7. variables are a value of the correct type
    </br>
    Performing these checks during the verifying stages means these checks do not need to be performed at runtime. Verification during linking slows down class loading however it avoids the need to perform these checks multiple when executing the bytecode.
    **Preparing** involves allocation of memory for static storage and any data structures used by the JVM such as method tables. Static fields are created and initialized to their default values, however, no initializers or code is executed at this stage as that happens as part of initialization.
    **Resolving** is an optional stage which involves checking symbolic references by loading the referenced classes or interfaces and checking the references are correct. If this does not take place at this point the resolution of symbolic references can be deferred until just prior to their use by a byte code instruction.
**Initialization** of a class or interface consists of executing the class or interface initialization method <clinit>
class loading, linking and initialization in the Java Virtual Machine (JVM)
![image](Pictures/Class_Loading_Linking_Initializing.png)
In the JVM there are multiple classloaders with different roles. Each classloader delegates to its parent classloader (that loaded it) except the bootstrap classloader which is the top classloader.
    - Bootstrap Classloader is usually implemented as native code because it is instantiated very early as the JVM is loaded. The bootstrap classloader is responsible for loading the basic Java APIs, including for example rt.jar. It only loads classes found on the boot classpath which have a higher level of trust; as a result it skips much of the validation that gets done for normal classes.
    - Extension Classloader loads classes from standard Java extension APIs such as security extension functions.
    - System Classloader is the default application classloader, which loads application classes from the classpath.
    - User Defined Classloaders can alternatively be used to load application classes. A user defined classloader is used for a number of special reasons including run time reloading of classes or separation between different groups of loaded classes typically required by web servers such as Tomcat.
classloader hierarchy in the Java Virtual Machine (JVM)
![image](Pictures/class_loader_hierarchy.png)
##Faster Class Loading</br>
A feature called Class Data Sharing (CDS) was introduce in HotSpot JMV from version 5.0. During the installation process of the JVM the installer loads a set of key JVM classes, such as rt.jar, into a memory-mapped shared archive. CDS reduces the time it takes to load these classes improving JVM start-up speed and allows these classes to be shared between different instances of the JVM reducing the memory footprint.
