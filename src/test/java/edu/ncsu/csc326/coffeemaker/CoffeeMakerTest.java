/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 *
 * Permission has been explicitly granted to the University of Minnesota
 * Software Engineering Center to use and distribute this source for
 * educational purposes, including delivering online education through
 * Coursera or other entities.
 *
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including
 * fitness for purpose.
 *
 *
 * Modifications
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

import edu.ncsu.csc326.coffeemaker.Main.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Unit tests for CoffeeMaker class.
 *
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {

	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;

	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker}
	 * object we wish to test.
	 *
	 * @throws RecipeException  if there was an error parsing the ingredient
	 * 		amount when setting up the recipe.
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

		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
	}

	/**
	 * Add the valid recipe to recipe book.
	 * Then the recipe book should add the new recipe to the array.
	 */
	@Test
	public void testAddValidRecipe() {
		assertTrue(coffeeMaker.addRecipe(recipe1));
		assertTrue(coffeeMaker.addRecipe(recipe2));
		assertTrue(coffeeMaker.addRecipe(recipe3));
	}

	/**
	 * Add the invalid recipe to recipe book.
	 * Then the recipe book should add the new recipe with default value to the array.
	 */
	@Test
	public void testAddInvalidRecipe() throws RecipeException {
		Recipe recipe = new Recipe();
		recipe.setName("White Malt");
		try {
			recipe.setAmtChocolate("-4");
			recipe.setAmtCoffee("0");
			recipe.setAmtMilk("-1");
			recipe.setAmtSugar("-1");
			recipe.setPrice("6");
		}
		catch (RecipeException e){
			assertTrue(coffeeMaker.addRecipe(recipe));
		}
	}

	/**
	 * Given a coffee maker with none recipe, so we have to add our recipe first
	 * When we add the recipe with specific name, well-formed quantities of milk, coffee, sugar, and price
	 * Then we do not get an exception trying to read recipe quantities.
	 *
	 */
	@Test
	public void testAddRecipeMoreThan3(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.addRecipe(recipe4); // should not add this recipe
		assertEquals(3, coffeeMaker.getRecipes().length);
	}

	/**
	 * Add the same recipe to the recipe book.
	 * Then the recipe ook should deny the duplicate recipe.
	 */
	@Test
	public void testAddSameRecipe() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		assertFalse(coffeeMaker.addRecipe(recipe1));
		assertFalse(coffeeMaker.addRecipe(recipe2));
		assertFalse(coffeeMaker.addRecipe(recipe3));
	}

	/**
	 * Test delete recipe from the recipe book.
	 * Then that recipe should not exist in the recipe book.
	 */
	@Test
	public void testDeleteExistRecipe() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.deleteRecipe(0);
		assertNotEquals(recipe1, coffeeMaker.getRecipes()[0]);
		assertEquals(recipe2, coffeeMaker.getRecipes()[1]);
		assertEquals(recipe3, coffeeMaker.getRecipes()[2]);
	}

	/**
	 * Test delete recipe which not exist from the recipe book.
	 * Then the recipe book should not delete any existing recipe.
	 */
	@Test
	public void testDeleteNonExistRecipe() {
		assertEquals(null, coffeeMaker.deleteRecipe(0));
		assertEquals(null, coffeeMaker.deleteRecipe(1));
		assertEquals(null, coffeeMaker.deleteRecipe(2));
	}

	/**
	 * Test to edit the existing recipe in the recipe book.
	 */
	@Test
	public void testEditExistRecipe() throws RecipeException {
		coffeeMaker.addRecipe(recipe1);
		Recipe editRecipe = new Recipe();
		editRecipe.setName("New");
		editRecipe.setAmtChocolate("5");
		editRecipe.setAmtCoffee("5");
		editRecipe.setAmtMilk("5");
		editRecipe.setAmtSugar("5");
		editRecipe.setPrice("5");
		coffeeMaker.editRecipe(0, editRecipe);
		assertEquals(coffeeMaker.getRecipes()[0].getAmtCoffee(), editRecipe.getAmtCoffee());
		assertEquals(coffeeMaker.getRecipes()[0], editRecipe);

	}

	/**
	 * Test to edit the non-existing recipe in the recipe book.
	 * Then it should throw exception.
	 */
	@Test
	public void testEditNonExistRecipe() throws RecipeException {
		Recipe editRecipe = new Recipe();
		editRecipe.setName("New");
		editRecipe.setAmtChocolate("5");
		editRecipe.setAmtCoffee("5");
		editRecipe.setAmtMilk("5");
		editRecipe.setAmtSugar("5");
		editRecipe.setPrice("5");
		assertNull(coffeeMaker.editRecipe(0, editRecipe));

	}

	/**
	 * When we edit the recipe the name of beverage
	 * Then the name should not change.
	 */
	@Test
	public void testEditMayNotChangeName() {
		coffeeMaker.addRecipe(recipe1);
		Recipe  changeName = new Recipe();
		changeName.setName("A");
		coffeeMaker.editRecipe(0,changeName);
		assertEquals("Coffee", coffeeMaker.getRecipes()[0].getName());

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
		coffeeMaker.addInventory("-5", "1", "1", "1");
		coffeeMaker.addInventory("-10", "1", "1", "1");
		coffeeMaker.addInventory("-100", "1", "1", "1");
		coffeeMaker.addInventory("-1", "0", "0", "0");
		coffeeMaker.addInventory("-500", "0", "0", "0");
	}

	/**
	 * Test to add negative value for milk in inventory.
	 * Then we get an inventory exception.
	 */
	@Test(expected = InventoryException.class)
	public void testAddNegativeIntegerMilk() throws InventoryException {
		coffeeMaker.addInventory("1", "-5", "1", "1");
		coffeeMaker.addInventory("1", "-10", "1", "1");
		coffeeMaker.addInventory("1", "-100", "1", "1");
		coffeeMaker.addInventory("1", "-1", "0", "0");
		coffeeMaker.addInventory("1", "-500", "0", "0");
	}

	/**
	 * Test to add negative value for sugar in inventory.
	 * Then we get an inventory exception.
	 */
	@Test(expected = InventoryException.class)
	public void testAddNegativeIntegerSugar() throws InventoryException {
		coffeeMaker.addInventory("1", "1", "-5", "1");
		coffeeMaker.addInventory("1", "1", "-10", "1");
		coffeeMaker.addInventory("1", "1", "-100", "1");
		coffeeMaker.addInventory("1", "1", "-1", "0");
		coffeeMaker.addInventory("1", "1", "-500", "0");
	}

	/**
	 * Test to add negative value for sugar in inventory.
	 * Then we get an inventory exception.
	 */
	@Test(expected = InventoryException.class)
	public void testAddNegativeIntegerChocolate() throws InventoryException {
		coffeeMaker.addInventory("1", "1", "1", "-5");
		coffeeMaker.addInventory("1", "1", "1", "-10");
		coffeeMaker.addInventory("1", "1", "1", "-100");
		coffeeMaker.addInventory("1", "1", "0", "-1");
		coffeeMaker.addInventory("1", "1", "0", "-500");
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
		coffeeMaker.addInventory("U", "0", "0", "0");
		coffeeMaker.addInventory("V", "0", "0", "0");
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
		coffeeMaker.addInventory("0", "U", "0", "0");
		coffeeMaker.addInventory("0", "V", "0", "0");
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
		coffeeMaker.addInventory("0", "0", "U", "0");
		coffeeMaker.addInventory("0", "0", "V", "0");
	}

	/**
	 * Test to add non-number value for sugar in inventory.
	 * Then we get an inventory exception.
	 */
	@Test(expected = InventoryException.class)
	public void testAddNonChocolate() throws InventoryException {
		coffeeMaker.addInventory("1", "1", "1", "abc");
		coffeeMaker.addInventory("1", "1", "1", "Java");
		coffeeMaker.addInventory("1", "1", "1", "L");
		coffeeMaker.addInventory("0", "0", "0", "U");
		coffeeMaker.addInventory("0", "0", "0", "V");
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
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than
	 * 		the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffeeWithSufficientPay() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe3);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
		assertEquals(75, coffeeMaker.makeCoffee(1, 175));

	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than
	 * 		the coffee costs
	 * Then we shouldn't get the coffee and return their money.
	 */
	@Test
	public void testMakeCoffeeWithInsufficientPay() {
		coffeeMaker.addRecipe(recipe1);
		int money = 10;
		int change = coffeeMaker.makeCoffee(0,money);
		assertEquals(change, money);
	}

}