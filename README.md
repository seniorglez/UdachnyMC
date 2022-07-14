# UdachnyMC

A java application which provides a API REST to execute commands on a minecraft server.

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

The first time the containers go up they will create a new directory where the minecraft server will generate it's files, called server. As you will probably known the minecraft server has the eula.txt file which allow the users to agree with [the minecraft eula policy](https://account.mojang.com/documents/minecraft_eula). In order to run the server and have the whole infraestucture running you will need to change the value eula to true like shown:

```
#By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).
#Mon Jan 03 18:51:55 UTC 2022
     eula=true
```

## Connect to the Minecraft server

The server will use the default port: 25565 so if your are running this in your machine just enter your localhost: 127.0.0.1 and if this is running on a server just enter the server ip.

## Contributing

Feel free to fork it and made pull request if you think that your own version is better or adds new functionalities. You can also help with any open issues or add new ones.

You should probably check the [DevGuide](docs/DevGuide).

## Authors

<a href="https://github.com/seniorglez/UdachnyMC/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=seniorglez/UdachnyMC" />
</a>

## License

### UdachnyMC

Copyright (C) 2022 Diego Dominguez Gonzalez

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License 
as published by the Free Software Foundation; either version 3 of the License, or any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU General Public
License along with this program, if not check it [here](https://www.gnu.org/licenses/gpl-3.0.txt) 

![lgpl3](https://www.gnu.org/graphics/gplv3-or-later.png)
