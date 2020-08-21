# University canteen SW

This application was developed as emulation of the Software managing the UNIBZ canteen; in our case, the canteen is provided by a DBMS which manages the whole data; more precisely:
•	Each student / professor has his own card (with chip) and with the relative balance
Students and professors have ID’s >= 100.
•	The government gives his contribution by paying a % (≈ 50%) of each student’s menu;
•	Each students card is allowed to benefit the government contribution 3 times a day; after that, the student should pay the full price of the menu;
•	A professor has to pay the full price of the menu;
•	Everyone can eat in the canteen (also without card) by paying in cash and providing his generalities;
•	The canteen system has different prices for the different menu’s, as follows:
1.	Full menu
full price 8.64 €, contribution of the Province 4.32 €, final price for students 4.32 €
2.	Light menu 
full price 6.90 €, contribution of the Province 3.45 €, final price for students 3.45 €
3.	Extra light menu
full price 5.20 €, contribution of the Province 2.60 €, final price for students 2.60 €
•	The canteen is open from Monday to Saturday at lunch and dinner and there is always at least 1 employee who will serve you (you can also charge your card directly there); 
•	By presenting a medical certificate attesting a food allergy or intolerance you are suffering from, you can have a meal according to your special needs. 
•	The DB provides an admin account which can be used by mantainance tehnicians or IT people.

The students / professors have access to a GUI application where can check their own balance and the made transactions.
The employees have the possibility through a GUI application to charge cards, sell menu’s, check statistics.

### Technologies used: Java, SQL
