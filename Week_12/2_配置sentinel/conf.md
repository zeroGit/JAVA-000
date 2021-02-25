
##### 配置 sentinel
通常的配置如下
```
# sentinel 的端口
port 26379

dir "/tmp"
sentinel myid 91c7f7944480d954205f5f4f937f00d396abe108
sentinel deny-scripts-reconfig yes

# 配置监控，nm 是随便自己的master名，自定义
# 这个主机端口，sentinel 运行中可以自己更改，然后 sentinel 重启之后，还会保持有所有的数据
# 只要配置一个主就行，从主会获取到所有的从
sentinel monitor nm 127.0.0.1 19937 1
# 主连不上多长时间算下线
sentinel down-after-milliseconds nm 3000
# 故障转移 多长时间算转移超时
sentinel failover-timeout nm 60000

sentinel config-epoch nm 2
sentinel leader-epoch nm 2

# 这个没有，是sentinel 运行中，自己更改的
sentinel known-slave nm 127.0.0.1 18937
sentinel current-epoch 2
```

##### 运行 
```
./redis-sentinel ../conf/sentinel.conf &
```

##### 操作
运行之后，就可以 通过 redis-cli 连接到 sentinel 进行操作了

比如 
```
> info
可以查看所有的配置，以及当前的主，当前的所有从
```

```
> SENTINEL MASTER nm
nm是master自定义名称，可以返回一些信息，比如当前的主
```

当然，还有 设置配置，获取配置的一些命令

还可以 
```
> PSUBSCRIBE *
订阅 sentinel 的所有事件，比如主切换了，从掉了等等
```

`需要注意一点，sentinel 运行过程中，会把当前的所有配置状态，写入配置文件中，所以，如果当前sentinel 切换了一个主从，然后 sentinel 重启了，读入的配置文件还是当前的，因为已经被 sentinel 更改了，不是原始的配置文件了`

##### 客户端怎么做

直接连接 sentinel 是执行不了通常的 redis 命令的

做法是，连接 sentinel ，然后，获取主，获取所有从，然后根据自己需要，连接主还是从去操作，
然后，还需要 接收 sentinel 的事件，或者循环拉取主从变化


##### 多个 sentinel

通常需要配置 >= 3 个的 sentinel

sentinel 的配置，确保master配置正确 就行，然后 多个 sentinel 会通过 master 获取到其他的 sentinel ，然后自己选主操作

复制已经运行过的 sentinel 的配置文件的时候，myid 配置要删除，这个是运行时写入的











