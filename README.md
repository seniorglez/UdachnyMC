# UdachnyMC

A java application which provides a API REST to execute commands on a minecraft server.

DISCLAIMER: The front is an MVP that is not safe or pretty and will probably have a few functional bugs.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites

#### Docker 

You need to download docker from the package manager of your distribution, from brew if you use MacOS or [from here](https://docs.docker.com/get-docker/) if you use Windows.

#### Docker-compose 

You only need to download docker-compose from the package manager of your distribution or see how to install it [from this link](https://docs.docker.com/compose/install/) if you use MacOS or Windows. 

### Build it

To build the docker containers just call:

```bash
     docker-compose build 
```

### Run it 

To run just the API call:

```bash
     docker-compose up
```

To run the API with the front call:

```bash
     docker-compose --profile front up
```

## Connect to the Minecraft server

The server will use the default port: 25565 so if your are running this in your machine just enter your localhost: 127.0.0.1 and if this is running on a server just enter the server ip.

## Built With

* [Maven](https://maven.apache.org/) - The software project management tool.
* [Spark](http://sparkjava.com/) - The framework for creating web applications.
* [JJWT](https://github.com/jwtk/jjwt) - The JSON Web Token library for Java and Android.
* [Gson](https://github.com/google/gson) - The Google's JSON converter.
* [JUnit](https://junit.org/junit4/) - The unit testing framework.
* [Docker](https://www.docker.com/) - The whale of the containers?
* [Docker-compose](https://docs.docker.com/compose/) - The tool for running multi-container Docker applications.
* [fucntionalJava](https://github.com/seniorglez/functionalJava) - My own functinal java library.


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
