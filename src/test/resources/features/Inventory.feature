Feature: Calculation the stock of milk, sugar, coffee and chocolate in inventory
  Everytime that we make some beverages it should decrease the amount of inventory
  or if we add some stock of ingredients the amount of inventory should increase

  Background:
    Given The coffeemaker is ready to use.

    Scenario: Add some milk, coffee and chocolate to inventory
      When I add 10 sugar, 10 milk, 0 coffee, and 10 chocolate to our inventory
      Then the amount of milk, sugar, coffee, and chocolate should increase

    Scenario: If we make some beverages the ingredients should decrease
      When I order recipe#0 with 100 Baht
      Then the amount of milk, sugar, coffee, and chocolate should decrease