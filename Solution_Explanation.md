# Solution Explanation

In this document, we will analyze in detail some of the key issues encountered when coding the reward points system.

It would be helpful to read this explanation document while browsing the **RewardService.java** file to fully understand the intent of the code. RewardService.java is located at **Credit-Card-Reward-Points-System\src\main\java\com\creditcard\rewardpoints** in the project folder.

## 1. Reward Points Rule Simplification

Before rushing to the coding process, we need to look carefully at the 7 rules provided in the problem statement document to simplify the problem as much as possible in advance.

● Rule 1: 500 points for every $75 spend at Sport Check, $25 spend at Tim Hortons and $25 spend at Subway
● Rule 2: 300 points for every $75 spend at Sport Check and $25 spend at Tim Hortons
● Rule 3: 200 points for every $75 spend at Sport Check
● Rule 4: 150 points for every $25 spend at Sport Check, $10 spend at Tim Hortons and $10 spend at Subway
● Rule 5: 75 points for every $25 spend at Sport Check and $10 spend at Tim Hortons
● Rule 6: 75 points for every $20 spend at Sport Check
● Rule 7: 1 point for every $1 spend for all other purchases (including leftover amount)

Certainly, in order to achieve the highest total reward points with limited amount of transactions, we want to apply those rules with the **highest return rate** (rather than simply the highest absolute return). If it is obvious that one rule does not bring a higher return than another, there would be no need to apply it at all.

For example:

● Rule 3: 200 points for every $75 spend at Sport Check

● Rule 6: 75 point for every $20 spend at Sport Check

If we apply Rule 6 x 3 times, we earn 225 points (> 200) with $20 x 3 = $60 Sport Check amount as the cost, while we could only earn 200 points even in exchange for $75 Sport Check amount. Therefore, Rule 3 would be deprecated, since it is quite unlikely to encounter a situation that really requires Rule 3 to be applied.

Similarly:

● Rule 5: 75 points for every $25 spend at Sport Check and $10 spend at Tim Hortons

● Rule 6: 75 point for every $20 spend at Sport Check

Only the most stubborn people in the world would insist on applying Rule 5 rather than Rule 6 to save $5 Sport Check and $10 Tim Hortons purchase amount. Obviously, Rule 5 would also be (treated as) deprecated in our reward points problem.

Now there are only 5 rules left to deal with:

● **Rule 1**: 500 points for every $75 spend at Sport Check, $25 spend at Tim Hortons and $25 spend at Subway
● **Rule 2**: 300 points for every $75 spend at Sport Check and $25 spend at Tim Hortons
● **Rule 4**: 150 points for every $25 spend at Sport Check, $10 spend at Tim Hortons and $10 spend at Subway
● **Rule 6**: 75 points for every $20 spend at Sport Check
● **Rule 7**: 1 point for every $1 spend for all other purchases (including leftover amount)

We can draw a table below to show the characteristics of each rule. Return rate = (Reward Points Earned/ Amount Cost). Please note that all cost amounts of the 3 merchants are in multiples of 5, therefore we **divide all the amounts by 5** in the table to simplify the problem. Later we will use the same technique to **optimize** the algorithm and significantly save memory.

**Table 1**: Reward Points Return Rate

| Rule | SC Amount | TH Amount | SW Amount | Reward Points | SC Rate | TH Rate | SW Rate | Total Return Rate |
| :--: | :-------: | :-------: | :-------: | :-----------: | :-----: | :-----: | :-----: | :---------------: |
|  1   | 15 ($75)  |  5 ($25)  |  5 ($25)  |      500      |  6.667  |   20    |   20    |         4         |
|  2   | 15 ($75)  |  5 ($25)  |     0     |      300      |    4    |   12    |   N/A   |         3         |
|  4   |  5 ($25)  |  2 ($10)  |  2 ($10)  |      150      |    6    |   15    |   15    |       3.333       |
|  6   |  4 ($20)  |     0     |     0     |      75       |  3.75   |   N/A   |   N/A   |       3.75        |
|  7   |   *any    |   *any    |   *any    |       1       |   *1    |   *1    |   *1    |         1         |

## 2. Algorithms

### 2.1 First Idea

We could observe a high and low difference between the return rates in Table 1. A natural idea is to set a **priority** for these rules and apply **Greedy Algorithm** to match these rules one by one in priority order to accumulate the total reward points. Such an algorithm would run efficiently, but a serious problem is that the greedy algorithm **cannot** guarantee that the resulting solution is globally optimal, which is unacceptable for a credit card background problem.

Although greedy algorithm was abandoned, it has the potential to assist in speeding up the algorithm, which we will discuss later in the document.

### 2.2 Knapsack Problem

