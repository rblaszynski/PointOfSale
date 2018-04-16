# PointOfSale
Presented project is an implementation of a simple point of sale application with barcodes scanner and two output devices: LCD display and printer.
## Description
One window simulates barcode scannner where user can enter barcodes and the second one is a LCD Display.

LCD display shows the last product entered to the system:

![Barcode Scanner and LCD Display](https://github.com/rblaszynski/PointOfSale/blob/master/img/windows.png)

After pressing _Print_ button on LCD display user can see the entire order:

![LCD after print](https://github.com/rblaszynski/PointOfSale/blob/master/img/lcddisplay.png)




To store products and orders I created a simple database with 3 tables:

![db uml diagram](https://github.com/rblaszynski/PointOfSale/blob/master/img/posdb.png)
```
ProductID | ProductName | UnitPrice | Barcode
---------------------------------------------
1         | Tomato      | 3.2       | 111 
---------------------------------------------
2         | Apple       | 2.6       | 135 
---------------------------------------------
3         | Bread       | 3.9       | 458 
---------------------------------------------
...

```
```
OrderID          | ProductID
----------------------------
20180416_144654  | 1
----------------------------
20180416_144654  | 9
----------------------------
...
```
```
OrderID          | OrderDate            | TotalPrice
----------------------------------------------------
20180416_144654  | 2018-04-16 14:46:54  | 17.7000
----------------------------------------------------
```

To design the application I used model-view-controller architectural pattern:

<img src="https://github.com/rblaszynski/PointOfSale/blob/master/img/pos.png" width="250">


## Getting Started

### Tools
* IDE: _IntelliJ IDEA_
* Test library: _JUnit 5.0_
* Database management system: _SQLite_

## Running the application

* Execute _App_ run configuration

## Running the tests

* Execute _All tests_ run configuration to run all tests
* Execute _JDBC/Model test_ run configuration to run JDBC/Model tests

### Tests:

#### JDBC tests:
* _select()_ - checks if select query return valid ProductName and UnitPrice (_Barcode=111 -> Tomato, 3.2_)

* _insertOrder()_ - checks if insert query insert correct information about order (_OrderID, ProductID, OrderDate and TotalPrice_)

#### Model tests:
* _checkValid()_ - checks if the application works correctly after entering:
  * Valid barcode (_111_)
  * Barcode that does not exist in database
  * Empty entry
  * Invalid barcode with too much digits
  * Invalid barcode that contains letters
* _printOrder()_ - checks if order is correctly shown on LCD Screen and printed to .txt file



## Author

**Robert Błaszyński**
