package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.Before;
import org.junit.Test;


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
		coffeeMaker.deleteRecipe(0);
		assertNotEquals(recipe1, coffeeMaker.getRecipes()[0]);
		assertNull(coffeeMaker.getRecipes()[0]);
	}

	/**
	 * Test delete recipe from the recipe book.
	 * Then that recipe should not exist in the recipe book even we remove many recipes.
	 */
	@Test
	public void testDeleteExistManyRecipes() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.deleteRecipe(0);
		coffeeMaker.deleteRecipe(1);
		coffeeMaker.deleteRecipe(2);
		assertNotEquals(recipe1, coffeeMaker.getRecipes()[0]);
		assertNotEquals(recipe1, coffeeMaker.getRecipes()[1]);
		assertNotEquals(recipe1, coffeeMaker.getRecipes()[2]);
		assertNull(coffeeMaker.getRecipes()[0]);
		assertNull(coffeeMaker.getRecipes()[1]);
		assertNull(coffeeMaker.getRecipes()[2]);
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

	/**
	 * Given a coffee maker with one invalid recipe
	 * When we make coffee, selecting the invalid recipe
	 * Then we shouldn't get the coffee and return their money.
	 */
	@Test
	public void testMakeCoffeeWithNoneRecipe() {
		int money = 10;
		int change = coffeeMaker.makeCoffee(0,money);
		assertEquals(change, money);
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe
	 * Then we shouldn't get the coffee because we don't have enough ingredients and return their money.
	 */
	@Test
	public void testMakeCoffeeWithNotEnoughIngredients() {
		coffeeMaker.addRecipe(recipe2);
		int money = 100;
		int change = coffeeMaker.makeCoffee(0,money);
		assertEquals(change, money);
	}
}