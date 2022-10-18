# Testing Demonstration

## 1. Set Up

Credit-Card-Reward-Points-System is a **Spring** project. It runs depending on **Java 8**, **Spring Web Service**, **JPA**, **Thymeleaf**, and **Bootstrap**. Please ensure that the relevant environment is configured before running the program.

The project main function '**CreditCardRewardPointsSystemApplication.java**' is located at **Credit-Card-Reward-Points-System\src\main\java\com\creditcard\rewardpoints**.

Currently the program only accepts **one single month** of a customer's transaction history as input. If more than one month of transaction history is received, the program will raise error message. (I have considered implementing the function to filter input and display output by month, but there is not quite enough time for me to make it comes true)

It is suggested to use the sample csv data provided as the program input. They are located at **Credit-Card-Reward-Points-System\sample_input** folder. However, please feel free to create any input data you want only if it follows the format below:

| transactionId | date       | merchantCode | amountCents |
| ------------- | ---------- | ------------ | ----------- |
| #             | yyyy/MM/dd | XXXX         | 0000        |

When the project is running properly locally, please visit http://localhost:8080/reward/ to see the **home page**.

## 2. Demo Testing

### 2.1 Get Started

As a easy start, we take transactions_input_1.csv as the first input. It contains data like this:

<img src="C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017094203085.png" alt="image-20221017094203085" style="zoom: 80%;" />

Open the home page http://localhost:8080/reward/, we could see:

<img src="C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017094919034.png" alt="image-20221017094919034" style="zoom: 67%;" />

