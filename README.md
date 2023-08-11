# posts-comments-services-project
**The construction of this project's architecture was grounded in the principles of microservices architecture.**

The project has been partitioned into three distinct microservices, complemented by an API gateway that serves as the conduit through which these services are accessed.

1- There is a "Post" service that permits users to generate, modify, and peruse posts connected to discussions about courses.
2- An accompanying "Comment" service empowers users to append, revise, and peruse comments on posts or materials.
3- Moreover, a "Reaction" service is in place to oversee reactions associated with both posts and comments.

The system incorporates an API gateway, serving as an external interface for users to interact with. This gateway is responsible for load balancing, routing, and facilitating communication with the various services.

**Unit Testing Approach:-**

-To ensure the accurate functioning of individual elements within the microservices.

-Every microservice was equipped with dedicated unit tests tailored to assess both message producers and consumers operating on RabbitMQ queues.
