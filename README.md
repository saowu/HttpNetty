# HttpNetty
> 我不想用Struts2绝不是因为它有漏洞，因为我自己写的漏洞更多......


👉[Blog](https://saowu.top/blog/dnlLkMdxz/)

## 技术特点
```text
1.使用Netty作为http服务器
2.支持注解@Controller、@RequestMapping等
3.利用maven项目管理
4.简洁的请求数据提取
6.HikariCP连接池
7.依赖注入（反射实现）
 ```
## 启动Banner
```shell
 _   _ _   _         _   _      _   _
| | | | |_| |_ _ __ | \ | | ___| |_| |_ _   _
| |_| | __| __| '_ \|  \| |/ _ \ __| __| | | |
|  _  | |_| |_| |_) | |\  |  __/ |_| |_| |_| |
|_| |_|\__|\__| .__/|_| \_|\___|\__|\__|\__, |
              |_|    Author：saowu       |___/

```

## 注解@Controller、@RequestMapping
```java
@Controller(path = "/index")
public class IndexController {

    @RequestMapping(method = RequestMethod.POST, path = "/test")
    public String test(Map<String, Object> map) {
        return JSONObject.toJSONString(map);
    }
}
```
```shell
Http Netty : 2020-05-09 04:34:34 GET -> /
Http Netty : 2020-05-09 04:34:34 GET -> /static/css
Http Netty : 2020-05-09 04:34:34 GET -> /static/js
Http Netty : 2020-05-09 04:34:34 GET -> /static/img
Http Netty : 2020-05-09 04:35:30 POST -> /index/test
```

## 请求数据提取
- 将GET、PUOST、PUT、DELETE请求数据统一解析成Map,无论是`application/x-www-form-urlencoded`、`multipart/form-data`还是`application/json`
### 解析 application/x-www-form-urlencoded、application/json等
```java
     @RequestMapping(method = RequestMethod.POST, path = "/test")
     public String test(Map<String, Object> map) {
        return JSONObject.toJSONString(map);
     }
 
     @RequestMapping(method = RequestMethod.GET, path = "/test1")
     public String test1(Map<String, Object> map) {
         return JSONObject.toJSONString(map);
     }
 
     @RequestMapping(method = RequestMethod.PUT, path = "/test2")
     public String test2(Map<String, Object> map) {
         return JSONObject.toJSONString(map);
     }
 
     @RequestMapping(method = RequestMethod.DELETE, path = "/test3")
     public String test3(Map<String, Object> map) {
         return JSONObject.toJSONString(map);
     }

```
### 解析 multipart/form-data
```java
     @RequestMapping(method = RequestMethod.POST, path = "/uploadfile")
     public String uploadfile(Map<String, Object> map) {
        HashMap<String, String> fileInfo = new HashMap<>();
        for (String key : map.keySet()) {
            //获取文件对象并保存
            String path = IOUtils.saveFileUpload(key, map.get(key));
            fileInfo.put(key, path);
        }
        return JSONObject.toJSONString(fileInfo);
     }
```

## 依赖注入 @Autowired
```java
@Service
public class TestService {
    @Autowired
    private TestDao testDao;

    public List<Files> selectAll() {
        List<Files> filesList = testDao.selectAll();
        return filesList;
    }

}
```


## 数据库连接池
### hikari.properties
```text
jdbcUrl=jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8
driverClassName=com.mysql.jdbc.Driver
dataSource.user=root
dataSource.password=123456
dataSource.connectionTimeout=30000
dataSource.idleTimeout=600000
dataSource.maxLifetime=1800000
dataSource.maximumPoolSize=30
```
### Entity Class
```java
@Entity
public class Files {
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "type")
    private String type;

    public String getId() {
        return id;
    }

  /*Getter、Setter*/
}

```
### demo
```java
@RequestMapping(method = RequestMethod.GET, path = "/dbtest")
    public String test5(Map<String, Object> map) {
        try {
            Connection connection = ContextConfig.poolUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_files");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Files> filesList = new ResultSetMapper<Files>().mapRersultSetToObject(resultSet, Files.class);
            return JSONObject.toJSONString(filesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
```





## 项目结构
```shell script
.
├── README.md
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org
    │   │       └── saowu
    │   │           ├── Application.java
    │   │           ├── controller
    │   │           │   └── IndexController.java
    │   │           ├── core
    │   │           │   ├── annotation
    │   │           │   │   ├── Column.java
    │   │           │   │   ├── Controller.java
    │   │           │   │   ├── Entity.java
    │   │           │   │   └── RequestMapping.java
    │   │           │   ├── banner
    │   │           │   ├── config
    │   │           │   │   └── ContextConfig.java
    │   │           │   ├── pojo
    │   │           │   │   ├── RequestMethod.java
    │   │           │   │   ├── RouteInfo.java
    │   │           │   │   ├── SCSS.java
    │   │           │   │   ├── SIMG.java
    │   │           │   │   ├── SJS.java
    │   │           │   │   └── Template.java
    │   │           │   ├── server
    │   │           │   │   ├── BootServer.java
    │   │           │   │   ├── HttpRequestHandler.java
    │   │           │   │   └── InitCenter.java
    │   │           │   └── utils
    │   │           │       ├── AnnotationUtils.java
    │   │           │       ├── HttpRequestUtils.java
    │   │           │       ├── IOUtils.java
    │   │           │       ├── PoolUtils.java
    │   │           │       ├── ReflexUtils.java
    │   │           │       ├── ResponseUtils.java
    │   │           │       └── ResultSetMapper.java
    │   │           └── entity
    │   │               └── Files.java
    │   └── resources
    │       ├── hikari.properties
    │       ├── static
    │       │   ├── css
    │       │   │   └── index.css
    │       │   ├── img
    │       │   │   └── picture.png
    │       │   └── js
    │       │       └── index.js
    │       ├── templates
    │       │   └── index.html
    │       └── upload
    └── test
        └── java
            └── org
                └── saowu
                    └── AppTest.java

24 directories, 33 files

```

## siege压力测试
`siege -c 200 -r 100 http://127.0.0.1:9000/index/test1`
```
{	"transactions":			       19855,
	"availability":			       99.28,
	"elapsed_time":			       41.73,
	"data_transferred":		        0.04,
	"response_time":		        0.31,
	"transaction_rate":		      475.80,
	"throughput":			        0.00,
	"concurrency":			      149.54,
	"successful_transactions":	       19855,
	"failed_transactions":		         145,
	"longest_transaction":		       19.77,
	"shortest_transaction":		        0.00
}
```
那当然是我太菜了，不是netty的问题
