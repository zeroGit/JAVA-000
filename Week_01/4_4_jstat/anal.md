一个 java 程序的 java 命令行
```
29709 Logstash 
-Xms1g 
-Xmx1g 
-XX:+UseParNewGC 
-XX:+UseConcMarkSweepGC 
-XX:CMSInitiatingOccupancyFraction=75 
-XX:+UseCMSInitiatingOccupancyOnly 
-Djava.awt.headless=true 
-Dfile.encoding=UTF-8 
-Djruby.compile.invokedynamic=true 
-Djruby.jit.threshold=0 
-XX:+HeapDumpOnOutOfMemoryError 
-Djava.security.egd=file:/dev/urandom
```
