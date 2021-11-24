# json perf

- Hardware: MacBookPro15, 6-Core Intel Core i7,  2.2 GHz, L2 256 KB, L3 9 MB, Hyper-Threading, Memory: 16 GB
- Java: jdk-17.0.1_macos-x64

Result:

线程数|每个线程跑次数|解析长度|解析工具|解析时间
---|---|---|----|---
300|1000|158|FASTJSON(1.2.78)|0.491296628S
300|1000|158|GSON(2.8.9)|0.513086866S
300|1000|158|Jackson(2.13.0)|0.197209585S
300|1000|1374|FASTJSON(1.2.78)|1.318842229S
300|1000|1374|GSON(2.8.9)|0.585861418S
300|1000|1374|Jackson(2.13.0)|0.525542182S
300|10000|158|FASTJSON(1.2.78)|1.782607019S
300|10000|158|GSON(2.8.9)|1.521178722S
300|10000|158|Jackson(2.13.0)|1.25975688S
300|10000|1374|FASTJSON(1.2.78)|14.381912312S
300|10000|1374|GSON(2.8.9)|5.670349488S
300|10000|1374|Jackson(2.13.0)|5.405553758S
100|1000|158|FASTJSON(1.2.78)|0.085133603S
100|1000|158|GSON(2.8.9)|0.059992445S
100|1000|158|Jackson(2.13.0)|0.060392925S
100|1000|1374|FASTJSON(1.2.78)|0.599349366S
100|1000|1374|GSON(2.8.9)|0.222597223S
100|1000|1374|Jackson(2.13.0)|0.205741341S
100|10000|158|FASTJSON(1.2.78)|0.744965355S
100|10000|158|GSON(2.8.9)|0.584015844S
100|10000|158|Jackson(2.13.0)|0.538882202S
100|10000|1374|FASTJSON(1.2.78)|5.558185677S
100|10000|1374|GSON(2.8.9)|2.197600657S
100|10000|1374|Jackson(2.13.0)|2.040003751S


Original:

```sh
300线程，每个跑1000次, 解析JSON长度158:
-----------------------------------
FASTJSON(1.2.78): PT0.491296628S
GSON(2.8.9): PT0.513086866S
Jackson(2.13.0): PT0.197209585S

300线程，每个跑1000次, 解析JSON长度1374:
-----------------------------------
FASTJSON(1.2.78): PT1.318842229S
GSON(2.8.9): PT0.585861418S
Jackson(2.13.0): PT0.525542182S

300线程，每个跑10000次, 解析JSON长度158:
-----------------------------------
FASTJSON(1.2.78): PT1.782607019S
GSON(2.8.9): PT1.521178722S
Jackson(2.13.0): PT1.25975688S

300线程，每个跑10000次, 解析JSON长度1374:
-----------------------------------
FASTJSON(1.2.78): PT14.381912312S
GSON(2.8.9): PT5.670349488S
Jackson(2.13.0): PT5.405553758S

100线程，每个跑1000次, 解析JSON长度158:
-----------------------------------
FASTJSON(1.2.78): PT0.085133603S
GSON(2.8.9): PT0.059992445S
Jackson(2.13.0): PT0.060392925S

100线程，每个跑1000次, 解析JSON长度1374:
-----------------------------------
FASTJSON(1.2.78): PT0.599349366S
GSON(2.8.9): PT0.222597223S
Jackson(2.13.0): PT0.205741341S

100线程，每个跑10000次, 解析JSON长度158:
-----------------------------------
FASTJSON(1.2.78): PT0.744965355S
GSON(2.8.9): PT0.584015844S
Jackson(2.13.0): PT0.538882202S

100线程，每个跑10000次, 解析JSON长度1374:
-----------------------------------
FASTJSON(1.2.78): PT5.558185677S
GSON(2.8.9): PT2.197600657S
Jackson(2.13.0): PT2.040003751S
```

Running environment:

```sh
$ system_profiler SPHardwareDataType
Hardware:

    Hardware Overview:

      Model Name: MacBook Pro
      Model Identifier: MacBookPro15,1
      Processor Name: 6-Core Intel Core i7
      Processor Speed: 2.2 GHz
      Number of Processors: 1
      Total Number of Cores: 6
      L2 Cache (per Core): 256 KB
      L3 Cache: 9 MB
      Hyper-Threading Technology: Enabled
      Memory: 16 GB
      System Firmware Version: 1715.40.15.0.0 (iBridge: 19.16.10549.0.0,0)
      OS Loader Version: 540.40.4~45
      Serial Number (system): C02XREXAJG5H
      Hardware UUID: FE7F82ED-B431-55D2-A040-8F6D0AD7E12B
      Provisioning UDID: FE7F82ED-B431-55D2-A040-8F6D0AD7E12B
      Activation Lock Status: Enabled
```

build:

```sh
$ export JAVA_HOME=/Users/bingoobjca/run/jdk-17.0.1.jdk/Contents/Home                                                                  
$ java -version                                                                                  
java version "17.0.1" 2021-10-19 LTS
Java(TM) SE Runtime Environment (build 17.0.1+12-LTS-39)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.1+12-LTS-39, mixed mode, sharing)
$ mvn clean package -DskipTests
$ java -jar target/json-perf-1.jar
```