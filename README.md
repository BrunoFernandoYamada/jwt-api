# jwt-api

API for authenticate and singup service

have 2 endpoints:

* /api/auth/authenticate
- Responsible for do the authenticantion

* /api/auth/signup
- Responsible for sign up in the Application

When starts the application in the first time, it's create a default user admin with followings credentials:
Username: admin
Password: admin1234

It's create too 3 roles in the database:
- ROLE_USER
- ROLE_MODERATOR
- ROLE_ADMIN

References: https://bezkoder.com/spring-boot-jwt-auth-mongodb/



