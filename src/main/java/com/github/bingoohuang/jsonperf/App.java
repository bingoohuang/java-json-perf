package com.github.bingoohuang.jsonperf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class App implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) throws  Exception {
      runTests(300, 1000, s1);
      runTests(300, 1000, s2);
      runTests(300, 10000, s1);
      runTests(300, 10000, s2);
      runTests(100, 1000, s1);
      runTests(100, 1000, s2);
      runTests(100, 10000, s1);
      runTests(100, 10000, s2);
  }

  public static void runTests(int c, int n, String s) throws  Exception {
      System.out.println(c + "线程，每个跑" + n + "次" + ", 解析JSON长度" + s.length() + ":");
      System.out.println("-----------------------------------");
      runTest("FASTJSON(1.2.78)", c, () -> fastjsonParse(s, n));
      runTest("GSON(2.8.9)", c, () -> gsonParse(s, n));
      runTest("Jackson(2.13.0)", c, () -> jacksonParse(s, n));
      System.out.println();
  }

    static String s1 = """
{
    "biz": "21LpX56tZr8f8YXrfyWo6vy9UpH",
    "data": {
        "signature": ""
    }
}
        """;
    static String s2 = """
{
    "biz": "21LpYu0DsGzhkvX44FQEUyyHv0G",
    "data": {
        "signature": "FrHT9UztlCe1ca5ZVQsLggZ4R7UfUiKUKMJxCH5ArX/XhbJ+lNqhUWpbWbusYFzNqG79Jo/a8titFn0Z9oAReEN0ugTxHQHutzIv2IXJhz5iSjrxVVuHjAYHH2r48AnHoQcro2Bj/ea5zn2sW9tBiWYnkKHZRw70+SJym45H7T2LNmuI1LrV30WvFsJEpfBkEGTDQRtChZyR72ogBf2+WDYCJPfg1chZGIl1KgcJClQwnVob/K/oVKCZtGkHhI8wo0IG4aYAS3p7qo0atg8OQj6YgxY2IO39r+BZXheMiiqCK41FXyMOMdyGfQM+BnBTv5YcVRCjA78l7nvLUhoBEtuL2VmdYaluCGu91B6QpxrElELojY9qRkkO8OUFN/QaQojJE1A489oiXRbf5d2/4TpYfyZ8v0C0RO88zVV3/Wqdx1rL2+uoSZOopYvzhAUeXXrzuPWAMQlAB2RDf3Lp4FX+MTz/dTUIqwVpx0QuUxhPJi1Z0ihrAPx+IEMvHViiYy1b/L55xF7cvfBDUl7a2uGec635R2qMPazBh9ymsprDyJh8l/jT5+nhQZP6vk8zLb++fqKSSDyNyHwn1vc3HvTfX2nGuFYxcgyLCFIngn9b7tDv1166A+Ak587R5a1BT4B95Awye3X+inzAp/NiUdMbrhMBY8NuwiOQ00RUrRWh0Geq373cwtxhqZPWP/LprExw30ZQW+KRfKN3TuGfNWVRPVF0JYp7tBjYb+rmVj74diG7bv24TGUx15Ju0it/MlWsUbuo38E70GkjvcBiG5SWvFYPNAZnoHXYNJ/DRhPpewiaKZxyHNn2BFCCvOuoAHPTse1VwZm0OxLut5wPQoIIdPL5BMUOY+Ezx6WkCk/GO9Bd4yb+izAjFI7rUY6CINOK4Kxu0pRKEUcid3dOLcLIW4/zTd9JsySUcHbpxOY85b+fHHvuuG6juTzm6vH7eAvRJheVU33ESBwyfWFGXEJiVDS92RoV66HVqxuEQGGTHrWmSDNbR/h8FsL6c4lWUFP6KJY9kMJeXmldapAi+YI/12ovlvvD09x+7BxjIhwBCxrHopVB4HYGbX2916JcHtfX9haC1LgAWueiteMIa770rL8D4bYpGHHwNaMeKVtcRRYr6yju35chbeewSl+EMJ6tiJr2D/3GkFjWmWwDq7O1yR4kykoAfPtssy20F9sRcDjgXNFPjOcK4/sk3q7HmL8pikCAupFtxBkhHrQCmj4m6Ea1m4KjOZmoAdSKjKQ5dCrz4przo8t+nZvubELWtR1Kc9OTBDkuBlYYy7glnU4MM/Tc70UBvTgcf27CEeujjsaqcKLkKx3mux9prm1eZvqR1nRrp9x6tIfJh/AJYh8LX8lAL8URNRuvJbsZNz2qG7OGfLlVdw0NnvQhOkQsBX/c0p/np4s3ADBG/yHRD3LY0vROliyex53FNz8WIHF3Rj8AserXQ/p+X3gepn8po0WQR60PALPcnZZKdvce1l9+n3vUJkIRVIACHpHHdmXN9eSR9ti6DGnUXxnGwDTaRPLFAeelVI3fASIw0Jk2vFovbSyuf5KhmhhnkKjsy0SIU0dlz25UNz/FzJbDTLLNjUQfAAoLuKVZAkxEfePGgiHoBvRzGbd4guGHBj2vWBlbcIuNYv4HtW842TpJC5LU+4q2EAhdmue83s4CZnGwUCTdnU4wKB6oZa+tkvHmxWklVnCgLXPmYXBmHP/oTdncSKGQxg=="
    }
}
        """;



  public static void runTest(String name, int c, Runnable r) throws  Exception {
    var service = Executors.newFixedThreadPool(c);

    var start = System.nanoTime();
    for (int j = 0; j < c; j++) {
      service.submit(r);
    }

    service.shutdown();
    service.awaitTermination(10, TimeUnit.MINUTES);

   var duration = Duration.ofNanos(System.nanoTime() - start);
    System.out.println(name + ": " + duration);
  }

  public static void fastjsonParse(String s, int n) {
    for (int i = 0; i < n; i++) {
      // https://github.com/alibaba/fastjson/releases
      JSON.parseObject(s, Rsp.class, Feature.DisableSpecialKeyDetect);
    }
  }

  // https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.0/com/google/gson/Gson.html
  // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
  // You can create a Gson instance by invoking new Gson() if the default configuration is all you need.
  static Gson gson=new Gson();

  public static void gsonParse(String s, int n) {
    for (int i = 0; i < n; i++) {
      gson.fromJson(s, Rsp.class);
    }
  }

  // https://fasterxml.github.io/jackson-databind/javadoc/2.6/com/fasterxml/jackson/databind/ObjectMapper.html
  // Mapper instances are fully thread-safe provided that ALL configuration of the instance occurs before ANY read or write calls.
  static ObjectMapper mapper=new ObjectMapper();

  public static void jacksonParse(String s, int n) {
    for (int i = 0; i < n; i++) {
        try {
            mapper.readValue(s, Rsp.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
  }

  @Data
  public static class Rsp {
    private String biz;
    private Value data;
  }

  @Data
  public static class Value {
    private String signature;
  }
}