We could abstract this credit card reward points problem into a **Three-Dimensional Unbounded Knapsack Problem** (**3D UKP**).

For the input transaction history data, actually we only care about the total purchase amount of each merchant for a month. Furthermore, we could get rid of all the irrelative information of the input data and simplify it to the following table:

**Table 2**: Simplification of Input Data

|            | Sport Check | Tim Hortons | Subway | Other (Outside of previous 3) |
| :--------: | :---------: | :---------: | :----: | :---------------------------: |
| **Amount** |     AAA     |     BBB     |  CCC   |              ZZZ              |

Where 'ZZZ' refers to the total amount of all other merchants outside of Sport Check, Tim Hortons, and Subway. 'ZZZ' would be counted when calculating the leftover amount (Rule 7), while it has nothing to do with the knapsack problem we are about to discuss.

'AAA', 'BBB', 'CCC' refer to the total purchase amount of Sport Check, Tim Hortons, and Subway. They are actually **3 different** '**weight limits of the knapsack**' (rather than 1 single weight) in three-dimensional unbounded knapsack problem.

The 'rules' we mentioned previously are similar to the concept of '**items**' in the knapsack problem. If we return to Table 1 mentioned previously, we could notice that every rule costs a different amount for each merchant, which is the same as the item takes weight in knapsack problem. Since every rule could be applied as many times as we want, the knapsack problem is supposed to be an unbounded one (infinite number of items available).

### 2.3 Dynamic Programming

Knapsack Problem is a NP-complete problem, and the best solution would be **Dynamic Programming**. Dynamic programming algorithm is quite computationally intensive, but it could guarantee that the solution obtained is globally optimal, which fits our needs in the credit card background of our assessment.

We define our dp as:

```java
dp[ruleNum][sportsAmount][timAmount][subwayAmount][]
```

Where 'ruleNum' refers to the first 'ruleNum' rules applied for promotion,

'sportsAmount' refers to the maximum amount of Sports Check that could be used for calculation,

'timAmount' refers to the maximum amount of Tim Hortons that could be used for calculation,

'subwayAmount' refers to the maximum amount of Subway that could be used for calculation.

The last dimension of the array would be:

```java
dp[ruleNum][sportsAmount][timAmount][subwayAmount] == [points, r1, r2, r3, r4]
```

Where 'points' represent the maximum reward points we could achieve currently,

'r1', 'r2', 'r3', 'r4' refer to the number of each rule (1, 2, 4, 6) that applied currently.

'points' is actually the primary target of our dynamic programming algorithm, and dp makes every effort to achieve a 'points' as higher as possible.

'r' is designed to record which exact rule is applied in the dynamic programming process for the convenience of calculating reward points of each transaction.

The state transition equation would be:

```java
dp[rule][sc][th][sw][0] = Math.max(dp[rule - 1][sc][th][sw][0], dp[rule][sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]][0] + points[rule]);
```

There are two cases:

1. If the current rule is not applied

   ```java
   dp[rule - 1][sc][th][sw][0] // dp equals the max points of rule - 1 applied 
   ```

2. If the current rule is applied

   ```java
   dp[rule][sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]][0] + points[rule]
   // dp equals the max points of previously traversed case of less weight limit (current weight limit minus amount consumption) plus the reward points earned from the current rule 
   ```

   We will discuss the consumption array later in the document.

Where the points array is the collection of the reward points earned if a specific rule is applied:

```java
int[] points = {0, 500, 300, 150, 75};
```

And 'rule' refers to the rule applied right now:

```java
/*
Applied rule in dynamic programming:
rule = 0: No rule is applied
rule = 1: Rule 1: 500 points for every $75 spend at Sport Check, $25 spend at Tim Hortons and $25 spend at Subway
rule = 2: Rule 2: 300 points for every $75 spend at Sport Check and $25 spend at Tim Hortons
rule = 3: Rule 4: 150 points for every $25 spend at Sport Check, $10 spend at Tim Hortons and $10 spend at Subway
rule = 4: Rule 6: 75 point for every $20 spend at Sport Check
*/
```

Please note that we could know how many times Rule 7 was applied simply by counting all the leftover amount of all merchants after the dynamic programming algorithm is completed, which means we do not have to take Rule 7 into the algorithm at this stage.

When rule = 0, there would be no rule applied at all, which means the reward points we could get in this case would only be 0. Therefore we have to initialize dp[rule = 0] with 0:

```java
Arrays.fill(dp[rule][sc][th][sw], 0);	// rule == 0
```

In this stage we could come up with the initial algorithm code:

