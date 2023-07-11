# 投资者服务平台

## 1. 项目描述

投资者服务平台是一个基于Java和Spring Boot开发的后端服务，使用Vue作为前端框架。这个平台旨在提供全面的投资者服务，包括用户认证、权限管理、数据持久化、账户管理、账户划款、分红管理、清盘管理和销户管理等。

## 2. 技术栈

- Java 8: 主要的开发语言。
- Spring Boot: 提供了创建独立、可直接运行的Spring应用的快捷方式，简化了配置文件，内置了服务器。
- Spring Security: 提供了全面的安全服务，包括认证和授权。
- Spring Data JPA: 提供了包括数据访问、持久化和查询等在内的全面的数据访问解决方案。
- Vue.js: 用于构建用户界面的渐进式JavaScript框架。
- Redis: 用作数据库，存储用户数据和会话信息。
- Maven: 用于项目管理和构建。

## 3. 主要功能

- **用户认证**: 用户可以注册和登录。密码会在保存前被加密。用户可以进行的操作基于他们的角色进行控制。
- **权限管理**: 不同的用户有不同的角色，不同的角色有不同的权限。权限控制了用户可以访问的页面和API。
- **数据持久化**: 用户数据被保存在Redis数据库中，确保数据的持久化。
- **账户管理和账户划款**: 用户可以管理他们的账户，包括查看账户余额、进行存款和取款等操作。
- **分红管理**: 管理员可以为用户分发红利。
- **清盘管理和销户管理**: 管理员可以清空用户的账户，或者关闭用户的账户。

## 4. 如何运行

1. 克隆这个仓库到你的本地环境。
2. 进入项目的根目录。
3. 使用Maven来构建项目：`mvn clean install`。
4. 运行项目：`java -jar target/myproject-0.0.1-SNAPSHOT.jar`。
5. 在浏览器中访问`http://localhost:8080`。

## 5. 开发环境

- JDK 1.8 或更高版本
- Maven 3.0+
- Redis 3.0+

## 6. 贡献

欢迎任何形式的贡献，包括提交问题、提出新功能的建议、改进代码等。

## 7. 功能实现逻辑
-用户认证：我们使用Spring Security来处理用户认证。当用户尝试登录时，Spring Security会调用UserDetailsService的loadUserByUsername方法来获取用户的详细信息。这个方法会在数据库中查找用户名，并返回一个UserDetails对象，这个对象包含了用户的用户名、密码、角色等信息。然后，Spring Security会将用户输入的密码和UserDetails对象中的密码进行比较，如果匹配，那么认证成功，否则，认证失败。

-权限管理：我们同样使用Spring Security来处理权限管理。在WebSecurityConfigurerAdapter的configure(HttpSecurity http)方法中，我们定义了哪些URL需要哪些角色才能访问。例如，我们可能会有这样的代码：.antMatchers("/admin/**").hasRole("ADMIN")，这表示只有ADMIN角色的用户才能访问/admin路径及其子路径。

-数据持久化：我们使用Spring Data JPA来处理数据持久化。我们定义了一个接口UserRepository，这个接口扩展了JpaRepository接口，JpaRepository接口定义了一些基本的数据访问方法，例如save、delete、findOne等。我们可以在UserRepository接口中定义更具体的方法，例如findByUsername，Spring Data JPA会自动为这些方法提供实现。

-账户管理和账户划款：在这个功能中，用户可以查看他们的账户余额，进行存款和取款操作。我们可能会在AccountController中定义一个getBalance方法来处理查看余额的请求，定义一个deposit方法来处理存款请求，定义一个withdraw方法来处理取款请求。这些方法会使用AccountService来操作用户的账户。AccountService可能会使用AccountRepository来访问账户数据。

-分红管理：在这个功能中，管理员可以为用户分发红利。我们可能会在DividendController中定义一个distributeDividends方法来处理分发红利的请求。这个方法会使用DividendService来分发红利。DividendService可能会使用UserRepository来获取所有的用户，然后为每个用户的账户添加红利。

-清盘管理和销户管理：在这个功能中，管理员可以清空用户的账户，或者关闭用户的账户。我们可能会在AccountController中定义一个clearAccount方法来处理清空账户的请求，定义一个closeAccount方法来处理关闭账户的请求。这些方法会使用AccountService来操作用户的账户。AccountService可能会使用AccountRepository来访问账户数据。
