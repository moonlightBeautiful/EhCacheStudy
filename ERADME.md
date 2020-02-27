简介：
    EhCache 是一个纯Java的进程内缓存框架，具有快速、精干等特点，是Hibernate中默认的CacheProvider。
使用
   爬虫方面，使用EhCache缓存框架主要是为了判断重复Url，每次爬取一个网页，都把Url存储到缓存中，并且每次爬某个网页之前，都去缓存中搜索下，假如存在的话，我们就不要爬取这个网页了。
主要的特性有：
    1. 快速
    2. 简单  
    3. 多种缓存策略   
    4. 缓存数据有两级：内存和硬盘，因此无需担心容量问题，可以设置内存存储多少、什么。超过则放到硬盘存储
    5. 缓存数据会在虚拟机重启的过程中写入磁盘
    6. 可以通过RMI、可插入API等方式进行分布式缓存
    7. 具有缓存和缓存管理器的侦听接口
    8. 支持多缓存管理器实例，以及一个实例的多个缓存区域
    9. 提供Hibernate的缓存实现
Cache hello
    1.xml配置文件配置缓存配置
    2.创建Cache管理器，获取cache
    3.创建元素，把一个元素添加到cache中，从cache中获取缓存元素，刷新缓存，即把cache中的数据放到缓存中
    4.关闭Cache管理器
常用配置
    cache元素的属性：  
        1.name：缓存名称  
        2.maxElementsInMemory：内存中最大缓存对象数  
        3.maxElementsOnDisk：硬盘中最大缓存对象数，若是0表示无穷大  
        4.eternal：缓存对象过期配置，true表示对象永不过期，此时会忽略timeToIdleSeconds和timeToLiveSeconds属性，默认为false 
        5.timeToIdleSeconds： 设定允许对象处于空闲状态的最长时间，以秒为单位。
                当对象自从最近一次被访问后，如果处于空闲状态的时间超过了timeToIdleSeconds属性值，这个对象就会过期，EHCache将把它从缓存中清空。
                只有当eternal属性为false，该属性才有效。如果该属性值为0，则表示对象可以无限期地处于空闲状态  
        6.timeToLiveSeconds：设定对象允许存在于缓存中的最长时间，以秒为单位。
                当对象自从被存放到缓存中后，如果处于缓存中的时间超过了 timeToLiveSeconds属性值，这个对象就会过期，EHCache将把它从缓存中清除。
                只有当eternal属性为false，该属性才有效。如果该属性值为0，则表示对象可以无限期地存在于缓存中。
                timeToLiveSeconds必须大于timeToIdleSeconds属性，才有意义   
        7.overflowToDisk：true表示当内存缓存的对象数目达到了maxElementsInMemory界限后，会把溢出的对象写到硬盘缓存中。
                注意：如果缓存对象要写入到硬盘中的话，则该对象必须实现了Serializable接口才行。 
                    Element类实现了Serializable接口。
        8.diskSpoolBufferSizeMB：磁盘缓存区大小，默认为30MB。每个Cache都应该有自己的一个磁盘缓存区。  
        9.diskPersistent：是否缓存虚拟机重启期数据  
        10.diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认为120秒  
                注意：一般很少发生，用默认的就好
        11.memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。
                可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。  
Cache缓存持久化到磁盘
    简介：
        Ehcache默认配置的话 为了提高效率，所以有一部分缓存是在内存中，然后达到配置的内存缓存对象总量，则才根据策略持久化到硬盘中。
    问题：
        假如系统突然中断运行 那内存中的那些缓存，直接被释放掉了（丢失了），不能持久化到硬盘。
        这种数据丢失，对于一般项目是不会有影响的，但是对于爬虫系统，我们是用来判断重复Url的，所以数据不能丢失；
    