```java
for (int rule = 1; rule <= ruleNum; rule ++) {
    for (int sc = 0; sc <= sportsCapacity; sc ++) {
        for (int th = 0; th <= timCapacity; th ++) {
            for (int sw = 0; sw <= subwayCapacity; sw ++) {
                dp[rule][sc][th][sw] = dp[rule - 1][sc][th][sw];

                if (sc >= consumption[rule][0] && 
                    th >= consumption[rule][1] && 
                    sw >= consumption[rule][2]) {
                    dp[rule][sc][th][sw] = Math.max(
                        dp[rule - 1][sc][th][sw], 

                        dp[rule][sc - consumption[rule][0]]
                        [th - consumption[rule][1]][sw - consumption[rule][2]] 
                        + points[rule]
                    );
                }
            }
        }
    }
}
```

### 2.4 Algorithm optimization

#### 2.4.1

In Table 1, as we previously mentioned, we **divide all the amounts by 5** since all cost amounts of these 3 merchants are in **multiples of 5**. We could use this trick to significantly reduce the size of dp array in our algorithm.

In the classic dynamic programming algorithm, the dp array of amount of one merchant  in our problem would be built like:

| Amount == 0 | Amount = 1 | Amount = 2 | Amount = 3 | ...... | Amount = Total Amount |
| ----------- | ---------- | ---------- | ---------- | ------ | --------------------- |

We could build our dp array like this (Refer to Table 1):

| Cost = 0 (Amount = 0) | Cost = 1 (Amount = 5) | Cost = 2 (Amount = 10) | Cost = 3 (Amount = 15) | ...... | Cost=Total/5 (Amount=Total) |
| --------------------- | --------------------- | ---------------------- | ---------------------- | ------ | --------------------------- |

Code implementation:

```java
// The capacity consumption for each rule (Similar to the weights in knapsack problem)
// e.g. consumption[1] = {15, 5, 5} refers to Rule 1: Sport Check 15 ($75), Tim Hortons 5 ($25), Subway 5 ($25)
int[][] consumption = {{0, 0, 0}, {15, 5, 5}, {15, 5, 0}, {5, 2, 2}, {4, 0, 0}};
```

Please note that the input amount is in unit of cents (aka. 100 cent == 1 dollar). Therefore, we should correspondingly divide the input data ('AAA', 'BBB', 'CCC' in Table 2) by 500 as well to fit the algorithm:

```java
// To optimise space complexity, every capacity == 1 actually means 500 cents == 5 dollars
// NOTE: Remember in the dp loops every capacity++ means 5 dollars rise now
int sportsCapacity = sportsAmount / 500;
int timCapacity = timAmount / 500;
int subwayCapacity = subwayAmount / 500;
```

With this technique, we could **reduce both time and space consumption** of the algorithm by (1 - 0.2^3) = **99.2%**.

#### 2.4.2

We could observe that in our maximum reward points comparison part of dynamic programming:

```java
dp[rule][sc][th][sw][0] = Math.max(dp[rule - 1][sc][th][sw][0], dp[rule][sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]][0] + points[rule]);
```

Current dp[rule] only relies on the dp[rule - 1] to do the comparison, which means the previous dp[0] ... dp[rule - 2] is unnecessary for the algorithm. Therefore, we discard the 'ruleNum' dimension of dp and build only 2 dp arrays instead, dp and preDp, with 4 dimensions.

In this case, we could **reduce the space consumption** of the algorithm by (5 - 2)/5 = **60%**.

Our **FINAL CODE** of dynamic programming is here:

```java
// preDp is the previous ([rule - 1) dp of current dp ([rule])
int[][][][] dp = new int[sportsCapacity + 1][timCapacity + 1][subwayCapacity + 1][ruleNum + 1];
int[][][][] preDp = new int[sportsCapacity + 1][timCapacity + 1][subwayCapacity + 1][ruleNum + 1];

// Initialize dp
// If there is no rule applied, obviously the reward points would all be 0 no matter what capacities are
for (int sc = 0; sc <= sportsCapacity; sc ++) {
    for (int th = 0; th <= timCapacity; th ++) {
        for (int sw = 0; sw <= subwayCapacity; sw ++) {
            Arrays.fill(preDp[sc][th][sw], 0);
        }
    }
}

// Dynamic programming
for (int rule = 1; rule <= ruleNum; rule ++) {
    for (int sc = 0; sc <= sportsCapacity; sc ++) {
        for (int th = 0; th <= timCapacity; th ++) {
            for (int sw = 0; sw <= subwayCapacity; sw ++) {
                dp[sc][th][sw] = preDp[sc][th][sw];

                if (sc >= consumption[rule][0] && 
                    th >= consumption[rule][1] && 
                    sw >= consumption[rule][2]) {
                    if (preDp[sc][th][sw][0] < dp[sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]][0] + points[rule]) {
                        dp[sc][th][sw][0] = dp[sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]][0] + points[rule];
                        dp[sc][th][sw][rule] = dp[sc - consumption[rule][0]][th - consumption[rule][1]][sw - consumption[rule][2]][rule] + 1;

                        // When maximum reward points is found, record the dp index
                        if (dp[sc][th][sw][0] > maxPoints) {
                            maxPoints = dp[sc][th][sw][0];
                            sportsUsedCapacity = sc;
                            timUsedCapacity = th;
                            subwayUsedCapacity = sw;
                        }
                    }
                }
            }
        }
    }
}
```

