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
##### 核心的类一共是5个  #####
**SecurityConfig** :  
    1.用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器  
    2.配置一些认证的过滤器链  
    3.提供密码加密方式   
**AuthUserService** :  用于根据用户名获取用户信息,实现了UserDetailsService接口,自定义实现了 loadUserByUsername 方法  
**JwtTokenUtil** : 用于生成和解析JWT token的工具类  
**JwtAuthenticationFilter** :  在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录。
**RestAuthenticationEntryPoint** :  用于处理未登录或token失效时返回401异常
**RestfulAccessDeniedHandler** :  用于处理匿名用户访问无权限资源时返回403异常

## 增加 验证码 captcha 服务

## 增加 邮件验证码服务

## 增加 短信验证码服务
