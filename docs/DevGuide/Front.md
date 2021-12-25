# Front dev guide

I'm using this project to teach myself React.js and Next.js so I'm sure it've a lot of errors and bad practices, if you are going to fix any of those errors it would be very useful for me if you tell me about the errors that I have made in your pr.

This front is designed to simply "paint the API" so I want to try to write as less logic as possible.

## Prerequisites 

In addition to those indicated in the README of the project necessary to execute the containers. You will need to install node.js and npm.

## Preparation of the development environment

Since there is no backend mock prepared we will simply work with the backend. So run the app like shown:

```bash
     docker-compose up -d
```

Now start the front application in development mode with npm:

```bash
     cd udachny-web

     npm run dev
```

This should open your application at http: // localhost: 3000 / Now every time you make a change to the code the page will automatically refresh so you can see your changes.
