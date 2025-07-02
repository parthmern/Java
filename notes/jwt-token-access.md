# JWT token access

<img src=".gitbook/assets/file.excalidraw.svg" alt="" class="gitbook-drawing">

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

### spring interceptor



### spring filters

