# oAuth | JWT

* login via email pswd
* loig via google/gb/linkedin
* login through sso
* login via movile sms otp
  * <mark style="color:yellow;">OAuth (Open Authorization) is the one who is behind it to maintain that all</mark>

components that provide oAuth function\
1\) resource owner ( user itself )\
2\) client ( bookmyshow, leetcode.com , paytm.com)\
3\) resource server ( serrver of bms, leetcode which has user info )\
4\) auth service ( google/fb/github server )

<img src="../.gitbook/assets/file.excalidraw (1) (1) (1) (1).svg" alt="" class="gitbook-drawing">

* here gogole is working as Auth server
* but if you want to replace this part with own server then you can make your own auth server\
  with login via email/password

<img src="../.gitbook/assets/file.excalidraw (2) (1).svg" alt="" class="gitbook-drawing">

three type of auth\
1\) token based\
2\) session based\
3\) hybrid ( token+session)\\

***

<mark style="background-color:red;">**session based**</mark>\
Hotstart ( max 4 device login )\
when you try to login in 5th device it prompts you that you have "4 device already loggedin" -> session based auth\
when you login, hotstart gives token to client and it stores in DB ( like token information, when was it geenrated, location where it is generated etc. ) this token known as session id\
this session id is present with user + in backend(db)\
\
cleint is goign to store that session id somewhere and when it send req it sends it to hotstar server then it check if session id present in DB or not if yes then you are authorized else not\
\
100XDEV edtech platform ( 1 device login )\
when you login from 2nd device, the moment you loggedin, 1st loggined device got loggedout automatically means that first session id got deactivated

***

<mark style="background-color:yellow;">**Token based auth**</mark>

this is stateless means we are not going to store the state in DB\
token gives to user and it has all information about user ex. JWT\
the bestway to store this is with <mark style="color:purple;">http only cookie</mark> ( you are not able to see it on client side with js or anything but you can access it on server side )

jwt- json web token

<img src="../.gitbook/assets/file.excalidraw (3).svg" alt="" class="gitbook-drawing">

### implementation

```java
implementation 'io.jsonwebtoken:jjwt-api:0.12.5'
    implementation 'io.jsonwebtoken:jjwt-impl:0.12.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.5'
```

`applicaiton.properties` file

<figure><img src="../.gitbook/assets/image (3).png" alt=""><figcaption></figcaption></figure>

{% tabs %}
{% tab title="JwtService" %}
```java
@Service // Marks this class as a Spring service bean
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}") // Injects value from application.properties (e.g., jwt.expiry=3600)
    private int expiry;

    @Value("${jwt.secret}") // Injects JWT secret key from application.properties
    private String SECRET;

    /**
     * This method generates a JWT token with custom claims and user subject.
     * 
     * @param payload   A map of additional claims (e.g., email, phone number)
     * @param username  The subject (main identifier) of the token
     * @return          A signed JWT as a string
     */
    private String createToken(Map<String, Object> payload, String username){

        Date now = new Date(); // current timestamp
        Date expiryDate = new Date(now.getTime() + expiry * 1000L); // token expiration time

        // Creates a HMAC SHA-256 key from the secret string
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        // Build and return the JWT string
        return Jwts.builder()
                .claims(payload)                     // set custom claims
                .issuedAt(new Date(System.currentTimeMillis())) // set issue time
                .expiration(expiryDate)             // set expiry time
                .subject(username)                  // set subject (e.g., username)
                .signWith(key)                      // sign the token with the secret key
                .compact();                         // serialize it to a JWT compact string
    }

    /**
     * This is a method from CommandLineRunner interface.
     * It runs automatically after the Spring Boot application context is loaded.
     * Used here for testing JWT token generation at startup.
     */
    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> mp = new HashMap<>(); // prepare custom claims
        mp.put("email", "parth@gmail.com");
        mp.put("phoneNumber", "1234567890");

        // Generate a JWT token using the custom claims and a subject ("parth")
        String result = createToken(mp, "parth");

        // Print the generated token to the console
        System.out.println("Generated Token: " + result);
    }
}

```
{% endtab %}
{% endtabs %}

<figure><img src="../.gitbook/assets/image (1) (1) (1) (1).png" alt=""><figcaption></figcaption></figure>

<figure><img src="../.gitbook/assets/image (2) (1).png" alt="" width="375"><figcaption></figcaption></figure>

see from token we are able to get data so this token should not be leaked\
so if you loose your jwt token then people can decrypt it

{% tabs %}
{% tab title="JwtService" %}
```java

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    // method creates brand new JWT token based on payload
    private String createToken(Map<String, Object> payload, String username){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+expiry*1000L);

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(username)   // subject = username
                .signWith(key)
                .compact();
    }

    public Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }


    public Claims extractAllPayloads(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllPayloads(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, String emailBasedOnDb) {
        final String userEmailFetchedFromToken = extractEmail(token);
        return (userEmailFetchedFromToken.equals(emailBasedOnDb)) && !isTokenExpired(token);
    }

    public Object extractPayload(String token, String payloadKey) {
        Claims claim = extractAllPayloads(token);
        return (Object) claim.get(payloadKey);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> mp = new HashMap<>();
        mp.put("email", "parth@gmail.com");
        mp.put("phoneNumber", "1234567890");
        String result = createToken(mp, "parth");
        System.out.println("Generated Token: " + result);
        System.out.println(extractPayload(result, "email").toString());

    }
}

```
{% endtab %}
{% endtabs %}

-Object as class in java - [https://chatgpt.com/share/6862758f-b25c-8010-8769-e7a9f35236f3](https://chatgpt.com/share/6862758f-b25c-8010-8769-e7a9f35236f3)\
\- when int vs Integer ? - [https://semih-turan.medium.com/understanding-the-difference-between-int-and-integer-in-java-300f24333ef4](https://semih-turan.medium.com/understanding-the-difference-between-int-and-integer-in-java-300f24333ef4)

### UserDetailsService - spring security

{% embed url="https://medium.com/@ihor.polataiko/spring-security-guide-part-4-out-of-the-box-implementations-http-basic-with-userdetailsservice-9ec3360c66ed" %}

<figure><img src="../.gitbook/assets/image (1) (1) (1).png" alt=""><figcaption></figcaption></figure>

-spring security works on UserDetails polymorphic type for auth

```java

public class AuthPassengerDetails extends Passenger implements UserDetails {

    private String username;

    private String password;

    public AuthPassengerDetails(Passenger passenger){
        this.username = passenger.getEmail();   // unique
        this.password = passenger.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    // below methods are not much imp due to business logic of ours

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```

### AuthenticationProvider

<img src="../.gitbook/assets/file.excalidraw (2).svg" alt="" class="gitbook-drawing">

```java
// SpringSecurity.java class

@Configuration // spring knows about this class and
                // will handle everything that we are going to mention inside class
@EnableWebSecurity  // enables Spring Security's web security features, you can add filter
public class SpringSecurity {
    
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth", "/api/v1/auth/**").permitAll() // allow public access
                        .anyRequest().authenticated() // secure all other endpoints
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

```

( too complex learn from somewhere else \_)
