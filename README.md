# Microservice-quote-app
This is a project to show the creation of microservices
It has 4 services :

1. The Eureka service wich acts as a Registry Server

2. The Zuul-proxy service wich acts as the Gateway server

3. A Quote service which provides data about quotes.This service in itself uses the IEXT REST API 
https://github.com/WojciechZankowski/iextrading4j to get the quote related data.

4. A User service with the ability to create users,add quote to users,get the quote details for a given user ,delete quotes from a user or
remove a user completely(See the endpoints below for the full list)

The User-service uses the Eureka registry to get the instance server of the Quote-service and consumes it's REST API to validate and get
quote details for different users.You need to go to user-service application.properties to configure the details for your database connection
By default Hibernate-ddl-auto is set to "validate" so it wont create the entity tables for you.You can change it to "create" for the first
time,so it will automaticlly create tables for you,and then change it to "validate" when you run it next time.

Here are the endpoints for User-service :
* GET /users : lists all users in the database
* GET /userQuotes/{userId} :shows the details for the quotes a user has,for the given user id as a path variable
* POST /addUser : accepts a user in Json Format and saves it to database.See the entity class for more details
* POST /addQuote/{userId} : accepts a single string as a quote's symbol(AMZN for example) and saves that quote for the given user id
* DELETE /deleteQuote/{userId} : accepts a single string(represents quote's symbol) as method body and deletes that quote for the given user id

You can see the code for more details.I have commented every method so you can understand what it will do.

Note,since you will access the user-service using Zuul Proxy you need to add /home prefix to all endpoints above.
For example http://localhost:8080/home/users
