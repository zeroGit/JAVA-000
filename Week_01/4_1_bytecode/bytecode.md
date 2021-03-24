
#### 基本字节码示例

有些字节码表示，得清楚
```
-> 1234 
表示 1234 入栈

1234 -> 
表示 1234 出栈
```

`代码`
```
public class Hello {
    public static void main(String[] args) {
        int i = 15;
        int j = 29;
        int a = 33;
        j = i + 1 - 2 * a + 4 / a;

        if (j != 0) {
            for (int i1 = 0; i1 < 10; i1++) {
                j += i1;
            }
        }

        System.out.println("j : " + j);
    }
}
```

`字节码`
```
Classfile /F:/01_pro/week01/t1/out/production/t1/Hello.class
  Last modified 2021-3-19; size 897 bytes
  MD5 checksum b08e3750b4a29e69ec5b407eca954f42
  Compiled from "Hello.java"
public class Hello
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER

# 常量池
Constant pool:
   #1 = Methodref          #11.#32        // java/lang/Object."<init>":()V
   #2 = Fieldref           #33.#34        // java/lang/System.out:Ljava/io/PrintStream;
   #3 = Class              #35            // java/lang/StringBuilder
   #4 = Methodref          #3.#32         // java/lang/StringBuilder."<init>":()V
   #5 = String             #36            // j :
   ...

# 代码段 
{
  # 构造器方法
  public Hello();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
      ...

  # main 方法
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      # stack ：操作栈深度 3
      # locals : 本地变量表 5 个
      # args_size : 方法参数 1 个
      # 下面分析，具体参照java方法调用过程来看
      # java方法调用是操作栈方式的，不是通常的寄存器方式的
      stack=3, locals=5, args_size=1
         #  栈压入 15
         0: bipush        15
         #  栈顶出栈并设置此值到变量表1位置
         2: istore_1

         #  栈压入 29
         3: bipush        29
         #  栈顶出栈并设置此值到变量表2位置
         5: istore_2

         #  栈压入 33
         6: bipush        33
         #  栈顶出栈并设置此值到变量表3位置
         8: istore_3

         #  变量表1的值压入栈顶 15 -> 入栈
         9: iload_1
         #  常量值1 -> 入栈
        10: iconst_1
         #  栈顶两值相加，并消除，新值入栈 16 -> 入栈
        11: iadd

         #  常量值2 -> 入栈
        12: iconst_2
         #  变量表3的值压入栈顶 33 -> 入栈
        13: iload_3
         #  栈顶两值相乘，消除， 2*33 -> 入栈
        14: imul
         #  栈顶两值相减，消除，16-66 -> 入栈，-50
        15: isub
         #  4 ->
        16: iconst_4
         #  33 ->
        17: iload_3
         #  4/33 -> ，0 ->
        18: idiv
         #  0 + -50 -> 
        19: iadd
         #  -50 出栈，设置到变量表2位置
        20: istore_2
         #  -50 压入栈
        21: iload_2
         #  如果栈顶为0 跳到 46行代码处执行，并且出栈
        22: ifeq          46
         
         #  以下是 for 段内的代码
         #  0 入栈
        25: iconst_0
         #  0 -> 出栈，设置到变量表4位置
        26: istore        4
         #  -> 0 ，0 入栈
        28: iload         4
         #  -> 10 ，10 入栈
        30: bipush        10
         #  -> 栈顶两值比较，[0][10] [0]>[10] 则跳转到 46行代码执行
        32: if_icmpge     46
         #  变量表2 入栈 -> -50
        35: iload_2
         #  变量表4 入栈 -> 0
        36: iload         4
         #  -50 -> ; 0 -> ; -> -50+0
        38: iadd
         #  -> -50
        39: istore_2
         #  变量表4 +1 ，值为1
        40: iinc          4, 1
         #  跳转到 28行 执行
        43: goto          28

        # System.out.println("j : " + j);
        46: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
        49: new           #3                  // class java/lang/StringBuilder
        52: dup
        53: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
        56: ldc           #5                  // String j :
        58: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        61: iload_2
        62: invokevirtual #7                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        65: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        68: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        71: return
      LineNumberTable:
        line 3: 0
        line 4: 3
        line 5: 6
        line 6: 9
        line 8: 21
        line 9: 25
        line 10: 35
        line 9: 40
        line 14: 46
        line 15: 71
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
           28      18     4    i1   I
            0      72     0  args   [Ljava/lang/String;
            3      69     1     i   I
            6      66     2     j   I
            9      63     3     a   I
      StackMapTable: number_of_entries = 2
        frame_type = 255 /* full_frame */
          offset_delta = 28
          locals = [ class "[Ljava/lang/String;", int, int, int, int ]
          stack = []
        frame_type = 250 /* chop */
          offset_delta = 17
}
```










