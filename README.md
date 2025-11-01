<!-- Variables (this block will not be visible in the readme -->
[gradleInstall]: https://gradle.org/install/
<!-- End of variables block -->

# Bukman
Bukman allows you to manage your plugins in-game.
Featuring reloading, unloading and loading of plugins from your plugins folder at runtime.
Bukman also has handy methods to lookup commands and plugins,
and provides you with handy information about them.

## Compiling Bukman
There are two ways to compile Bukman:
### 1. Installing gradle (recommended)
1. Make sure you have [gradle][gradleInstall] installed.
2. Run the project with `gradle build` to compile it with dependencies.
### 2. Using the wrapper
**Windows**: `gradlew.bat build`
<br>
**Linux/macOS**: `./gradlew build`

#### Gradle:
```kotlin
repositories { 
  maven("https://maven.zhira.net/repository/zhdev/")
}

dependencies {
  compileOnly("org.zhdev.bukman:bukman:VERSION")
}
```

#### Maven:
```xml
<project>
  <repositories>
    <!-- Bukman repo -->
    <repository>
      <id>zhdev-repo</id>
      <url>https://maven.zhira.net/repository/zhdev/</url>
    </repository>
  </repositories>
  
  <dependencies>
    <!-- Bukman dependency -->
    <dependency>
      <groupId>org.zhdev.bukman</groupId>
      <artifactId>bukman</artifactId>
      <version>VERSION</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>
</project>
```
