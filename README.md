# Kotling Boot(Kotlin + Spring Boot)をGKEで動かす
## 概要
- `Kotlin` + `Spring Boot`を勝手に`Kotling Boot(KotlingBoot)`と呼んでみる
  - 2018/08/26時点で`kotlingboot`, `"kotling boot"`を検索してヒットしなかったので、しょうもなさすぎて誰も公には名乗っていないっぽい
    - ![image](https://user-images.githubusercontent.com/8069859/44628454-e5b8d500-a97a-11e8-86b2-18fc7342b16a.png)
    - ![image](https://user-images.githubusercontent.com/8069859/44628463-05e89400-a97b-11e8-88ed-9ae5bdecc45d.png)
- `Kotling Boot`で`Hello World`を作り、GKEで動かしてみた
- 地味に`WebFlux`を使ってるので、`Netty`のイベントループで動いている

## GitHub
- https://github.com/sugikeitter/KotlingBoot

## 参考
### 公式ドキュメント
- [コンテナ化されたウェブ アプリケーションのデプロイ  |  Kubernetes Engine のドキュメント  |  Google Cloud](https://cloud.google.com/kubernetes-engine/docs/tutorials/hello-app)
- [23. WebFlux framework](https://docs.spring.io/spring/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html)
### Qiita
- [Kotlin with Spring Boot 1.5で簡単なRest APIを実装する - Qiita](https://qiita.com/rubytomato@github/items/7d4bb10ca3779ab3277c)
- [Spring WebFlux の概要を理解する - Qiita](https://qiita.com/kimullaa/items/33667b80ca315331d4da)
- [Kotlin文法 - データクラス, ジェネリクス - Qiita](https://qiita.com/k5n/items/18adb5c3503a54e96c22)

## 必要なファイル
```bash
$ tree .
.
├── build.gradle
├── Dockerfile
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── kotlin
    │   │   └── com
    │   │       └── sugikeitter
    │   │           └── kotlinboot
    │   │               ├── controller
    │   │               │   └── HelloController.kt
    │   │               ├── data
    │   │               │   └── Hello.kt
    │   │               └── KotlinbootApplication.kt
    │   └── resources
    │       └── application.yml
    └── test
        └── kotlin
            └── com
                └── sugikeitter
                    └── kotlinboot
                        └── KotlinbootApplicationTests.kt
16 directories, 12 files
```

## Kotling Bootアプリの作成
### Project作成
- [IntellJ IDEA Ulitimate](https://www.jetbrains.com/idea/)の`Spring Intializer`からプロジェクトを作成する機能を使う
  - "Create New Project"
  ![image](https://user-images.githubusercontent.com/8069859/44628645-d76cb800-a97e-11e8-8f3d-873d00ad16e9.png)
  - "Spring Initializer"
  ![image](https://user-images.githubusercontent.com/8069859/44628666-709bce80-a97f-11e8-90ca-e84ed1727a67.png)
  - "Type: `Gradle1`", "Language: `Kotlin`"
  ![image](https://user-images.githubusercontent.com/8069859/44628720-983f6680-a980-11e8-8c2e-cd36c7151545.png)
  - "Reacrive Web"
  ![image](https://user-images.githubusercontent.com/8069859/44628690-dab47380-a97f-11e8-924b-99b6f78f235d.png)
  - "FINISH"
  ![image](https://user-images.githubusercontent.com/8069859/44628723-a5f4ec00-a980-11e8-98f4-479bed7c711a.png)
  - "OK"
  ![image](https://user-images.githubusercontent.com/8069859/44628745-1439ae80-a981-11e8-9aca-380ae3d98640.png)
- ほぼ動くものが完成

### Contllerクラス作成
- `src/main/kotlin/com/sugikeitter/kotlingboot/controller`パッケージを作成し、`HelloControlle.kt`を作成
  - クラスに`@RestController`をつけることでURLマッピング対象クラスに
  - メソッドに`@GetMapping("/hello")`をつけることで、`GET /hello`のエントリポイントに対応
  ```kotlin
  package com.sugikeitter.kotlingboot.controller

  import com.sugikeitter.kotlingboot.data.Hello
  import org.springframework.web.bind.annotation.GetMapping
  import org.springframework.web.bind.annotation.RestController

  @RestController
  class HelloController {
      @GetMapping("/hello")
      fun hell(): Hello {
          return Hello()
      }
  }
  ```

### datクラス作成
- `src/main/kotlin/com/sugikeitter/kotlingbootdata`パッケージを作成し、`Hello.kt`を作成
  - `Kotlin`のdataクラスの機能を利用
  ```kotlin
  package com.sugikeitter.kotlingboot.data

  import java.util.Date

  data class Hello (
          val message: String = "Hello. World.",
          val date: Date = Date()
  )
  ```

### 設定ファイル作成
- 今回は`src/main/resources/application.yml`を作成し、ここに設定を追記する
  - 利用するポートを`8888`にしてみた
  ```yaml
  server:
    port: 8888
  ```
### 動作確認
- `Gradle`プラグインの`bootRun`を使ってビルド&起動
```bash
$ ./gradlew bootRun

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.4.RELEASE)

2018-08-26 23:14:33.612  INFO 10271 --- [           main] c.s.k.KotlingBootApplicationKt           : Starting KotlingBootApplicationKt on MBP-13UAU-034 with PID 10271 (/Users/kesugimo/IdeaProjects/kotlingboot/build/classes/kotlin/main started by kesugimo in /Users/kesugimo/IdeaProjects/kotlingboot)
2018-08-26 23:14:33.619  INFO 10271 --- [           main] c.s.k.KotlingBootApplicationKt           : No active profile set, falling back to default profiles: default
2018-08-26 23:14:33.679  INFO 10271 --- [           main] onfigReactiveWebServerApplicationContext : Refreshing org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext@6a400542: startup date [Sun Aug 26 23:14:33 JST 2018]; root of context hierarchy
2018-08-26 23:14:34.729  INFO 10271 --- [           main] s.w.r.r.m.a.RequestMappingHandlerMapping : Mapped "{[/hello],methods=[GET]}" onto public com.sugikeitter.kotlingboot.data.Hello com.sugikeitter.kotlingboot.controller.HelloController.hell()
2018-08-26 23:14:34.798  INFO 10271 --- [           main] o.s.w.r.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.reactive.resource.ResourceWebHandler]
2018-08-26 23:14:34.799  INFO 10271 --- [           main] o.s.w.r.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.reactive.resource.ResourceWebHandler]
2018-08-26 23:14:34.969  INFO 10271 --- [           main] o.s.w.r.r.m.a.ControllerMethodResolver   : Looking for @ControllerAdvice: org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext@6a400542: startup date [Sun Aug 26 23:14:33 JST 2018]; root of context hierarchy
2018-08-26 23:14:35.436  INFO 10271 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-08-26 23:14:35.535  INFO 10271 --- [ctor-http-nio-1] r.ipc.netty.tcp.BlockingNettyContext     : Started HttpServer on /0:0:0:0:0:0:0:0:8888
2018-08-26 23:14:35.535  INFO 10271 --- [           main] o.s.b.web.embedded.netty.NettyWebServer  : Netty started on port(s): 8888
2018-08-26 23:14:35.539  INFO 10271 --- [           main] c.s.k.KotlingBootApplicationKt           : Started KotlingBootApplicationKt in 2.414 seconds (JVM running for 2.805)
<==========---> 80% EXECUTING [12s]
> :bootRun

```

- リクエストが帰ってくることを確認
```
$ curl http://localhost:8888/hello
{"message":"Hello. World.","date":"2018-08-26T14:15:02.436+0000"}
```

## Dockerfileの作成
- 最初にjavaビルド環境用のimageを作成し、`Gradle`でビルドしてjarファイルを作成する
- 必要なjarファイルだけを配備してサイズ小さくし、container起動時にjarを起動させるimageを作成
```docker
FROM openjdk:8-slim
RUN mkdir -p /opt/kotlingboot
ADD . /opt/kotlingboot
WORKDIR /opt/kotlingboot
RUN ./gradlew build

FROM openjdk:8-slim
COPY --from=0 /opt/kotlingboot/build/libs/kotlingboot-0.0.1-SNAPSHOT.jar /opt/app.jar
ENV PORT 8888
CMD ["java", "-jar", "/opt/app.jar"]
```
