# API

## Prerequisites 

In addition to those indicated in the README of the project necessary to execute the containers you will need to install maven and the openJDK.

## Architecture

This part of the project tryes to implement the Hex architecture to make it as modular and third party libraries independent as possible.

## The development cicle 

This is a guide of how I personaly develop the API, is not the only way just maybe the most convinient one. 

First of all you should writte your changes in the code. As long as the project uses maven to take care of all the lifecicle of the API, the source code is under the directory src/main/java.

### Compiling

Once you have made some changes you can compile the API calling:

```bash
    mvn compile
```

### Testing

Now, if you have writte a new feauture you should writte some test to make sure that your code actualy works (Yeah!). In order to test you should create for every new class anotherone with the same name but with the prefix "Test" under the directory src/test/java.

Now the new classes should import JUnit:

```java
import org.junit.Test;
import static org.junit.Assert.assertTrue;
```

You can check the test suite documentation, but here is a quick example of a simple test:


```java

package com.seniorglez.whatever;
//Import the class you want to test
import com.seniorglez.infra.Calculator;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestCalculator {
    //Add the anotation @Test to allow the test runner to know that the following function is a test.
    @Test
    public void testOnePlusOneShouldReturnTwo() { //meaning name
        Calculator cal = new Calculator();
        int result  = cal.add(1,1); // Try any public method of the class
        assertTrue(result == 2); // Check if the method returns the expected result. 
    }
}

```

Once you are happy with your tests run them:

```bash
    mvn test
```

### Moar testing

Once I´m happy with the code and with the maven test I used to preform some "Manual tests" just to be sure. First of all I build the containers:

```bash
    docker-compose build 
```

And then I start the API

```bash
    docker-compose up -d
```

If I was just fixing a bug or refactoring some code I will use the front to test the system but if you can not do that I recomend you to use curl to call the API endpoints.

For example:

``` bash
    curl -d "{user: 'guest', password: 'guest'}" -X POST http://localhost:4567/v1/auth/login
```