**Time Complexity: O(RABC)**

**Space Complexity: O(ABC)**

Where **R** = number of rules, **A** = total transaction amount in Sport Check, **B** = total transaction amount in Tim Hortons, **C** = total transaction amount in Subway.

### 2.5 Further Thoughts

There are some immature ideas that fail to be implemented in the code due to the assessment time limit, while I suppose they still have their value of discussion.

I noticed that the algorithm would terminate unexpectedly due to lack of memory on my computer when the customer's total purchase amount reaches the 10,000 dollar level (1,000,000 cent). While the time consumption is relatively acceptable, the demand for **memory saving** is quite strong in comparison.

#### 2.5.1 Pre Calculation

There are quite some duplicate calculations every time we input new data. A map storing the **pre-calculated results** (within the parameter range of the purchase amounts of the vast majority of customers) could be built to reduce duplicate calculations. When receiving an input data, we first check if the result could be found in the pre-calculated map. If yes, return the result directly without doing any annoying calculations. If not, call the dp function in this case. 

#### 2.5.2 Maybe Greedy Algorithm Could Still Help 

Check Table 1. (Table 1 is really important!)

We could see that Rule 1 has a return rate of up to 4, far higher than those of all other rules (< 3.75). If the current amounts allow Rule 1 to be applied, this act could never go wrong, since there is no rule exists that could be applied and earn higher reward points than Rule 1.

A very natural idea is, we **apply Rule 1 as many times as we can at first** (Greedy), and leave the intermediate result after greedy to dynamic programming algorithm. Since the greedy algorithm would be of time O(A + B + C), this could roughly save 25% of the time consumption.

## 3. Reward Points Calculation for Each Transaction

It is relatively easy to calculate the maximum total reward points we could reach for a customer's monthly transaction history, while it is another story to calculate the reward points contributed for each individual transaction. 

The FAQ mentions that "For a given list of monthly transactions for a customer, the system should return the maximum reward points earned for **each transaction**, as well as the total maximum rewards points earned for the month for that customer."

And "If the monthly transactions have more than one transaction for the same merchant, the system should **combine** all the transaction's purchase amount for that merchant and calculate the reward points on the **accumulated** amount."

Let us say we have a customer with a transaction history including a large number of transactions to all the 3 merchants (Sport Check, Tim Hortons, Subway). Obviously, there are multiple transactions with **multiple** rules (quite a lot if the total amount is high enough!) that could apply to them. The problem is, when we have to return the reward points earned for each transaction, it is pretty hard to identify the **exact rule applied to it**.

For example:

Sport Check → T1: $50 T2: $30 T3: $20 → Total monthly purchase amount: $100
Tim Hortons → T4: 25 → Total monthly purchase amount: $25

We could see that:

Rule 2: 300 points for every $75 spend at Sport Check and $25 spend at Tim Hortons → Promotion Applied **x 1**
Rule 6: 75 points for every $20 spend at Sport Check → Promotion Applied **x 1**
Rule 7: 1 point for every $1 spend for all other purchases → Promotion Applied **x 5**

**Total** points: 300 + 75 + 5 = **380** points

**NOTE**: As a **temporary solution**, in my code the following rules are designed and applied for the calculation of each transaction contribution:

For T4, since it is supposed to contribute **part** of the Rule 2 promotion, T4 points earned =  300 * 25 / (75 + 25) = 75 points

For T1 - T3, it would be quite hard to say which exact rule applies to a specific transaction. 

(T1 → Rule 2? Rule 6? Rule 7? T1 could probably contribute **some or all** of these 3 rules!)

We could calculate the reward points earned for each transaction **based on their average contributions**:

Sport Check total purchase amount: $50 + $30 + $20 = $100
Sport Check total reward points earned (**strictly** from sport check): 300 * 75 / (75 + 25) + 75 + 5 = 305 points
Sport Check reward points earn rate (points per dollar): 305 / $100 = 3.05 points / $1
T1 points: 50 * 3.05 = 152.5 points
T2 points: 30 * 3.05 = 91.5 points
T3 points: 20 * 3.05 = 61 points

In this case, the total reward points for T1 - T4 would still be: 152.5 + 91.5 + 61 + 75 = **380** points, which perfectly matches our expectation.
