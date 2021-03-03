@purchaseTest
Feature: Customer navigation through product categories: Phones, Laptops and Monitors

  Scenario:
    Given Accessing Demo Online Shop
    And Navigate to "Sony vaio i5" and AddToCart
    And Navigate to Laptop "Dell i7 8gb" and AddToCart
    And Navigate to Cart and delete "Dell i7 8gb" Laptop
    When Click on Place Order
    And Fill in all web form fields:
      | name  | country | city     | creditCard          | month  | year |
      | Osbel | Spain   | Zaragoza | 4444 5555 7887 0098 | march  | 2021 |
    And Click on purchase
    Then Capture and Log purchase Id and Amount
    And Assert purchase amount equals expected
    And Click on OK
