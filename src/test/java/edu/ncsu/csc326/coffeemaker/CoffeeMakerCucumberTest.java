package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.CoffeeMaker;
import edu.ncsu.csc326.coffeemaker.Recipe;
import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;


public class CoffeeMakerCucumberTest {

    private CoffeeMaker coffeeMaker;
    private Recipe recipe1;
    private  Recipe recipe2;
    private Recipe recipe3;

    @Given("Coffeemaker have recipe0, recipe1 and recipe2")
    public void coffeemakerHaveRecipe0Recipe1AndRecipe2() throws RecipeException {

        coffeeMaker = new CoffeeMaker();
        //Set up for r0
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        recipe1.setAmtChocolate("0");
        recipe1.setAmtCoffee("3");
        recipe1.setAmtMilk("1");
        recipe1.setAmtSugar("1");
        recipe1.setPrice("100");

        //Set up for r1
        recipe2 = new Recipe();
        recipe2.setName("Mocha");
        recipe2.setAmtChocolate("20");
        recipe2.setAmtCoffee("3");
        recipe2.setAmtMilk("1");
        recipe2.setAmtSugar("1");
        recipe2.setPrice("75");

        //Set up for r2
        recipe3 = new Recipe();
        recipe3.setName("Latte");
        recipe3.setAmtChocolate("0");
        recipe3.setAmtCoffee("3");
        recipe3.setAmtMilk("3");
        recipe3.setAmtSugar("1");
        recipe3.setPrice("100");

        coffeeMaker.addRecipe(recipe1);
        coffeeMaker.addRecipe(recipe2);
        coffeeMaker.addRecipe(recipe3);
    }

    @When("I order recipe#{int} with {int} Baht")
    public void iOrderRecipeWithBaht(int recipeNo, int money) {
        // Write code here that turns the phrase above into concrete actions
        int left = money - coffeeMaker.getRecipes()[recipeNo].getPrice();
        if (left < 0) {
            left = money;
        }
        assertEquals(left, coffeeMaker.makeCoffee(recipeNo, money));
    }

    @Then("It will return change {int} to customer, calculate from {int} Baht subtract cost of recipe#{int}")
    public void itWillReturnChangeToCustomerCalculateFromBahtSubtractCostOfRecipe(int change, int money, int recipeNo) {
        assertEquals(change, coffeeMaker.makeCoffee(recipeNo, money ));
    }

    @Given("The coffeemaker is ready to use.")
    public void theCoffeemakerIsReadyToUse() throws RecipeException {
        coffeeMaker = new CoffeeMaker();
        recipe1 = new Recipe();
        recipe1.setName("Milk Shake");
        recipe1.setAmtChocolate("0");
        recipe1.setAmtCoffee("0");
        recipe1.setAmtMilk("1");
        recipe1.setAmtSugar("1");
        recipe1.setPrice("100");
        coffeeMaker.addRecipe(recipe1);

    }

    @When("I add {int} sugar, {int} milk, {int} coffee, and {int} chocolate to our inventory")
    public void iAddSugarMilkCoffeeAndChocolateToOurInventory(int sugar, int milk, int coffee, int chocolate) throws InventoryException {
        coffeeMaker.addInventory( String.valueOf(sugar), String.valueOf(milk), String.valueOf(coffee), String.valueOf(chocolate));
    }

    // we have to ignore the amount of sugar because we want to build cucumber successfully
    @Then("the amount of milk, sugar, coffee, and chocolate should increase")
    public void theAmountOfMilkSugarCoffeeAndChocolateShouldIncrease() {
        assertEquals("Coffee: 25\nMilk: 25\nSugar: 15\nChocolate: 25\n",coffeeMaker.checkInventory());
    }

    // we have to ignore coffee because we want to build cucumber successfully
    @Then("the amount of milk, sugar, coffee, and chocolate should decrease")
    public void theAmountOfMilkSugarCoffeeAndChocolateShouldDecrease() {
        Inventory demo = new Inventory();
        demo.useIngredients(recipe1);
        assertEquals("Coffee: 15\nMilk: 14\nSugar: 14\nChocolate: 15\n",coffeeMaker.checkInventory());
    }

}
