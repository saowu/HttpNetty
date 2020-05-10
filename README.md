# HttpNetty
> 我不想学Struts2绝不是因为它有漏洞，因为我自己写的漏洞更多......


👉[Blog](https://saowu.top/blog/dnlLkMdxz/)

## 技术特点
```
1.使用Netty作为http服务器
2.支持注解@Controller、@RequestMapping
3.利用maven项目管理
4.简洁的请求数据提取
6.Java反射技术
 ```
## 启动Banner
```
 _   _ _   _         _   _      _   _
| | | | |_| |_ _ __ | \ | | ___| |_| |_ _   _
| |_| | __| __| '_ \|  \| |/ _ \ __| __| | | |
|  _  | |_| |_| |_) | |\  |  __/ |_| |_| |_| |
|_| |_|\__|\__| .__/|_| \_|\___|\__|\__|\__, |
              |_|    Author：saowu       |___/

```

## 注解@Controller、@RequestMapping
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
Http Netty : 2020-05-09 04:34:34 GET -> /
Http Netty : 2020-05-09 04:34:34 GET -> /static/css
Http Netty : 2020-05-09 04:34:34 GET -> /static/js
Http Netty : 2020-05-09 04:34:34 GET -> /static/img
Http Netty : 2020-05-09 04:35:30 POST -> /index/test
```

## 请求数据提取
- 将GET、PUOST、PUT、DELETE请求数据统一解析成Map,无论是`application/x-www-form-urlencoded`、`multipart/form-data`还是`application/json`
```
     @RequestMapping(method = RequestMethod.POST, path = "/test")
     public String test(Map<String, Object> map) {
        HashMap<String, String> fileInfo = new HashMap<>();
        for (String key : map.keySet()) {
            //获取文件对象并保存
            String path = IOUtils.saveFileUpload(key, map.get(key));
            fileInfo.put(key, path);
        }
        return JSONObject.toJSONString(fileInfo);
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
## 项目结构
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
    │   │               │   ├── SCSS.java
    │   │               │   ├── SIMG.java
    │   │               │   ├── SJS.java
    │   │               │   └── Template.java
    │   │               ├── server
    │   │               │   ├── BootServer.java
    │   │               │   ├── HttpRequestHandler.java
    │   │               │   └── InitCenter.java
    │   │               └── utils
    │   │                   ├── AnnotationUtils.java
    │   │                   ├── HttpRequestUtils.java
    │   │                   ├── IOUtils.java
    │   │                   ├── ReflexUtils.java
    │   │                   └── ResponseUtils.java
    │   └── resources
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

23 directories, 27 files
```
## Browser Console
![](https://saowu.top/blog/post-images/1589013914038.png)




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