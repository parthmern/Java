# more about migration in diff microservices

### more about migration in diff microservices

having entity which is common and you are doing copy-paste and use that entity in other microservices\
it is reptition and coflicts -> but still used in industry\
solution : common library\


#### little more about Migration

* intially I had Review Serice
* I created new service in which I introduced PasswngerSignupRequestDto which needs Passenger class
* so I copied all models from Review services

```java
// PasswngerSignupRequestDto 
public class PassengerSignupRequestDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
}
```

* i need some more attributes in Passenger table so I did changes in Auth service

<figure><img src=".gitbook/assets/image (4).png" alt=""><figcaption></figcaption></figure>

* ( this changes are only done in AuthService )
* run code - <mark style="background-color:green;">got validation error</mark>
* so you need to do migration  ( this will not be init migration bcz we already created that before in ReviewService for first time, here we are interecting with that same db)
* click on "flyway diff version migration" \
  &#x20;![](<.gitbook/assets/image (3) (1).png>) ( same as below img)

<img src=".gitbook/assets/file.excalidraw (4).svg" alt="" class="gitbook-drawing">

* here always read this automatic generated migration files they are not accurate and now you need to do changes according to you know
* so now my final file looks like

```sql
alter table passenger
    add email VARCHAR(255) NULL;

alter table passenger
    add password VARCHAR(255) NULL;

alter table passenger
    add phone_number VARCHAR(255) NULL;

alter table passenger
    modify email VARCHAR(255) NOT NULL;

alter table passenger
    modify password VARCHAR(255) NOT NULL;

alter table passenger
    modify phone_number VARCHAR(255) NOT NULL;

alter table passenger
    modify name VARCHAR(255) NOT NULL;
```

* i need to do migration for version4  file but issue is that i cannot perform directly it needs to validate v1, v2, v3 and then it can do migration, validation for v4 file
* so i copied all migrations files\
  ![](<.gitbook/assets/image (31).png>)
* and then run code it started doing migration for v4 but got some error

<figure><img src=".gitbook/assets/image (30).png" alt=""><figcaption></figcaption></figure>

* see in DB scehma&#x20;
* <mark style="color:blue;background-color:yellow;">it says that you are in halfway of migration so also in that case u need to delete that Version from table</mark>

<figure><img src=".gitbook/assets/image (32).png" alt=""><figcaption></figcaption></figure>

here at v4 success is 0 ( false )\
so we need to <mark style="background-color:blue;">delete  that V4 ROW</mark> from table

* issue it was giving me issue from line 10 w\
  remove the V4 migration which was stuck in halfway from DB table

```sql
SET SQL_SAFE_UPDATES = 0;
DELETE FROM flyway_schema_history WHERE version = '4';
SET SQL_SAFE_UPDATES = 1;
```

<figure><img src=".gitbook/assets/image (33).png" alt=""><figcaption></figcaption></figure>

so i change to this and getting errror

<figure><img src=".gitbook/assets/image (5).png" alt=""><figcaption></figcaption></figure>

{% hint style="info" %}
<mark style="color:yellow;background-color:red;">benchod ye half commnad run kar chuka hoga and wo already DB me reflected ho gaya hoga</mark>
{% endhint %}

so i deleted email column from DB\
with password and phone\_number column and then use gpt to make a new script\
which looks like this

```java
-- Add columns as nullable
ALTER TABLE passenger
    ADD email VARCHAR(255) NULL,
    ADD password VARCHAR(255) NULL,
    ADD phone_number VARCHAR(255) NULL;

-- Update existing records to have some default values (empty string or placeholder)
UPDATE passenger SET email = '' WHERE email IS NULL;
UPDATE passenger SET password = '' WHERE password IS NULL;
UPDATE passenger SET phone_number = '' WHERE phone_number IS NULL;

-- Then modify to NOT NULL
ALTER TABLE passenger MODIFY email VARCHAR(255) NOT NULL;
ALTER TABLE passenger MODIFY password VARCHAR(255) NOT NULL;
ALTER TABLE passenger MODIFY phone_number VARCHAR(255) NOT NULL;

-- Also modify name to NOT NULL if needed
ALTER TABLE passenger MODIFY name VARCHAR(255) NOT NULL;

```

migration is fuped



