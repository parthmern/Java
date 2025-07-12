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

### Flyway - setup and init migration

{% embed url="https://medium.com/swlh/introduction-of-flyway-with-spring-boot-d7c11145d012" %}

* `implementation 'org.flywaydb:flyway-core:6.4.3'`  in build gradle
* `spring.flyway.baseline-on-migrate=true` in application properties
* install _JPA buddy plugin_&#x20;
* [jpa-explorer-not-showing-issue-solve.md](jpa-explorer-not-showing-issue-solve.md "mention") btn not showing
* click on flyway <mark style="color:red;background-color:orange;">init migration</mark> ( first time migration )
*

    <figure><img src="../.gitbook/assets/image (15).png" alt=""><figcaption></figcaption></figure>
*

    <figure><img src="../.gitbook/assets/image (2) (1) (1) (1) (1).png" alt=""><figcaption></figcaption></figure>
* save it and you got the whole sql file
* i had no tables in DB rn
* added some code

```java
spring.flyway.url=jdbc:mysql://mysql-13f24328-pptl8685-9946.b.aivencloud.com:16510/defaultdb
spring.flyway.user=avnadmin
spring.flyway.password=AVNS_I_rzbISG46QB8AW4RZI
```

```
implementation 'org.flywaydb:flyway-core:10.14.0'
	implementation 'org.flywaydb:flyway-mysql:10.14.0'
	implementation 'com.mysql:mysql-connector-j:8.3.0'
```

* fixed some error from above things
* run the server and it has migrated all tables in DB ( there is ddl update - validate in properites )
* it created all tables
* to maintain there is one migration history table

<figure><img src="../.gitbook/assets/image (3) (1) (1).png" alt=""><figcaption></figcaption></figure>

***

* here from one table/class there is name col which can be null so i removed it \
  run code - not getting error
* but there is licenseNumber column which was required and unique, i remove it\
  I got error - while running code ( Error : schema validation failed )

***

### version migration

* i added one col/attribute in class named Driver

```
    private String phoneNumber;
```

* run code - getting error
* click on "flyway diff migration" button
*

    <figure><img src="../.gitbook/assets/image (4) (1) (1).png" alt=""><figcaption></figcaption></figure>
* it shows changes
*

    <figure><img src="../.gitbook/assets/image (6) (1).png" alt=""><figcaption></figcaption></figure>
* re-run code and see table in DB that inside driver phonenumber propery added\
  inside the migration maintain table V2 also added

***

### Rollback&#x20;

enterprice verion of flyway there is command named `flyway undo` to revert back to prev verison that you need to run inside _flyway cli_

_it only helpful to for previous migration rollback_

<mark style="background-color:blue;">V1 -> V1 -> V3 -> V4 -> V5 -> V6 -> V7  ( like V7 to V6 )</mark>\ <mark style="background-color:blue;">for ( V7 to V3 ) you need to do manual migration</mark>\


if you want to do then you have to write normal migration \
how ?&#x20;

* delete the col/attribute from class
* then do flyway migration in new version\


***

#### when do we need roolback? there is one change i got&#x20;

* booking can exist without revview&#x20;
* but review cannot be existed wihtout booking

{% hint style="info" %}
do not trust generated schema blindly\
it can give you many faults
{% endhint %}

***

#### halfway migration issue

* if it breaks at 4version then delete that version row from flyway\_schema\_history table&#x20;
* solving that issue in that V4.sql file
* start project again so you
