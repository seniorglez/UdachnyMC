# minecraft-server-REST
A java application which provides a API REST to execute commands on a minecraft server.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

#### Java

In order to run Maven you will need to install Java's JDK but as long we are using Maven we didn't need to have the
specific version of Java that we are using to write owr project (Maven will compile on JDK8 anyway). I recommend to install versions older 
that JDK8 (minimum JDK7). On my case I'm using OpenJDK8.

I think that everybody who works with Java already knows how to set his JAVA_HOME system variable, but some of you use to set it like this:

```bash
    export JAVA_HOME="/usr/lib/jvm/java-8-openjdk/bin"
    
    export PATH=$JAVA_HOME/bin:$PATH

```

With the most of the programs this will works nice but Maven will not recognize this variable as a valid one. If you have set up
your JAVA_HOME like this the only think you need to do is to remove that /bin. 

```bash
    export JAVA_HOME="/usr/lib/jvm/java-8-openjdk"
    
    export PATH=$JAVA_HOME/bin:$PATH

```

#### Maven

We have to install maven to package this project.

If you use **Arch Linux or Arch-based** distros:
```bash
	
	sudo pacman -S maven
	
```
If you use **ubuntu/devian-based** distros:

```bash

    sudo apt install maven

```
If you use **MacOS** in order to install maven you will have to install [Homebrew](https://brew.sh/)

```bash

    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

```
And now you can just install maven with this package manager:

```bash
    
    brew install maven
    
```
If you are using **Windows** download maven form the [maven website](http://maven.apache.org/download.cgi). And the follow the
guide and the advice of the [installation guide](http://maven.apache.org/install.html).



Now we can check if maven works properly

```bash

	mvn -version

```

You should get something like this:

```
	Apache Maven 3.6.3 (NON-CANONICAL_2019-11-27T20:26:29Z_root)
	Maven home: /opt/maven
	Java version: 11.0.6, vendor: Oracle Corporation, runtime: /usr/lib/jvm/java-11-openjdk
	Default locale: es_ES, platform encoding: UTF-8
	OS name: "linux", version: "5.4.15-arch1-1", arch: "amd64", family: "unix"

```
## Build

To generate a executable jar you have to use the maven-assembly-plugin:

```
     mvn clean compile assembly:single  
```

## Run the project

As long the main class is being declare on the main manifest you can just invoke the java command with the -jar option.

```
    java -jar target/minecraft-server-rest-1.0-SNAPSHOT.jar
```

## Built With

* [Maven](https://maven.apache.org/) - The software project management tool.
* [Spark](http://sparkjava.com/) - The framework for creating web applications.
* [JJWT](https://github.com/jwtk/jjwt) - The JSON Web Token library for Java and Android.
* [Gson](https://github.com/google/gson) - The Google's JSON converter.
* [JUnit](https://junit.org/junit4/) - The unit testing framework.


## Contributing

Feel free to fork it and made pull request if you think that your own version is better or adds new functionalities.

## Authors

* **Diego Dominguez**   <a href="https://twitter.com/DGlez1111" target="_blank">
    <img alt="Twitter: DGlez1111" src="https://img.shields.io/twitter/follow/DGlez1111.svg?style=social" />
  </a>

## License

### minecraft-server-REST

Copyright (C) 2020 Diego Dominguez Gonzalez

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License 
as published by the Free Software Foundation; either version 3 of the License, or any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU General Public
License along with this library, if not check it [here](https://www.gnu.org/licenses/gpl-3.0.txt) 

![lgpl3](https://www.gnu.org/graphics/gplv3-or-later.png)
