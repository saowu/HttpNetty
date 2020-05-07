# HttpNetty
>基于Netty的Http服务器(学习休闲所做)

### 技术特点
```
1.使用Netty作为http服务器
2.支持注解@Controller、@RequestMapping
3.maven项目管理
4.springboot目录结构
5.简洁的请求数据提取
6.可返回json、html
 ```
### 注解@Controller、@RequestMapping
```
@Controller(path = "/index")
public class IndexController {

    @RequestMapping(method = RequestMethod.POST, path = "/test")
    public String test(Map<String, Object> map) {
        return JSONObject.toJSONString(map);
    }
}
```
```
/Library/Java/JavaVirtualMachines/jdk-11.0.6.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=54309:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/saowu/Documents/IdeaProjects/HttpNetty/target/classes:/Users/saowu/.m2/repository/io/netty/netty-all/4.1.42.Final/netty-all-4.1.42.Final.jar:/Users/saowu/.m2/repository/com/alibaba/fastjson/1.2.62/fastjson-1.2.62.jar org.saowu.Application
 _   _ _   _         _   _      _   _
| | | | |_| |_ _ __ | \ | | ___| |_| |_ _   _
| |_| | __| __| '_ \|  \| |/ _ \ __| __| | | |
|  _  | |_| |_| |_) | |\  |  __/ |_| |_| |_| |
|_| |_|\__|\__| .__/|_| \_|\___|\__|\__|\__, |
              |_|                       |___/   Author：saowu
Netty Server : Annotation scanning completed
Netty Server : http://127.0.0.1:9000
Netty Server : 2020-05-03 10:43:32 GET -> /index/test4
Netty Server : 2020-05-03 10:43:46 GET -> /index/test4

Process finished with exit code 130 (interrupted by signal 2: SIGINT)

```

### 请求数据提取
- 将GET、PUOST、PUT、DELETE请求数据统一解析成Map,无论是`multipart/form-data`还是`application/json`
```
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
### 项目结构
```
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
    │   │           └── core
    │   │               ├── annotation
    │   │               │   ├── Controller.java
    │   │               │   └── RequestMapping.java
    │   │               ├── banner
    │   │               ├── config
    │   │               │   └── ContextConfig.java
    │   │               ├── pojo
    │   │               │   ├── RequestMethod.java
    │   │               │   ├── RouteInfo.java
    │   │               │   └── Template.java
    │   │               ├── server
    │   │               │   ├── BootServer.java
    │   │               │   └── HttpRequestHandler.java
    │   │               └── utils
    │   │                   ├── AnnotationUtils.java
    │   │                   ├── HttpRequestUtils.java
    │   │                   ├── IOUtils.java
    │   │                   ├── ReflexUtils.java
    │   │                   └── ResponseUtils.java
    │   └── resources
    │       └── templates
    │           └── index.html
    └── test
        └── java
            └── org
                └── saowu
                    └── AppTest.java

```
### siege压力测试
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

