# Zedata Scaffold

> author: [Mr-Glacier](https://github.com/Mr-Glacier)
>
> version: 1.0.0
>

## 代码生成器

代码生成器，用于快速生成代码  
位置: `src/test/java/com/zedata/project/CodeGenerator.java`  
需要填写数据库连接信息

```aidl
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

## 增加 验证码 captcha 服务

## 增加 邮件验证码服务

## 增加 短信验证码服务
