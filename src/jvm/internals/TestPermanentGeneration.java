package jvm.internals;

/**
 * Created by wjf on 16-10-1.
 */
public class TestPermanentGeneration {
    /**
     * This class is to verify the Interned String in permanent generation
     * @param args
     * code
     * {{{
     *      public static void main(String[] args) {
     *            System.out.println("Hello");
     *
     *   }
     * }}}
     * Using the below command: javap -v -s -sysinfo -constants out/production/JVMInternals/jvm/internals/TestPermanentGeneration.class
     *
     * We get the information as below:
     * We can see that in method area there is an string "hello"
     * Next we use String.intern() to see some interesting things.
     *
     *
     * Classfile /usr/local/intellij/JVMInternals/out/production/JVMInternals/jvm/internals/TestPermanentGeneration.class
    Last modified 2016-10-1; size 594 bytes
    MD5 checksum b2ed7333a4362e1935be13abda221ffd
    Compiled from "TestPermanentGeneration.java"
    public class jvm.internals.TestPermanentGeneration
    minor version: 0
    major version: 52
    flags: ACC_PUBLIC, ACC_SUPER
    Constant pool:
    #1 = Methodref          #6.#20         // java/lang/Object."<init>":()V
    #2 = Fieldref           #21.#22        // java/lang/System.out:Ljava/io/PrintStream;
    #3 = String             #23            // Hello
    #4 = Methodref          #24.#25        // java/io/PrintStream.println:(Ljava/lang/String;)V
    #5 = Class              #26            // jvm/internals/TestPermanentGeneration
    #6 = Class              #27            // java/lang/Object
    #7 = Utf8               <init>
    #8 = Utf8               ()V
    #9 = Utf8               Code
    #10 = Utf8               LineNumberTable
    #11 = Utf8               LocalVariableTable
    #12 = Utf8               this
    #13 = Utf8               Ljvm/internals/TestPermanentGeneration;
    #14 = Utf8               main
    #15 = Utf8               ([Ljava/lang/String;)V
    #16 = Utf8               args
    #17 = Utf8               [Ljava/lang/String;
    #18 = Utf8               SourceFile
    #19 = Utf8               TestPermanentGeneration.java
    #20 = NameAndType        #7:#8          // "<init>":()V
    #21 = Class              #28            // java/lang/System
    #22 = NameAndType        #29:#30        // out:Ljava/io/PrintStream;
    #23 = Utf8               Hello
    #24 = Class              #31            // java/io/PrintStream
    #25 = NameAndType        #32:#33        // println:(Ljava/lang/String;)V
    #26 = Utf8               jvm/internals/TestPermanentGeneration
    #27 = Utf8               java/lang/Object
    #28 = Utf8               java/lang/System
    #29 = Utf8               out
    #30 = Utf8               Ljava/io/PrintStream;
    #31 = Utf8               java/io/PrintStream
    #32 = Utf8               println
    #33 = Utf8               (Ljava/lang/String;)V
    {
    public jvm.internals.TestPermanentGeneration();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
    stack=1, locals=1, args_size=1
    0: aload_0
    1: invokespecial #1                  // Method java/lang/Object."<init>":()V
    4: return
    LineNumberTable:
    line 6: 0
    LocalVariableTable:
    Start  Length  Slot  Name   Signature
    0       5     0  this   Ljvm/internals/TestPermanentGeneration;

    public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
    stack=2, locals=1, args_size=1
    0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
    3: ldc           #3                  // String Hello
    5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
    8: return
    LineNumberTable:
    line 12: 0
    line 14: 8
    LocalVariableTable:
    Start  Length  Slot  Name   Signature
    0       9     0  args   [Ljava/lang/String;
    }
    SourceFile: "TestPermanentGeneration.java"

     */
    public static void main(String[] args) {

//        String s0 = "wang";
        String s1 = new String("wang");
        String s2 = s1.intern();
//        String
        System.out.println(s1.intern() == s2);
        System.out.println( s2 == s1);
        System.out.println("Hello");

//        String c = "D";
//        String d = new String("D").intern();
        String c = "D";
        String d = new String("D").intern();
        // 类似的情况大家可以自己分析
        System.out.println(c == d);




    }
}
