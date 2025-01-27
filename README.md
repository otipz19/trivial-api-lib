## Installation

1. Create a new folder `local-maven-repo` at the root of your Maven project.
2. Paste library's .jar here.
3. Add a local repository inside your `<project>` section of your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>local-maven-repo</id>
        <url>file:///${project.basedir}/local-maven-repo</url>
    </repository>
</repositories>
```

4. Execute the following mvn command, changing {version} and {lib-file-name}:

```sh
mvn deploy:deploy-file
    -DgroupId=otipz
    -DartifactId=trivial.api.lib
    -Dversion={version}
    -Durl=file:./local-maven-repo/
    -DrepositoryId=local-maven-repo
    -DupdateReleaseInfo=true
    -Dfile=./local-maven-repo/{lib-file-name}.jar
```
