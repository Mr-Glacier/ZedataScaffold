package com.zedata.project;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

public class CodeGenerator {

    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 这里使用的默认引擎，就没有setTemplateEngine，如果使用其他的引擎，还需要添加相关的依赖

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        //String projectPath = System.getProperty("user.dir");
        gc.setOutputDir("E:\\ZKZD2025\\ZedataScaffold\\src\\main\\java");
        gc.setAuthor("Mr-Glacier");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(true); //重新生成时文件是否覆盖

        gc.setServiceName("%sService");    //去掉Service接口的首字母I
        gc.setIdType(IdType.ID_WORKER_STR); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        // 配置文件中的数据源配置和这个无关，这里需要重新配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");//mysql-connector-java 6及以上，加cj
        dsc.setUsername("root");
        dsc.setPassword("");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        //\zedata\com\crawlerdatasystem\
        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("project"); //模块名
        pc.setParent("com.zedata");
        pc.setController("controller.general");
        pc.setEntity("entity.po");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 其他的配置确定后，以后需要生成哪个表，改下面这句话就可以了
        strategy.setInclude("sys_user");//与数据库中的表名对应
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);
        // 6、执行
        mpg.execute();
    }
}
