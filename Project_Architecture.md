# Project Architecture

## 1. Introduction

Credit-Card-Reward-Points-System is a **Spring** project. It runs depending on **Java 8**, **Spring Web Service**, **JPA**, **Thymeleaf**, and **Bootstrap**. Please ensure that the relevant environment is configured before running the program.

The project main function '**CreditCardRewardPointsSystemApplication.java**' is located at **Credit-Card-Reward-Points-System\src\main\java\com\creditcard\rewardpoints**.

Sample csv input data files are located at **Credit-Card-Reward-Points-System\sample_input** folder.

## 2 UML

![Reward_Points_System_UML](C:\Users\haozh\Desktop\Reward_Points_System_UML.svg)

Here we briefly introduce the architecture of the credit card reward points system, including the work flow of the project and the purpose of each class and function method.

### 2.1 MainController

MainController is a service class that redirect user to the home page (home.html).

### 2.2 home.html

home.html (url: http://localhost:8080/reward/) provides an channel that allow user to upload the customer transaction history csv file. It likes like this:

<img src="C:\Users\haozh\Desktop\Testing_Demonstration.jpg" alt="Testing_Demonstration" style="zoom:67%;" />

### 2.3 RewardController

RewardController gets the uploaded transaction history date from home.html, and implement 2 functions:

1. Distract the input data depending on TransactionService, and receive the modified data back.

2. Find the optimal solution for the highest reward points possible depending on RewardService, and get the reward points results & relevant data back.

3. Pass reward points results & relevant data to TransactionService, and get the reward points for each transaction back.

   

After all the data ready, pass reward result to reward_points_details.html

### 2.4 TransactionService

TransactionService provides 3 method:

1. dateToMonth() : distract the month yyyy/MM from the input data yyyy/MM/dd, and return the month.
2. amountCountByCategory(): sum the total amount of each merchant, and return the amounts in list.
3. setPointsForEachTransaction(): set reward points data for each transaction object.

### 2.5 Transaction

Transaction class contains many attributes related to the transaction history. Each Transaction object refers to a single transaction in the transaction history.

### 2.6 Reward

Reward class contains many attributes related to the customer monthly reward points details. Reward object would be distracted by RewardController and passed to reward_points_details.html.

### 2.7 RewardService

There is only one method findMaxReward() in the RewardService class, which is the primary core of our application. It receive the customer transaction amount data from RewardController, find the optimal solution for the highest reward points possible, and return the detailed conditions for how to achieve the optimal solution.

### 2.7 reward_points_details.html

reward_points_details.html displays all the relevant information for the reward points details. It looks like this:

![Testing_Demonstration_](C:\Users\haozh\Desktop\Testing_Demonstration_.jpg)