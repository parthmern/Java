# Spring data JPA



```java
// Some code
implementation("org.springframework.data:spring-data-jpa")
implementation("mysql:mysql-connector-java:8.0.33")
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

```

<figure><img src="../.gitbook/assets/image (8).png" alt=""><figcaption></figcaption></figure>

in application.properties file

```java
spring.application.name=firstSpringProject

spring.datasource.url=jdbc:mysql://mysql-13f24328-pptl8685-9946.b.aivencloud.com:16510/defaultdb
spring.datasource.username=avnadmin
spring.datasource.password=AVNS_I_rzbISG46QB8AW4RZI
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

```



-> spring.jpa.hibernate.ddl-auto=<mark style="background-color:yellow;">create</mark>       &#x20;

* `create` means whatever class that u difined it is going to create table
* `create-drop` means for that session it creates and then drop table



#### make Todo class

```
// Some codepackage com.example.firstSpringProject;
import jakarta.persistence.*;

@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


```

* make TodoRepository class

```java
package com.example.firstSpringProject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {}
```

#### start project and see logs

<figure><img src="../.gitbook/assets/image (9).png" alt=""><figcaption></figcaption></figure>
