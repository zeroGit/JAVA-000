

##### 首先，集群中的redis数量，最少3个主，所以，通常3主3从的话，最少需要6个redis节点
所以，首先，需要配置 6个redis节点

```
mkdir 7000 7001 7002 7003 7004 7005
```
然后每个 文件夹中，放置一个redis.conf
类似如下
```
port 7000
cluster-enabled yes
cluster-config-file nodes-7000.conf
cluster-node-timeout 5000
appendonly yes
```
appendonly 具体什么作用，未知
各个配置文件，对应的配置好

##### 然后，启动这六个redis节点，此时，这些redis节点之间，没有任何关系

```
redis-server 7000/redis.conf &
```

##### 创建集群
然后，使用者六个节点，创建一个集群

有个问题是，redis-3 redis-4 版本，必须要使用 `redis-trib.rb` 来创建

这玩意是一个 ruby 脚本，所以，他么的机器上还必须要安装这个沙比ruby语言环境

然后，centos6 当前时间，yum 源已经都不能用了，只能找一个 centos7 的去测试

然后，centos7 yum install 了ruby，但是，他么的 `redis-trib.rb` 出错，没有 ruby 的 redis 相关的包
还得使用 
```
gem install redis
```
去他么安装 redis 的 ruby 的相关包

`麻痹的有病吧，是不是有病？ 麻痹的提供一个工具，提供一个大多数人不用的环境，是不是他么的有病？麻痹的用c再写一个有他么的什么问题？你麻痹的省事了，他么的使用者呢？都他么什么傻逼玩意`

结果，redis-5 终于不用这个沙比玩意部署集群了，草，有病

