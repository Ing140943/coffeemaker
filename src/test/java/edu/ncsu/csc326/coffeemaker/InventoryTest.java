package edu.ncsu.csc326.coffeemaker;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import static org.junit.Assert.*;

public class InventoryTest {
    /**
     * Initializes some recipes to test with and the {@link CoffeeMaker}
     * object we wish to test.
     * amount when setting up the recipe.
     */
    private CoffeeMaker coffeeMaker;

    // Sample recipes to use in testing.
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;

    /**
     * Initializes some recipes to test with and the {@link CoffeeMaker}
     * object we wish to test.
     *
     * @throws RecipeException if there was an error parsing the ingredient
     *                         amount when setting up the recipe.
     */
    @Before
    public void setUp() throws RecipeException {
        coffeeMaker = new CoffeeMaker();

        //Set up for r1
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        recipe1.setAmtChocolate("0");
        recipe1.setAmtCoffee("3");
        recipe1.setAmtMilk("1");
        recipe1.setAmtSugar("1");
        recipe1.setPrice("50");

        //Set up for r2
        recipe2 = new Recipe();
        recipe2.setName("Mocha");
        recipe2.setAmtChocolate("20");
        recipe2.setAmtCoffee("3");
        recipe2.setAmtMilk("1");
        recipe2.setAmtSugar("1");
        recipe2.setPrice("75");

        //Set up for r3
        recipe3 = new Recipe();
        recipe3.setName("Latte");
        recipe3.setAmtChocolate("0");
        recipe3.setAmtCoffee("3");
        recipe3.setAmtMilk("3");
        recipe3.setAmtSugar("1");
        recipe3.setPrice("100");
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with well-formed quantities
     * Then we do not get an exception trying to read the inventory quantities.
     *
     * @throws InventoryException  if there was an error parsing the quanity
     * 		to a positive integer.
     */
    @Test
    public void testAddInventoryWithPositiveInteger() throws InventoryException {
        coffeeMaker.addInventory("0","0","0","0");
        coffeeMaker.addInventory("5","5","5","5");
        coffeeMaker.addInventory("0","1","1","1");
        coffeeMaker.addInventory("1","0","1","1");
        coffeeMaker.addInventory("1","1","5","5");
        coffeeMaker.addInventory("5","5","5","5");
    }

    /**
     * Test to add negative value for coffee in inventory.
     * Then we get an inventory exception.
     */
    @Test(expected = InventoryException.class)
    public void testAddNegativeIntegerCoffee() throws InventoryException {
        coffeeMaker.addInventory("-5", "0", "1", "0");
        coffeeMaker.addInventory("-10", "0", "0", "0");
        coffeeMaker.addInventory("-100", "0", "0", "0");
    }

    /**
     * Test to add negative value for milk in inventory.
     * Then we get an inventory exception.
     */
    @Test(expected = InventoryException.class)
    public void testAddNegativeIntegerMilk() throws InventoryException {
        coffeeMaker.addInventory("0", "-5", "0", "0");
        coffeeMaker.addInventory("0", "-10", "0", "0");
        coffeeMaker.addInventory("0", "-100", "0", "0");
    }

    /**
     * Test to add negative value for sugar in inventory.
     * Then we get an inventory exception.
     */
    @Test(expected = InventoryException.class)
    public void testAddNegativeIntegerSugar() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "-5", "0");
        coffeeMaker.addInventory("0", "0", "-10", "0");
        coffeeMaker.addInventory("0", "0", "-100", "0");
    }

    /**
     * Test to add negative value for sugar in inventory.
     * Then we get an inventory exception.
     */
    @Test(expected = InventoryException.class)
    public void testAddNegativeIntegerChocolate() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "0", "-5");
        coffeeMaker.addInventory("0", "0", "0", "-10");
        coffeeMaker.addInventory("0", "0", "0", "-100");
    }

    /**
     * Test to add non-number value for coffee in inventory.
     * Then we get an inventory exception.
     */
    @Test(expected = InventoryException.class)
    public void testAddNonNumCoffee() throws InventoryException {
        coffeeMaker.addInventory("abc", "1", "1", "1");
        coffeeMaker.addInventory("Java", "1", "1", "1");
        coffeeMaker.addInventory("L", "1", "1", "1");
    }

    /**
     * Test to add non-number value for milk in inventory.
     * Then we get an inventory exception.
     */
    @Test(expected = InventoryException.class)
    public void testAddNonNumMilk() throws InventoryException {
        coffeeMaker.addInventory("1", "abc", "1", "1");
        coffeeMaker.addInventory("1", "Java", "1", "1");
        coffeeMaker.addInventory("1", "L", "1", "1");
    }

    /**
     * Test to add non-number value for sugar in inventory.
     * Then we get an inventory exception.
     */
    @Test(expected = InventoryException.class)
    public void testAddNonNumSugar() throws InventoryException {
        coffeeMaker.addInventory("1", "1", "abc", "1");
        coffeeMaker.addInventory("1", "1", "Java", "1");
        coffeeMaker.addInventory("1", "1", "L", "1");
    }

    /**
     * Test to add non-number value for sugar in inventory.
     * Then we get an inventory exception.
     */
    @Test(expected = InventoryException.class)
    public void testAddNonNumChocolate() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "0", "abc");
        coffeeMaker.addInventory("0", "0", "0", "Java");
        coffeeMaker.addInventory("0", "0", "0", "Lol");

    }

    /**
     * Test to check the number of each type in inventory
     * Then we will get the correct amount of ingredients.
     */
    @Test
    public void testCheckInventory() throws InventoryException {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n",coffeeMaker.checkInventory());
        coffeeMaker.addInventory("5","5","0","5");
        assertEquals("Coffee: 20\nMilk: 20\nSugar: 15\nChocolate: 20\n",coffeeMaker.checkInventory());

    }

    /**
     * When we make coffee we should have sufficient resource
     * Then the ingredients should decrease.
     */
    @Test
    public void testMakeCoffeeWithSufficientResource() throws InventoryException {
        Inventory demo = new Inventory();
        assertTrue(demo.useIngredients(recipe1));
        assertEquals("Coffee: 12\nMilk: 14\nSugar: 14\nChocolate: 15\n",coffeeMaker.checkInventory());
        coffeeMaker.addInventory("5","5","0","5");
        assertTrue(demo.useIngredients(recipe2));
        assertEquals("Coffee: 14\nMilk: 18\nSugar: 13\nChocolate: 0\n",coffeeMaker.checkInventory());
    }

    /**
     * When we make coffee we should have sufficient resource, but we don't.
     * Then we can't make the coffee.
     */
    @Test
    public void testMakeCoffeeWithInsufficientResource() {
        Inventory demo = new Inventory();
        assertTrue(demo.useIngredients(recipe1));
        assertFalse(demo.useIngredients(recipe2));
        assertTrue(demo.useIngredients(recipe3));
    }

    /**
     * When we make coffee we should have enough coffee, but we don't.
     * Then we can't make the coffee.
     */
    @Test
    public void testNotEnoughCoffee() throws RecipeException {
        Recipe newRecipe = new Recipe();
        newRecipe.setAmtCoffee("20");
        newRecipe.setAmtMilk("0");
        newRecipe.setAmtChocolate("0");
        newRecipe.setAmtSugar("0");
        Inventory demo = new Inventory();
        assertFalse(demo.enoughIngredients(newRecipe));
    }

    /**
     * When we make coffee we should have enough coffee, but we don't.
     * Then we can't make the coffee.
     */
    @Test
    public void testNotEnoughMilk() throws RecipeException {
        Recipe newRecipe = new Recipe();
        newRecipe.setAmtCoffee("1");
        newRecipe.setAmtMilk("10");
        newRecipe.setAmtChocolate("1");
        newRecipe.setAmtSugar("1");
        Inventory demo = new Inventory();
        demo.useIngredients(newRecipe);
        assertFalse(demo.enoughIngredients(newRecipe));
    }

    /**
     * When we make coffee we should have enough sugar, but we don't.
     * Then we can't make the coffee.
     */
    @Test
    public void testNotEnoughSugar() throws RecipeException {
        Recipe newRecipe = new Recipe();
        newRecipe.setAmtCoffee("1");
        newRecipe.setAmtMilk("1");
        newRecipe.setAmtChocolate("1");
        newRecipe.setAmtSugar("10");
        Inventory demo = new Inventory();
        demo.useIngredients(newRecipe);
        assertFalse(demo.enoughIngredients(newRecipe));
    }

    /**
     * When we make coffee we should have enough chocolate, but we don't.
     * Then we can't make the coffee.
     */
    @Test
    public void testNotEnoughChocolate() throws RecipeException {
        Recipe newRecipe = new Recipe();
        newRecipe.setAmtCoffee("1");
        newRecipe.setAmtMilk("1");
        newRecipe.setAmtChocolate("10");
        newRecipe.setAmtSugar("1");
        Inventory demo = new Inventory();
        demo.useIngredients(newRecipe);
        assertFalse(demo.enoughIngredients(newRecipe));
    }

    /**
     * When we make coffee we should set valid coffee.
     * Then we can make the coffee.
     */
    @Test
    public void testSetAmountCoffeeOfInventory() {
        Inventory factory = new Inventory();
        factory.setCoffee(10);
        assertEquals(10, factory.getCoffee());
        factory.setCoffee(-10);
        assertEquals(15, factory.getCoffee());
    }

    /**
     * When we make coffee we should set valid milk.
     * Then we can make the coffee.
     */
    @Test
    public void testSetAmountMilkOfInventory() {
        Inventory factory = new Inventory();
        factory.setMilk(10);
        assertEquals(10, factory.getMilk());
        factory.setMilk(-10);
        assertEquals(15, factory.getMilk());
    }

    /**
     * When we make coffee we should set valid sugar.
     * Then we can make the coffee.
     */
    @Test
    public void testSetAmountSugarOfInventory() {
        Inventory factory = new Inventory();
        factory.setSugar(10);
        assertEquals(10, factory.getSugar());
        factory.setSugar(-10);
        assertEquals(15, factory.getSugar());
    }

    /**
     * When we make coffee we should set valid chocolate.
     * Then we can make the coffee.
     */
    @Test
    public void testSetAmountChocolateOfInventory() {
        Inventory factory = new Inventory();
        factory.setChocolate(10);
        assertEquals(10, factory.getChocolate());
        factory.setChocolate(-10);
        assertEquals(15, factory.getChocolate());
    }

}
