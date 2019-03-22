## JPA + Elasticsearch 연습
---

## Elasticsearch로 로그전송 추가
``` bash
http://woowabros.github.io/tools/2019/02/15/controller-log.html 
우아한형제들 기술블로그를 참고하여 연습했음을 알려드립니다.
elasticsearch 6.6.2 버전을 사용중입니다.
```


``` bash
kibana에서 시각화하여 간단하게 보기위해 index template을 추가하였습니다.

- controller-index.json
{
    "index_patterns" : [
        "controller-log-*"
    ],
    "mappings" : {
        "log" : {
            "properties" : {
                "httpMethod" : {
                    "type" : "keyword"
                },
                "urlPattern" : {
                    "type" : "keyword"
                },
                "requestedAt" : {
                    "type": "date"
                }
             }
         }
    }
}

$ cd {controller-index.json이 있는 path} 로 이동
$ curl -XPUT "http://localhost:9200/_template/controller-log" -H "Content-Type: application/JSON" -d @controller-index.json
```