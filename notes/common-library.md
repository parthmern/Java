---
cover: >-
  https://images.unsplash.com/photo-1579546928937-641f7ac9bced?crop=entropy&cs=srgb&fm=jpg&ixid=M3wxOTcwMjR8MHwxfHNlYXJjaHw5fHxncmFkaWVudHxlbnwwfHx8fDE3NTE5NzMyNTR8MA&ixlib=rb-4.1.0&q=85
coverY: 0
---

# Common Library

we have same models in different micreoservce till now\
also db migration is same\
that was creating issues/conflicts for us



* trasnfer all models
* transfer all db.migration files
* also configure all applicaiton.yml&#x20;
*

    <figure><img src=".gitbook/assets/image (2).png" alt=""><figcaption></figcaption></figure>
* it is shutting down automatiically brecause we donot have spring web but it is working

\
\- here as we know all dependencies are available in maven center from where we are downloading\
\- so we can push this file to maven centeral\
-but we do not need this we only need that this llibrary accessable into other projects of mine



### maven publish plugin

{% embed url="https://docs.gradle.org/current/userguide/publishing_maven.html" %}

```
plugins {
    id 'maven-publish'
}
```

```java

publishing {
    publications {
        mavenJava(MavenPublication) {
            from components.java

            versionMapping {            // extra to solve the dependecy version issue
                allVariants {
                    fromResolutionResult()
                }
            }
        }
    }
    repositories {
        maven {
            //url = uri('/d/Z_PARTH-CODES/Uber_backend/Uber_EntitiyService')
            url = uri("${project.rootDir}")
        }
    }
    }

}

```

build it

then run `./gradlew publish`

<figure><img src=".gitbook/assets/image (39).png" alt=""><figcaption></figcaption></figure>

How to use it\
in different service

```java
repositories {
    maven {
        url = uri("${project.rootDir}/../../Uber_backend/Uber_EntitiyService")  // match the path used above
    }
    mavenCentral()
}
```

and then&#x20;



### from new

<figure><img src=".gitbook/assets/image (37).png" alt=""><figcaption></figcaption></figure>

```
plugins{
    id 'maven-publish'
}
```

```java

publishing {
	publications {
		mavenJava(MavenPublication){
			versionMapping {
				usage('java-api') {
					fromResolutionOf('runtimeClasspath')
				}
				usage('java-runtime') {
					fromResolutionResult()
				}
			}
			from components.java
		}
	}

	repositories {
		maven {
			// pwd - do that in cli
			url = uri("D:\\Z_PARTH-CODES\\Java\\UberEntityService")
		}
	}
}

```

how to publish&#x20;

in another service, --> how to access

```
repositories {
    maven {
        url = uri("${project.rootDir}/../UberEntityService/")  // match the path used above
    }
    mavenCentral()
}
```

```java
// maven local common lib
implementation "org.example:UberEntityService:0.0.1-SNAPSHOT"    
               "<build.gradle-group>:<settings.gradle-rootProject.name>:<build.gradle-version>
```

<figure><img src=".gitbook/assets/image (38).png" alt=""><figcaption></figcaption></figure>

* after replaceing all things
* start server
*   got error

    <figure><img src=".gitbook/assets/image (35).png" alt=""><figcaption></figcaption></figure>
* why error?\
  problem is that entity classes are not present in authService\
  it is not able to scan for entities\
  it is using it as normal javaclass\
  but at runtime it should be as entitty

```java
@EntityScan("org.example.uberentityservice.models")    // Add this line
public class UberAuthServiceApplication {   // ... }
```

* working perfectly
* in authService, you can delete flyway dependency because we donot need in this service

### how o wokr with it after making changes in entityservice

* after doing some changes in entityserviice
* changes like change model and then do flywaymigration with script and reflected into db
* change the version

```java
group = 'org.example'
version = '0.0.2-SNAPSHOT'
```

* do first `./gradlew publish`  which works ( extra  `./gradlew publishToMavenLocal`  )

<figure><img src=".gitbook/assets/image (36).png" alt=""><figcaption></figcaption></figure>

*   then go to AuthService\
    there chhange version in denepdency to \


    ```
    implementation "org.example:UberEntityService:0.0.2-SNAPSHOT"
    ```
* build it and run it
