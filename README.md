Walmart assignment for Simple ticket service of a venue.

To install the project:

mvn clean install

To run test (all tests are written in Spock)

mvn test


The seating score for 10 rows and 10 seats per row is as follow:


     Stage
21,22,23,24,25,25,24,23,22,21

19,20,21,22,23,23,22,21,20,19

17,18,19,20,21,21,20,19,18,17

15,16,17,18,19,19,18,17,16,15

13,14,15,16,17,17,16,15,14,13

11,12,13,14,15,15,14,13,12,11

9,10,11,12,13, 13, 12,11,10,9

7, 8, 9, 10, 11, 11, 10,9,8,7

5 ,6 ,7 ,8 ,9 ,9 ,8 ,7 ,6 , 5

3, 4, 5, 6, 7, 7, 6, 5, 4, 3

Middle seats have the highest score for each row and moving away from middle seats in each direction loses value equally


TODOs
Better field validations,
Put the score method in a class implementing an interface,
Synchronize part of the reserveSeat method 

