# authN and authZ

### bcrypt for hashing

* bcrypt is part of springboot security ecosystem

```
implementation 'org.springframework.boot:spring-boot-starter-security:3.2.5'
```

it has too many things ( not only bcrypt)

<figure><img src="../.gitbook/assets/image (6).png" alt=""><figcaption></figcaption></figure>

error ( spring boot is going to do the const. injection)&#x20;

it does not know about bcrypt password encoder&#x20;

{% embed url="https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt" %}

you have to configure this

<figure><img src="../.gitbook/assets/image (7).png" alt=""><figcaption></figcaption></figure>

now that issue is solved\
why it is like that - because `new BCryptPasswordEncoder();`  has too many constrcutors inside so we have choice to select with different params liek for algorithm, method, rounds etc...

```
bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword())
```

-> the moment you install spring security in your app \
all of your routes become protected means required authenticated req\
![](<../.gitbook/assets/image (8).png>) but signup is the way to authenticate right? so we have to make sure that any signup and login req are independent of authentication because they are mean/root for authentication

solve:&#x20;

```java

@Configuration // spring knows about this class and
                // will handle everything that we are going to mention inside class
@EnableWebSecurity  // enables Spring Security's web security features, you can add filter
public class SpringSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth", "/api/v1/auth/**").permitAll() // allow public access
                        .anyRequest().authenticated() // secure all other endpoints
                );
        return http.build();
    }
    
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

```
