# Zedata Scaffold

> author: [Mr-Glacier](https://github.com/Mr-Glacier)
>
> version: 1.0.0
>

## 代码生成器

代码生成器，用于快速生成代码  
位置: `src/test/java/com/zedata/project/CodeGenerator.java`  
需要填写数据库连接信息

```
        // 3、数据源配置
        // 配置文件中的数据源配置和这个无关，这里需要重新配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");//mysql-connector-java 6及以上，加cj
        dsc.setUsername("root");
        dsc.setPassword("");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
```

并且可以指定生成的文件路径,只是作为初步使用,后期会进一步完善

## 构建 oauth 鉴权服务
> 这里主要介绍一下 oauth 鉴权服务,依托于 spring security 和 JWT 

#### 1.引入依赖
```xml
 <!-- JWT相关库 -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.11.5</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope>
        </dependency>
        <!-- JWT相关库 end -->
```
#### 2.配置文件
在application.yml 文件中配置,关于 jwt 的配置,密钥以及过期时间
```yaml
my-jwt:
  secret: UBAX9AySVCpXnzG4YFkeEZC9iTcFwKxa9I1L0n3A2as= # jwt secret
  expirationTime: 8640000 # 10days
```
#### 3.配置代码

## 增加 验证码 captcha 服务

## 增加 邮件验证码服务

## 增加 短信验证码服务
