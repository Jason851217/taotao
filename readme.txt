服务器规划：
    taotao-mysql-server:
      192.168.61.135

    taotao-image-server:
      192.168.61.101

    taotao-rest-server:
      192.168.61.102

    taotao-portal-server:
      192.168.61.103

    taotao-redis-server：
      192.168.61.104

    taotao-solr-server:
      192.168.61.105

    taotao-manager-server:
      192.168.61.106

    taotao-nginx-server:
      192.168.61.107



  本地运行的话：
    taotao-manager: http://localhost:8080
    taotao-rest:    http://localhost:8081
    taotao-portal:  http://localhost:8082
    taotao-search:  http://localhost:8083
    taotao-sso:     http://localhost:8084
    taotao-order:   http://localhost:8085


 Module:
    taotao-common:主要是一些常用工具类
    taotao-manager:网站后台
    taotao-portal：网站前台，负责网站页面的显示(会调用taotao-rest，taotao-sso，taotao-search,taotao-order等模块)
    taotao-rest:网站前台的数据接口 taotao-portal会通过httpclient调用
    taotao-sso：负责单点登录的相关接口(网站登录时会使用) taotao-portal会通过httpclient调用
    taotao-search: 负责整个网站搜索实现(网站搜索时会使用) taotao-portal会通过httpclient调用

redis集群：

  1. yum -y install ruby
  2. yum -y install rubygems
  3. 下载redis-3.2.2.gem并上传至服务器
  4. cp redis-3.2.2.gem /usr/local/redis-3.2.2.gem
  5. gem install /usr/local/redis-3.2.2.gem


  6. mkdir redis-cluster
  7. cp -r bin/ redis-cluster/redis01
  8. [root@jason-centos redis01]# rm -rf dump.rdb
  9. 6个redis实例，端口号分布：7001-7006
  10. 修改redis01实例配置文件中的端口号为7001，以及cluster-enabled yes
  11. 创建其余5个实例并重复步骤10做同样的修改
  12. 把创建集群的ruby脚本(redis-trib.rb)复制到redis-cluster目录下
         [root@jason-centos redis-cluster]# cp /opt/software/redis-3.2.11/src/*.rb ./
  13. 启动6个redis实例

      [root@jason-centos redis-cluster]# vi star-all.sh
            cd redis01/
            ./redis-server redis.conf
            cd ..

            cd redis02/
            ./redis-server redis.conf
            cd ..

            cd redis03/
            ./redis-server redis.conf
            cd ..

            cd redis04/
            ./redis-server redis.conf
            cd ..

            cd redis05/
            ./redis-server redis.conf
            cd ..

            cd redis06/
            ./red2ais-server redis.conf
            cd ..

     [root@jason-centos redis-cluster]# chmod +x start-all.sh   //加可执行权限

     [root@jason-centos redis-cluster]# ./start-all.sh


   14. 创建集群
        [root@jason-centos redis-cluster]# ./redis-trib.rb create --replicas 1 192.168.61.104:7001 192.168.61.104:7002 192.168.61.104:7003 192.168.61.104:7004 192.168.61.104:7005 192.168.61.104:7006   #命令中的"1"表示1个备份
        >>> Creating cluster
        >>> Performing hash slots allocation on 6 nodes...
        Using 3 masters:
        192.168.61.104:7001
        192.168.61.104:7002
        192.168.61.104:7003
        Adding replica 192.168.61.104:7004 to 192.168.61.104:7001
        Adding replica 192.168.61.104:7005 to 192.168.61.104:7002
        Adding replica 192.168.61.104:7006 to 192.168.61.104:7003
        M: 330417223c7e4d6250cd7b8e49192f5bc88d6366 192.168.61.104:7001    #slots:0-5460 这里是卡槽，范围在0~16383，slave是没有卡槽的，和master同步一致
           slots:0-5460 (5461 slots) master
        M: e36b1e2e064340bd351a6276b506297b13c9d7a9 192.168.61.104:7002
           slots:5461-10922 (5462 slots) master
        M: 6edab8ada0ef23ae43d2d5d2247b5f0b9f29bf46 192.168.61.104:7003
           slots:10923-16383 (5461 slots) master
        S: 27bfb1c159b2446d250febc596e4e53c05a12f22 192.168.61.104:7004
           replicates 330417223c7e4d6250cd7b8e49192f5bc88d6366
        S: 31d7fe02cb3734d3c9ec0961ade44803e9e94871 192.168.61.104:7005
           replicates e36b1e2e064340bd351a6276b506297b13c9d7a9
        S: 2301918cdeef985d1f4ecb0d35980ddf9c68d10d 192.168.61.104:7006
           replicates 6edab8ada0ef23ae43d2d5d2247b5f0b9f29bf46
        Can I set the above configuration? (type 'yes' to accept): yes
        >>> Nodes configuration updated
        >>> Assign a different config epoch to each node
        >>> Sending CLUSTER MEET messages to join the cluster
        Waiting for the cluster to join.....
        >>> Performing Cluster Check (using node 192.168.61.104:7001)
        M: 330417223c7e4d6250cd7b8e49192f5bc88d6366 192.168.61.104:7001
           slots:0-5460 (5461 slots) master
           1 additional replica(s)
        M: 6edab8ada0ef23ae43d2d5d2247b5f0b9f29bf46 192.168.61.104:7003
           slots:10923-16383 (5461 slots) master
           1 additional replica(s)
        M: e36b1e2e064340bd351a6276b506297b13c9d7a9 192.168.61.104:7002
           slots:5461-10922 (5462 slots) master
           1 additional replica(s)
        S: 2301918cdeef985d1f4ecb0d35980ddf9c68d10d 192.168.61.104:7006
           slots: (0 slots) slave
           replicates 6edab8ada0ef23ae43d2d5d2247b5f0b9f29bf46
        S: 27bfb1c159b2446d250febc596e4e53c05a12f22 192.168.61.104:7004
           slots: (0 slots) slave
           replicates 330417223c7e4d6250cd7b8e49192f5bc88d6366
        S: 31d7fe02cb3734d3c9ec0961ade44803e9e94871 192.168.61.104:7005
           slots: (0 slots) slave
           replicates e36b1e2e064340bd351a6276b506297b13c9d7a9
        [OK] All nodes agree about slots configuration.
        >>> Check for open slots...
        >>> Check slots coverage...
        [OK] All 16384 slots covered.


  Solr：
    1.Solr 是Apache下的一个顶级开源项目，采用Java开发，它是基于Lucene的全文搜索服务器。Solr提供了比Lucene更为丰富的查询语言，同时实现了可配置、可扩展，并对索引、搜索性能进行了优化。
      Solr是一个全文检索服务器，只需要进行配置就可以实现全文检索服务。

    2.