Using the '**choose file**' button to upload the csv input file. (Choose file button will always display in browser's native language)

<img src="C:\Users\haozh\Desktop\2022-10-17 0949.png" alt="2022-10-17 0949" style="zoom: 67%;" />

After choosing the file properly, click '**submit**' button to move forward to the **Monthly Reward Points Details page**.

![image-20221017095748227](C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017095748227.png)

The **total maximum rewards points earned** is displayed at the top of the page with **red** font. The
**maximum reward points earned for each transaction** could also be found in the 'Reward Points' column of the Transaction Details table. Other interesting information is also provided along in the details page.

Note that if certain rules are **not applied** at all (e.g. Rule 1 & 4), they would **not show** in the Promotion Applied table.

You could found some console output in the console terminal as well:

<img src="C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017101357290.png" alt="image-20221017101357290" style="zoom: 50%;" />

### 2.2 Standard Input

Now let us test the standard input mentioned in the Question document:

transactions = {
"T01": {"date": "2021-05-01", "merchant_code" : "sportcheck", "amount_cents": 21000},
"T02": {"date": "2021-05-02", "merchant_code" : "sportcheck", "amount_cents": 8700},
"T03": {"date": "2021-05-03", "merchant_code" : "tim_hortons", "amount_cents": 323},
"T04": {"date": "2021-05-04", "merchant_code" : "tim_hortons", "amount_cents": 1267},
"T05": {"date": "2021-05-05", "merchant_code" : "tim_hortons", "amount_cents": 2116},
"T06": {"date": "2021-05-06", "merchant_code" : "tim_hortons", "amount_cents": 2211},
"T07": {"date": "2021-05-07", "merchant_code" : "subway", "amount_cents": 1853},
"T08": {"date": "2021-05-08", "merchant_code" : "subway", "amount_cents": 2153},
"T09": {"date": "2021-05-09", "merchant_code" : "sportcheck", "amount_cents": 7326},
"T10": {"date": "2021-05-10", "merchant_code" : "tim_hortons", "amount_cents": 1321}
}

It looks like this in **transactions_input_2.csv**:

<img src="C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017101544145.png" alt="image-20221017101544145" style="zoom:80%;" />

Follow a process similar to 2.1, we could get the output page:

![image-20221017101712008](C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017101712008.png)

### 2.3 Massive Input

A massive input data **transactions_input_3.csv** is designed with 100 transactions and total purchase amount of 379316 cent (=3793 dollar).

```java
transactionId,date,merchantCode,amountCents
1,2022/10/16,sportcheck,4960
2,2022/10/16,tim_hortons,1751
3,2022/10/16,subway,950
4,2022/10/16,costco,15368
5,2022/10/16,walmart,10000
6,2022/10/16,sportcheck,8372
7,2022/10/16,tim_hortons,731
8,2022/10/16,amazon,4870
9,2022/10/16,tim_hortons,508
10,2022/10/16,sportcheck,1979
11,2022/10/16,tim_hortons,1078
12,2022/10/16,subway,1828
13,2022/10/16,tim_hortons,1248
14,2022/10/16,sportcheck,6431
15,2022/10/16,shoppers,2166
16,2022/10/16,tim_hortons,1614
17,2022/10/16,subway,563
18,2022/10/16,sportcheck,5224
19,2022/10/16,sportcheck,3281
20,2022/10/16,amazon,3571
21,2022/10/16,tim_hortons,1666
22,2022/10/16,sportcheck,3801
23,2022/10/16,subway,1651
24,2022/10/16,costco,12364
25,2022/10/16,tim_hortons,1435
26,2022/10/16,sportcheck,4192
27,2022/10/16,sportcheck,6927
28,2022/10/16,walmart,3770
29,2022/10/16,shoppers,1819
30,2022/10/16,sportcheck,5509
31,2022/10/16,tim_hortons,1365
32,2022/10/16,subway,1102
33,2022/10/16,amazon,3321
34,2022/10/16,sportcheck,5520
35,2022/10/16,tim_hortons,1797
36,2022/10/16,subway,975
37,2022/10/17,sportcheck,1970
38,2022/10/17,tim_hortons,1205
39,2022/10/17,tim_hortons,847
40,2022/10/17,shoppers,1563
41,2022/10/17,walmart,502
42,2022/10/17,sportcheck,4702
43,2022/10/17,sportcheck,6117
44,2022/10/17,tim_hortons,1971
45,2022/10/17,subway,1548
46,2022/10/17,sportcheck,6485
47,2022/10/17,shoppers,1354
48,2022/10/17,tim_hortons,1383
49,2022/10/17,sportcheck,5381
50,2022/10/17,tim_hortons,751
51,2022/10/17,subway,1956
52,2022/10/17,tim_hortons,1879
53,2022/10/17,sportcheck,12469
54,2022/10/17,subway,1411
55,2022/10/17,tim_hortons,677
56,2022/10/17,subway,1263
57,2022/10/17,sportcheck,5048
58,2022/10/17,sportcheck,11777
59,2022/10/17,amazon,5198
60,2022/10/17,tim_hortons,479
61,2022/10/17,sportcheck,6862
62,2022/10/17,subway,1867
63,2022/10/17,costco,9403
64,2022/10/17,tim_hortons,971
65,2022/10/17,tim_hortons,1246
66,2022/10/17,sportcheck,7958
67,2022/10/17,walmart,1971
68,2022/10/17,shoppers,1135
69,2022/10/17,sportcheck,11805
70,2022/10/17,tim_hortons,527
71,2022/10/17,subway,1582
72,2022/10/17,amazon,3983
73,2022/10/17,sportcheck,12709
74,2022/10/17,tim_hortons,971
75,2022/10/17,subway,1632
76,2022/10/17,sportcheck,11621
77,2022/10/17,tim_hortons,481
78,2022/10/17,tim_hortons,1866
79,2022/10/17,shoppers,679
80,2022/10/17,walmart,1632
81,2022/10/17,sportcheck,11871
82,2022/10/17,tim_hortons,1646
83,2022/10/17,tim_hortons,1934
84,2022/10/17,subway,1634
85,2022/10/17,sportcheck,12624
86,2022/10/17,shoppers,2012
87,2022/10/17,tim_hortons,1790
88,2022/10/17,sportcheck,12265
89,2022/10/17,tim_hortons,936
90,2022/10/17,tim_hortons,1358
91,2022/10/17,subway,1925
92,2022/10/17,sportcheck,14210
93,2022/10/17,tim_hortons,1185
94,2022/10/17,tim_hortons,1585
95,2022/10/17,shoppers,1324
96,2022/10/17,walmart,2398
97,2022/10/17,sportcheck,9765
98,2022/10/17,sportcheck,4002
99,2022/10/17,tim_hortons,1105
100,2022/10/17,subway,1203
```

Here is the output we get:

![image-20221017110331623](C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017110331623.png)

![image-20221017110400924](C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017110400924.png)

![image-20221017110434901](C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017110434901.png)

![image-20221017110459407](C:\Users\haozh\AppData\Roaming\Typora\typora-user-images\image-20221017110459407.png)