然后，终于他么的启动起来了
```
>>> Creating cluster
>>> Performing hash slots allocation on 6 nodes...
Using 3 masters:
127.0.0.1:18940
127.0.0.1:18941
127.0.0.1:18943
Adding replica 127.0.0.1:18938 to 127.0.0.1:18940
Adding replica 127.0.0.1:18937 to 127.0.0.1:18941
Adding replica 127.0.0.1:18939 to 127.0.0.1:18943
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: 6af67ec9cce052930e5c686623a95db88dbec723 127.0.0.1:18940
   slots:0-5460 (5461 slots) master
M: 684de3f46bacb2b1609aa4a37295ce3f4cc291a6 127.0.0.1:18941
   slots:5461-10922 (5462 slots) master
M: 372ae11b19ab5241a97e8a7c1b405ad5fb1719be 127.0.0.1:18943
   slots:10923-16383 (5461 slots) master
S: 7667cf799b375717494a5bd0f6ac0d143b83e3dd 127.0.0.1:18939
   replicates 372ae11b19ab5241a97e8a7c1b405ad5fb1719be
S: bafdd44621bbb41320129da96f1a5b63dffde6e1 127.0.0.1:18938
   replicates 6af67ec9cce052930e5c686623a95db88dbec723
S: 491d5b34889a4c5b9b9092c36c464da2776f31f4 127.0.0.1:18937
   replicates 684de3f46bacb2b1609aa4a37295ce3f4cc291a6
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join...
>>> Performing Cluster Check (using node 127.0.0.1:18940)
M: 6af67ec9cce052930e5c686623a95db88dbec723 127.0.0.1:18940
   slots:0-5460 (5461 slots) master
   1 additional replica(s)
S: 491d5b34889a4c5b9b9092c36c464da2776f31f4 127.0.0.1:18937
   slots: (0 slots) slave
   replicates 684de3f46bacb2b1609aa4a37295ce3f4cc291a6
M: 684de3f46bacb2b1609aa4a37295ce3f4cc291a6 127.0.0.1:18941
   slots:5461-10922 (5462 slots) master
   1 additional replica(s)
M: 372ae11b19ab5241a97e8a7c1b405ad5fb1719be 127.0.0.1:18943
   slots:10923-16383 (5461 slots) master
   1 additional replica(s)
S: bafdd44621bbb41320129da96f1a5b63dffde6e1 127.0.0.1:18938
   slots: (0 slots) slave
   replicates 6af67ec9cce052930e5c686623a95db88dbec723
S: 7667cf799b375717494a5bd0f6ac0d143b83e3dd 127.0.0.1:18939
   slots: (0 slots) slave
   replicates 372ae11b19ab5241a97e8a7c1b405ad5fb1719be
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

##### 执行命令
如果使用 redis-cli 去测试，必须使用 `-c` 选项，表明是集群
如果使用通常的方式

```
# redis-cli -p 7000
127.0.0.1:18940> set 1 a
(error) MOVED 9842 127.0.0.1:18941
127.0.0.1:18940> set 2 a
(error) MOVED 5649 127.0.0.1:18941
127.0.0.1:18940> set 3 a
OK
127.0.0.1:18940> set 4 a
(error) MOVED 14039 127.0.0.1:18943
```
访问不在当前节点上的数据的时候，会返回一个错误

以下使用 `-c` 指定一个集群去访问其中一个redis服务
```
[root@yg-bgp-38 cluster]# redis-cli -c -h 127.0.0.1 -p 18940
127.0.0.1:18940> 
127.0.0.1:18940> get 1
-> Redirected to slot [9842] located at 127.0.0.1:18941
(nil)
127.0.0.1:18941> set 1 a
OK
127.0.0.1:18941> 
127.0.0.1:18941> get 3
-> Redirected to slot [1584] located at 127.0.0.1:18940
"a"
127.0.0.1:18940> get 4
-> Redirected to slot [14039] located at 127.0.0.1:18943
(nil)
127.0.0.1:18943> get 1
-> Redirected to slot [9842] located at 127.0.0.1:18941
"a"
```
其中，会自动转到那个服务连接上


##### 查看状态

```
> CLUSTER nodes
```
可以查看所有节点，以及负责的槽
```
372ae11b19ab5241a97e8a7c1b405ad5fb1719be 127.0.0.1:18943@28943 master - 0 1614235316053 3 connected 10923-16383
6af67ec9cce052930e5c686623a95db88dbec723 127.0.0.1:18940@28940 master - 0 1614235316053 1 connected 0-5460
491d5b34889a4c5b9b9092c36c464da2776f31f4 127.0.0.1:18937@28937 slave 684de3f46bacb2b1609aa4a37295ce3f4cc291a6 0 1614235317556 6 connected
bafdd44621bbb41320129da96f1a5b63dffde6e1 127.0.0.1:18938@28938 slave 6af67ec9cce052930e5c686623a95db88dbec723 0 1614235316000 5 connected
7667cf799b375717494a5bd0f6ac0d143b83e3dd 127.0.0.1:18939@28939 slave 372ae11b19ab5241a97e8a7c1b405ad5fb1719be 0 1614235317000 4 connected
684de3f46bacb2b1609aa4a37295ce3f4cc291a6 127.0.0.1:18941@28941 myself,master - 0 1614235317000 2 connected 5461-10922
v
```


当然还有其他的cluster 命令

##### 添加新节点
```
redis-trib.rb add-node 127.0.0.1:7003 127.0.0.1:7000
```
第一个地址，是要添加的 redis，第二个地址，是连接的集群中的某个服务


然后查看 集群节点
```
895009e59ea71c87df6dc9eeab461a2e21d01052 127.0.0.1:7002@17002 master - 0 1614241406615 3 connected
1391a29cea6a51ddd65b45571c2973b468a4520c 127.0.0.1:7000@17000 master - 0 1614241407115 5 connected 0-3999
7781600d181ac8ecb5ecb053413c0c2f7d7fd591 127.0.0.1:7003@17003 master - 0 1614241407616 4 connected 4000-16383
```

类似于上面，新添加的节点，没有分配槽


`分配槽`
连接到集群
```
redis-trib.rb reshard 127.0.0.1:7000
```
询问要转移多少个槽，比如输入 4000
```
How many slots do you want to move (from 1 to 16384)? 
```
输入接收槽的id
```
What is the receiving node ID?
```
输入抽取槽的源节点有哪些
```
  Please enter all the source node IDs.
  Type 'all' to use all the nodes as source nodes for the hash slots.
  Type 'done' once you entered all the source nodes IDs.
Source node #1:
```

输入yes
```
    Moving slot 7989 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7990 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7991 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7992 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7993 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7994 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7995 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7996 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7997 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7998 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
    Moving slot 7999 from 7781600d181ac8ecb5ecb053413c0c2f7d7fd591
Do you want to proceed with the proposed reshard plan (yes/no)? 
```

之后，会发现转移完成

##### 对于redis 3-4，只能使用 redis-trib.rb 这个玩意，还需要看看具体的别的命令

集群的操作大多通过这个脚本完成，redis-5 虽然工具变成了 redis-cli，但是，命令格式依然一样






