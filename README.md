# springBootCRUDThymeleafJarApp
•	Quick description

This is Spring boot application that use MySQL for storing data and Thymeleaf for presentation. Although it is not used Spring boot Security, admin part of application is still secured using Session (after every unauthorised request application redirect to login page).

This is Blog application that besides simple CRUD operations have also and demandig operations like searching for blog by title, author or keywords.

Also, this is Single page aplication (SPA) – although the application has several different views, there are only two html pages in which Thymeleaf code is embedded. This app is suplied with oportunity to upload image and use it like admin, author or user image.

•	Used thechnologies

Spring boot,

Thymeleaf,

JPA (Hibernate),

MySQL.

•	Database manegement system

MySQL for storing data.

In application.properties file are defined credential values for datasource url, username and password.

CommandLineAppStartupRunner class implements CommandLineRunner and it use to populate database with starting data how application would working properly at the same start. This class is annotated with @Componet annotation and also use @Autowired annotation.

•	Models – Entities

There are for entities: Post, Author, Tag and Comment. All entities are annotated with @Entity and have No-args-constructor method, With-args-constructor method, Get and Set methods and ToString method. Also, in almost all of this entities are used next annotations: @Id, @GeneratedValue, @Column, @CreatedDate.

•	Entities relations

There are relations beetwen entites like: @OneToOne (unidirectioinal, not biderectional), @OneToMany and @ManyToOne.

•	Repository

For 4 entities threre are 4 interfaces that extends CrudRepository class: PostRepository, AuthorRepository, TagRepository and CommentRepository. These interfaces are annotated with @Repository annotation. Gist for all of these repositories are to communicate with database. Beside predefinated methods that CrudRepository class ofer there are definated custom methods, too.

•	Controllers

There are 5 Controlles: PostController, AuthorController, TagController, HomeController and AdminController. These controllers communicate with database through Services. All controller are annotated with @Controller annotation and also are used next annotatioins: @RequestMapping, @PostMapping, @GetMapping, @ModelAttribute, @RequestParam, @PathVariable.

•	Services

For 4 entities threre are 4 Service classes: PostService, AuthorService, TagService and CommentService. These classes are annotated with @Service and @Transactional annotations. Gist for all of these services are to make easy communication with database through mentioned repositories.

•	View – styling

For styling html files used W3.CSS (modern CSS framework with built-in responsiveness)

•	Application executable

JAR file

•	Security

Custom security through Session – not used Spring boot Security

•	Introduction for cloning

git clone https://github.com/milberonja/springbootJpaThymeleafApp

In your MySQL or another Database manegement system create database with name diverseblog

In application.properties file change username and password (type your credential data for these fields)


