# EasyExpendIt

###### Briefly: EasyExpendit is a simple and fast monolith utility for managing consumables of any kind. It is based on PostgreSQL DB on back, Spring Boot 2.5 for logic and Thymeleaf on the front.

## Business case
- optimize an engineer's efficiency by automating consumables accounting process
- obtain better consuming control by getting some stats
- provide better planning

## Functional requirements
1. Service must be cross-platform and available via HTTP
2. Service must have security limits for anonymous user access
3. Service must have authorization for registered users
4. Service must have two user roles: Administrator and User 

## Use cases
#### Administrator role
1. As an Admin I can create a count of new consumables of one type to take in a new arrivals
2. As an Admin I can edit existing consumables to make corrections  
3. As an Admin I can proceed consumable to its next status by hitting one button to make accounting faster
4. As an Admin I can delete consumable on any of its status to make corrections
5. As an Admin I can create user accounts to get new employees into work with system 
6. As an Admin I can edit any user account to reset password or make corrections
7. As an Admin I can create new categories to widen the scope of accounting
8. As an Admin I can edit categories to make corrections
9. As an Admin I can view all transactions to get some stats

#### User role
1. As a plain user I can create a count of new consumables of one type to take in a new arrivals
2. As a plain user I can edit existing consumables to make corrections
3. As a plain user I can proceed consumable to its next status by hitting one button to make accounting faster
4. As a plain user I can view transactions to get some consuming stats

## Technology Stack
- Jav 11
- Spring Boot 2.5.4
- Spring Security
- Spring DataJPA
- Spring MVC
- Thymeleaf
- SLF4J 1.7.32
- PostgreSQL

## ER-diagram
![ER-diagram](https://user-images.githubusercontent.com/37121361/144762995-e663dd3a-a625-4030-a8e5-23ada26ca8a7.JPG)

## Views
* /consumables - main view for managing consumables
* /transactions - view existing transactions (admins only)
* /categories - manage categories (admins only)
* /users - manage users (admins only)
* /roles - manage user roles (admins only)
