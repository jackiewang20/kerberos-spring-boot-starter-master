#hbase-spring-boot-starter集成kerberos操作hbase

#1.背景
使用自定义kerberos-spring-boot-starter，hbase-spring-boot-starter组件进行开发。

由于大数据平台hadoop和hbase集群集成了kerberos验证，因此链接hbase之前首先进行kerberos身份认证，认证通过后可以访问hbase数据库。

#2.maven依赖
##2.1.kerberos-spring-boot-starter maven依赖
        <!-- kerberos starter和hbase starter分别都引用了hadoop-common，两个hadoop-common包冲突，
        排除其一个hadoop-common即可；如果造成krb5.conf不能正确读取，则排除guava包 -->

###方法1
```
         <dependency>
             <artifactId>kerberos-spring-boot-starter</artifactId>
             <groupId>com.example</groupId>
             <version>1.0.0-SNAPSHOT</version>
             <exclusions>
                 <exclusion>
                     <artifactId>hadoop-common</artifactId>
                     <groupId>org.apache.hadoop</groupId>
                 </exclusion>
             </exclusions>
         </dependency>       
```   
     
###方法2
```
        <!-- 自定义kerberos认证starter -->
        <dependency>
            <artifactId>kerberos-spring-boot-starter</artifactId>
            <groupId>com.example</groupId>
            <version>1.0.0-SNAPSHOT</version>
            <exclusions>
                <exclusion>
                    <artifactId>guava</artifactId>
                    <groupId>com.google.guava</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        
        <!-- 依赖新版本的guava -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <version>21.0</version>
            <artifactId>guava</artifactId>
        </dependency>
```

###方法3

```
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-common</artifactId>
            <version>3.1.4</version>
            <exclusions>
                <exclusion>
                    <artifactId>guava</artifactId>
                    <groupId>com.google.guava</groupId>
                </exclusion>
            <exclusions>
        </dependency>

        <!-- 依赖新版本的guava -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <version>21.0</version>
            <artifactId>guava</artifactId>
        </dependency>
```


##2.2.hbase-spring-boot-starter maven依赖

        <!-- 自定义hbase starter -->
        <dependency>
            <artifactId>hbase-spring-boot-starter</artifactId>
            <groupId>com.example</groupId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>

#3.属性配置
配置文件属性配置hbase.keytab、jaas.conf、krb5.conf，启动krb5.conf完整的配置信息从kerberos服务器中复制。

完整配置参考：
kerberos认证基础配置
application-kerberos.properties

hbase开启kerberos认证
application-hbase.properties
说明：如果不使用application-hbase.properties中的kerberos认证，则在resources文件夹中配置hbase-site.xml，
但是这样会存在弊端，如果kerberos认证账号等属性配置修改，需要重新更新该文件，并部署项目。

#4.测试
HBaseProviderApp测试服务
启动类HbaseKerberosProviderApp，声明KerberosObject初始化Bean，优先加装kerberos。

@SpringBootApplication(scanBasePackages = {"com.example.hbase.kerberos.provider"})
public class HbaseKerberosProviderApp {
    @Autowired
    private KerberosObject kerberosObject;

    public static void main(String[] args) {
        SpringApplication.run(HbaseKerberosProviderApp.class, args);
    }

}

说明：Spring Bean加装顺序可以通过@Order注解设置加载顺序，如果是自定义AutoConfiguration使用@AutoConfigureOrder。
当前项目使用hbase需要先进行kerberos认证，因此需要配置Bean的加载顺序，保障kerberos优先加载。

但是，在客户端服务中引入了两个starter组件，优先加载顺序实际根据主程序具体调用的某个starter中的bean优先加载。

#5.Q&A
##5.1.Q:guava-18.0.jar多个版本jar包冲突
R:
Description:

An attempt was made to call the method com.google.common.base.Preconditions.checkArgument(ZLjava/lang/String;Ljava/lang/Object;)V but it does not exist. Its class, com.google.common.base.Preconditions, is available from the following locations:

    jar:file:/D:/java-component/maven/repos/com/google/guava/guava/18.0/guava-18.0.jar!/com/google/common/base/Preconditions.class

It was loaded from the following location:

    file:/D:/java-component/maven/repos/com/google/guava/guava/18.0/guava-18.0.jar


Action:

Correct the classpath of your application so that it contains a single, compatible version of com.google.common.base.Preconditions

A:
```
        <!-- kerberos starter和hbase starter分别都引用了hadoop-common，两个hadoop-common包依赖guava冲突，
        排除即可；不用排除整个hadoop-common,否则造成krb5.conf不能正确读取 -->
        <!-- 自定义kerberos认证starter -->
        <dependency>
            <artifactId>kerberos-spring-boot-starter</artifactId>
            <groupId>com.example</groupId>
            <version>1.0.0-SNAPSHOT</version>
            <exclusions>
                <exclusion>
                    <artifactId>guava</artifactId>
                    <groupId>com.google.guava</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        
        <!-- 依赖新版本的guava -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <version>21.0</version>
            <artifactId>guava</artifactId>
        </dependency>
```
或

```
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-common</artifactId>
            <version>3.1.4</version>
            <exclusions>
                <exclusion>
                    <artifactId>guava</artifactId>
                    <groupId>com.google.guava</groupId>
                </exclusion>
            <exclusions>
        </dependency>

        <!-- 依赖新版本的guava -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <version>21.0</version>
            <artifactId>guava</artifactId>
        </dependency>
```


##5.2.Q:Can't get Kerberos realm 
R:Caused by: sun.security.krb5.KrbException: Cannot locate default realm
不能正常加载krb5.conf文件造成的错误。

A:
方案1：
可能是krb5.conf、hdfs.keytab、jaas.conf文件路径写错了，导致不能正确读文件中的属性信息。复制属性配置对于的
路径在本地访问验证，如果错误，修正即可。

方案2：
启动类编辑启动参数，示例如下：
IDEA工具栏，编辑当前服务选项，Run/Debug Configurations,HBaseProviderApp,Configuration,Environment,VM options:
-Djava.security.krb5.conf=D:\\krb5.conf
-Djava.security.krb5.kdc=hadoop-server-003:88
-Djava.security.krb5.realm=HADOOP.COM

或者根据以上属性自定义属性在启动类配置，例如：
System.setProperty("java.security.krb5.conf", "D:\\krb5.conf");
System.setProperty("java.security.krb5.kdc", "hadoop-server-003:88");
System.setProperty("java.security.krb5.realm", "HADOOP.COM");

说明：上述报错是Can't get Kerberos realm，是因为拿不到kdc和realm，需要指定krb5.conf文件，kdc地址，realm值。


##5.3.Q:hadoop集群配置了https后java客户端链接不上
A：hdfs-site.xml中加入如下配置：
```
<configuration>
    <property>
        <name>dfs.encrypt.data.transfer</name>
        <value>true</value>
    <description>
如果dfs.encrypt.data.transfer设置为true，则它将取代dfs.data.transfer.protection的设置，并强制所有连接必须使用专门的加密SASL握手。
    </description>    
    </property>
</configuration>
```

