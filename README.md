# Java application for tracking game score with Redis using Jedis client.  

### How to run it?

To run the application you first need to have a running Redis Server. You can build a Redis server image from the Dockerfile in the root of this project. The Dockerfile copies the file redis.conf to the container so if you need to change any configuration file that   


### Setting up the Redis Server   

Run this command to build the docker image for the Redis server from the same directory of your Dockerfile:   
**docker build -t redis .**   

Run this command to run your redis image as a container:   
**docker run -d -p 6379:6379 redis**   


### Configuring the Redis client in Java   

You will need to set 3 environment variables so the java client can connect to the redis server on the docker host as defined in the **RedisClient.java** file:   

// host environment variable   
**private String host = System.getenv("host");**   

// 6379 for the port of the Redis Server running on the docker, same as defined in the redis config and docker command.
**private String port = System.getenv("port");**   

// The same password setted in your redis.conf file.   
// Remember if you change the password you will need to rebuild your docker image.   
**private String password = System.getenv("password");**   

That's all you need to run your Java Jedis Application.

### Testing your new Java Jedis API.   

### Add score to a user   

You can create a post request to test your application using Postman.   

// Method and url   
**POST 127.0.0.1:7000/api/score/user**   

// Body (Payload)   

**{"userName": "Luke", "score": 700, "position": 1}**   







