
以下使用不同GC 执行 GCLogAnalysis.java
执行大约 1分钟，然后查看 top jstat 命令，查看结果的不同

机器，大约 24核，内存 32G

##### 串行GC

`生成对象`
```
正在执行...
执行结束!共生成对象次数:360070
```

`串行GC top命令`
可以看出，CPU 在 100%左右，实际上，一分钟之内，一直在 100%左右浮动，
内存也一直维持这个水平
说明了GC和应用线程是互斥的在CPU上处理
![串行GC-top](https://raw.githubusercontent.com/zeroGit/JAVA-000/main/Week_02/%E5%91%A8%E5%9B%9B4_gc%E6%80%BB%E7%BB%93/%E4%B8%B2%E8%A1%8Cgctop.png)

串行GC jstat命令
每秒5-7次YGC，每秒一次FGC
![串行GC-jstat](https://raw.githubusercontent.com/zeroGit/JAVA-000/main/Week_02/%E5%91%A8%E5%9B%9B4_gc%E6%80%BB%E7%BB%93/%E4%B8%B2%E8%A1%8Cgc.png)

##### 并行GC
`生成对象`
```
正在执行...
执行结束!共生成对象次数:687529
```

`并行GC top`
cpu 占用 200% 多，说明应用线程之外，其他线程有在并行处理，而且 内存占用 比串行的多
![并行GC-top](https://raw.githubusercontent.com/zeroGit/JAVA-000/main/Week_02/%E5%91%A8%E5%9B%9B4_gc%E6%80%BB%E7%BB%93/%E5%B9%B6%E8%A1%8Cgctop.png)

`并行GC jstat`
大概每秒1-2次YGC，每5秒一次FGC
![并行GC-jstat](https://raw.githubusercontent.com/zeroGit/JAVA-000/main/Week_02/%E5%91%A8%E5%9B%9B4_gc%E6%80%BB%E7%BB%93/%E5%B9%B6%E8%A1%8Cgcstat.png)

##### CMS GC
`生成对象`
和串行GC差不多
```
正在执行...
执行结束!共生成对象次数:362438
```

CMS top
CPU 占用 700%，占用很过，内存占用 高于 并行GC
![CMS-top](https://raw.githubusercontent.com/zeroGit/JAVA-000/main/Week_02/周四4_gc总结/cmstop.png)

CMS jstat
每秒20多次YGC，每6/7秒一次FGC
![CMS-jstat](https://raw.githubusercontent.com/zeroGit/JAVA-000/main/Week_02/%E5%91%A8%E5%9B%9B4_gc%E6%80%BB%E7%BB%93/cmsgc.png)

##### G1 10ms GC
`生成对象`
```
正在执行...
执行结束!共生成对象次数:540946
```

G1 top
CPU占用 350%，内存 21%
![G1-top](https://raw.githubusercontent.com/zeroGit/JAVA-000/main/Week_02/周四4_gc总结/g1_10_top.png)

G1 jstat
每秒 7-8次 YGC，没有 FGC
![G1-jstat](https://raw.githubusercontent.com/zeroGit/JAVA-000/main/Week_02/%E5%91%A8%E5%9B%9B4_gc%E6%80%BB%E7%BB%93/g1_10_stat.png)


##### 各GC情况对比
![GC比较](https://raw.githubusercontent.com/zeroGit/JAVA-000/main/Week_02/%E5%91%A8%E5%9B%9B4_gc%E6%80%BB%E7%BB%93/GC%E6%AF%94%E8%BE%83.png)

由比较可见，
其中 并行GC 创建的对象最多YGC FGC 总体而言算是最好

CMS FGC 时间很短
CMS 创建的对象几乎和串行GC差不多，内存和CPU占用都比较大

G1 没有FGC，但是YGC次数不少

串行GC 效率最低













