---
cover: >-
  https://images.unsplash.com/photo-1744646366936-874cf4a39b96?crop=entropy&cs=srgb&fm=jpg&ixid=M3wxOTcwMjR8MHwxfHJhbmRvbXx8fHx8fHx8fDE3NTA3MzIwNTV8&ixlib=rb-4.1.0&q=85
coverY: 0
---

# DB versioning | DB migration

1\) when the product features and req are huge, there will be lot of tables and relstions\
so how we are gone remember this all

2\) when there are multiple devs working on same DB\
one user change phoneNumber to String in USER class/table\
another one is also changed something in classes \
it can break feature

3\) you changed 5 classes and now it is breaking the things\
you have to rollback all the things,



version controlling of DB\
previous state of table, which col change, how new table added\
this is called as DB versioning, DB migration, schema versioning\
\- ex. `Flyway` is lib to do this

here mainintaing incremental script inside which there is _code that shows that what DB looks like in next  version_\
every script act as version for us

***

```
spring.jpa.hibernate.ddl-auto=validate
```

* please do not create / update tables for me spring JPA
* just do validate that the class representation and the table repre. are matching or validating ?

***

### Flyway

{% embed url="https://medium.com/swlh/introduction-of-flyway-with-spring-boot-d7c11145d012" %}

* `implementation 'org.flywaydb:flyway-core:6.4.3'`  in build gradle
* `spring.flyway.baseline-on-migrate=true` in application properties
* install _JPA buddy plugin_&#x20;
* [jpa-explorer-not-showing-issue-solve.md](jpa-explorer-not-showing-issue-solve.md "mention") btn not showing
*
