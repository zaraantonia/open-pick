# open-pick

# 1 Abstract
OpenPick is a Java Spring application meant to aid students and IT enthusiasts into participating
in open-source projects. The idea behind the application is that the user can create a profile, post
their own open source project along with tasks and needed documents and wait for other users to take
matter into their own hands and start editing the documents. Alternatively, an user can pick a project
that most represents them and can start solving the written tasks.

# 2 Functionalities
The user can edit their account. They can register, login and logout. Additionally, for the projects
that they own (meaning that they have created), they can edit and delete them, as well as add, delete,
and edit their tasks. For other projects, they can edit the documents.
Each user can see the projects page, which should all the projects in the application, and their
profile section.
The admin can delete projects and users, acting as a moderator for the wellbeing of the application.
File size for the documents is of maximum 20MB.

# 3 Technologies Used
The application has been written in Java Spring as for the back-end. For security protocols we have
used Spring Security to ensure that the login/logout/register operations do not allow interference to
the database. The front-end of the applications uses Bootstrap, Thymeleaf as well as a lot of plain
HTML and CSS code for the aesthetics of our project. The database management is implemented
through the frameworks JPA and Hibernate, for a better and safer data access.

# 4 Design Pattern and Architecture
The design pattern chosen for the project is the Model View Controller pattern, specifically preferred
by the authors of Java Spring for their framework. The architecture chosen is a five layer architecture
mainly centered around the database access, where we implement repository classes as data access
objects, service classes as business layers to handle the repositories, model classes for modelling the
objects in our application, controllers for interaction between the front end and back end and view for
the front end.
