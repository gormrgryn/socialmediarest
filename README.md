## Description

Social media app backend api with unit tests, built using Spring Boot, Hibernate.

## REST API endpoints
<ul>
  <li>
    User endpoints
    <ul>
      <li>
        "/user/" POST - create new user (registration)
      </li>
      <li>
        "/user/{id}" GET - get user
      </li>
      <li>
        "/user/posts/{id}" GET - get users posts
      </li>
      <li>
        "/user/{id}" PUT - update user
      </li>
    </ul>
  </li>
  <li>
    Post endpoints
    <ul>
      <li>
        "/post/" POST - create new post
      </li>
      <li>
        "/post/{id}" GET - get post
      </li>
      <li>
        "/post/{id}" PUT - update post
      </li>
      <li>
        "/post/{id}" DELETE - delete post
      </li>
    </ul>
  </li>
</ul>

### Executing program

Edit applicaion.properties file
```
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}/{db_name}
spring.datasource.username={username}
spring.datasource.password={password}
```
Run your database
Run the app
```
cd socialmediarest
./mvnw spring-boot:run
```

### Run Tests
```
./mvnw test
```


## License

This project is licensed under the MIT License - see the LICENSE.md file for details
