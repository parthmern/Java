# Working how?

### Bean

* Managed by the Spring container
* Created, initialized, injected, and destroyed automatically by Spring
* It lives in the Spring ApplicationContext (the "container") and is available for dependency injection.

```java
@Configuration
public class MyConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

***

Spring uses beans so you don’t have to write manual code like:

```java
javaCopyEditPasswordEncoder encoder = new BCryptPasswordEncoder();
```

Instead, you write:

```java
javaCopyEdit@Autowired
private PasswordEncoder encoder;
```

And Spring **automatically injects** the bean you defined earlier.

<figure><img src="../.gitbook/assets/image (1) (1).png" alt=""><figcaption></figcaption></figure>

### execution flow ( authentication using spring security )

<img src="../.gitbook/assets/file.excalidraw (1) (1) (1).svg" alt="" class="gitbook-drawing">

[https://youtu.be/qvAoUVXgpZg?si=Y8XI\_cehMbay3Gxj](https://youtu.be/qvAoUVXgpZg?si=Y8XI_cehMbay3Gxj) \
this video good
