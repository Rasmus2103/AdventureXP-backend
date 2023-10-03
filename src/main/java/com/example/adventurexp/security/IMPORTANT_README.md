# About the security module

This module contains the security layer of the application. It is responsible for authenticating users and authorizing them to access certain resources.

## Important
Unless you know what you are doing, do not modify any code in this package, except from the following two files which you are supposed to adjust.

- config.SecurityConfig.java
- config.CorsConfig.java

#### If you change anything in the security module, make sure to enable the security tests
You can do this for your maven builds via the maven-surefire-plugin, in your pom-file

