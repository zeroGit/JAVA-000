

测试 ：

```
java -javaagent:path_To_TimingInterceptor-1.0-SNAPSHOT-shaded.jar -jar path_to_InterceptorTest-1.0-SNAPSHOT.jar
```

输出
```
this is a method namedEndWith timed : testTimed
void TestMain.testTimed() took 0
```
