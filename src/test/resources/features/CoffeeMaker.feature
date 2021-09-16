Feature: Make beverage from the customer's order
  CoffeeMake can make beverage from the customers order and
  calculate the change. If the customers don't have enough
  money, then the CoffeeMaker will return their money.

  Background:
    Given Coffeemaker have recipe0, recipe1 and recipe2

  Scenario: Customers order beverage with the right amount of money
    When I order recipe#0 with 100 Baht
    Then It will return change 0 to customer, calculate from 100 Baht subtract cost of recipe#0

  Scenario: Customer order beverage with lack of money
    When I order recipe#1 with 50 Baht
    Then It will return change 50 to customer, calculate from 50 Baht subtract cost of recipe#1

  Scenario: Customer order beverage with the over amount of money
    When I order recipe#2 with 1000 Baht
    Then It will return change 900 to customer, calculate from 1000 Baht subtract cost of recipe#2