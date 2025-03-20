# 🖥️ TO-DO APP

## 📝 Summary
This project is a simple Spring Boot backend RESTful API for a to-do app that allows users to create, update, and delete tasks.
## 🏗️ Project Structure
```
/todoapp
│── src/
│   ├── main/
│   │   ├── java/com/example/project/
│   │   │   ├── controller/   # handle request
│   │   │   ├── service/      # handle logic
│   │   │   ├── repository/   # communicate with DB
│   │   │   ├── model/        # define entity
│   │   │   ├── dto/          # define dto
│   │   │   └── exception/    # handle exception
│   │   └── resources/
│── pom.xml                   # dependencies
│── Dockerfile                # use to build images
│── docker-compose.yml        # use to run all required services
└── .env                      # put all your secret keys here
```

## 🚀 How to start this app
1. Install Docker and Docker Compose (required).
2. Create a .env file (see the example below) and place it in the project's root directory.
3. Run the following command
```
docker-compose up --build
```
4. In **DueDateCheckerService** replace the mail you want to recive the notification
```
for (Task task : tasks) {
  mailService.sendDueDateReminder("youremail@gmail.com", task.getTitle(), task.getDueDate().toString());
}
```
5. Use Postman or any other API testing tool to send HTTP requests.


## 🚀 Example for the .env File
```
SERVER_PORT=8080

# Postgres config
DB_USERNAME=postgres
DB_PASSWORD=1234
DB_HOST=db

# Mail config
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
✅ "IF YOU WANT NOTIFICATIONS THROUGH MAIL, REPLACE YOUR GOOGLE APP PASSWORD HERE. IF NOT PROVIDED, IT WILL SIMPLY LOG THE NOTIFICATION TO THE CONSOLE INSTEAD."
✅ "YOU CAN CHANGE HOW FREQUENTLY THE NOTIFICATION IS SENT BY MODIFYING fixedRate IN DueDateCheckerService."
MAIL_USERNAME=abc@gmail.com  
MAIL_PASSWORD=abcd efgh ijkl mnop

MAIL_SMTP_AUTH=true
MAIL_STARTTLS_ENABLE=true
```

## 🚀 API Collection

### Task

1. create:
```
POST {{BASE_URL}}/tasks
```
```
{
    "title": "Học Spring AAA",
    "description": "Làm bài tập RESTful API v3",
    "dueDate": "2025-03-21",
    "priority": "HIGH",
    "status": "TODO"
}
```

2. update
```
PUT {{BASE_URL}}/tasks/{taskid}
```
```
{
  "title": "ádsad",
  "description": "ádasđs",
  "dueDate": "2025-03-24",
  "priority": "HIGH",
  "status": "TODO"
}
```

3. delete
```
DELETE {{BASE_URL}}/tasks/{taskid}
```

4. get by id
```
GET {{BASE_URL}}/tasks/{taskid}
```

5. search by string
```
GET http://localhost:8080/api/tasks/search?page=0&query=example&size=10
```
### Dependency

1. Link 2 taks
```
POST {{BASE_URL}}/tasks/{first-task-id}/dependencies/{second-task-id}
```
2. retrive all dependencies
```
GET {{BASE_URL}}/tasks/{taskid}/dependencies
```
