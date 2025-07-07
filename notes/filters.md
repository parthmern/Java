# Filters

\
JWT token access
----------------

<figure><img src=".gitbook/assets/image (34).png" alt=""><figcaption></figcaption></figure>

```java
@GetMapping("/validate")
    public ResponseEntity<?> isValidate(HttpServletRequest request){
        List<Cookie> cookies = List.of(request.getCookies());
        if(!cookies.isEmpty()){
            for (Cookie cookie : cookies){
                System.out.println(cookie.getName() + " " + cookie.getValue());
            }
        }else {
            System.out.println("cookie is empty");
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
```

#### spring interceptor <a href="#spring-interceptor" id="spring-interceptor"></a>

( learn from online article )

#### spring filters <a href="#spring-filters" id="spring-filters"></a>

<img src=".gitbook/assets/file.excalidraw (1) (1).svg" alt="" class="gitbook-drawing">
