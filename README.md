# users-back

Spring boot

CRUD Users with MySQL Database.

Missing *.properties file.

If you would like run this project. Add the following files:
  
    · application.properties (src/main/resources)
        Add the following properties
            
            # Datasource
            spring.datasource.url=
            spring.datasource.username=
            spring.datasource.password=
            
    · application-test.properties
        Add the following properties
        
            spring.datasource.platform=test
            # Datasource
            spring.datasource.url=
            spring.datasource.username=
            spring.datasource.password=
            
EndPoints
  
    Get all users: http://url:port/api/crud/users
    Get user by id: http://url:port/api/crud/users/{id}
    Create user: http://url:port/api/crud/users
        {
            "name": "User Name",
            "email": "userName@test.com"
        }
    Update user: http://url:port/api/crud/users/{id}
        {
            "id": {id}, 
            "name": "User Name",
            "email": "userName@test.com"
        }
    Delete user: http://url:port/api/crud/users/{id}
    
