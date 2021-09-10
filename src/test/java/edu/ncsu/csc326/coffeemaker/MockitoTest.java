package edu.ncsu.csc326.coffeemaker;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class MockitoTest {
    private CoffeeMaker coffeeMakerMock;
    private Recipe recipe;
    private RecipeBook dummyRecipeBook;
    private Recipe [] recipes;


    /**
     * Initialize the setUp for the mock test.
     */
    @Before
    public void setUp() {
        Inventory inventory = new Inventory();
        dummyRecipeBook = mock(RecipeBook.class);
        recipe = mock(Recipe.class);
        coffeeMakerMock = new CoffeeMaker(dummyRecipeBook, inventory);
        recipes = new Recipe[] {recipe};
    }

    /**
     * Test that the user have enough payment for their beverage.
     */
    @Test
    public void testTheUserPayWithEnoughMoney() {
        when(recipe.getPrice()).thenReturn(100);
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        assertEquals(50, coffeeMakerMock.makeCoffee(0, 50)); // call getRecipes 2 times
        assertEquals(0, coffeeMakerMock.makeCoffee(0, 100)); // call getRecipes 4 times
        assertEquals(900, coffeeMakerMock.makeCoffee(0, 1000)); // call getRecipes 4 times
        assertEquals(100, coffeeMakerMock.makeCoffee(0, 200)); // call getRecipes 4 times
        assertEquals(25, coffeeMakerMock.makeCoffee(0, 125)); // call getRecipes 4 times
        verify(dummyRecipeBook, times(18)).getRecipes();
    }

    /**
     * Test that the user have enough payment for their beverage with many recipes.
     */
    @Test
    public void testTheUserPayWithEnoughMoneyRecipes() {
        Recipe  recipe2 = mock(Recipe.class);
        Recipe  recipe3 = mock(Recipe.class);
        recipes = new Recipe[] {recipe, recipe2, recipe3};
        when(recipe.getPrice()).thenReturn(100);
        when(recipe2.getPrice()).thenReturn(50);
        when(recipe3.getPrice()).thenReturn(10);
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        assertEquals(0, coffeeMakerMock.makeCoffee(1, 50));  // call getRecipes 4 times
        assertEquals(0, coffeeMakerMock.makeCoffee(0, 100)); // call getRecipes 4 times
        assertEquals(990, coffeeMakerMock.makeCoffee(2, 1000)); // call getRecipes 4 times
        assertEquals(100, coffeeMakerMock.makeCoffee(1, 150)); // call getRecipes 4 times
        assertEquals(0, coffeeMakerMock.makeCoffee(2, 10));  // call getRecipes 4 times
        verify(dummyRecipeBook, times(20)).getRecipes();
    }

    /**
     * Test that the user have enough payment for their beverage.
     */
    @Test
    public void testTheUserPayWithNotEnoughMoney() {
        when(recipe.getPrice()).thenReturn(1000);
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        assertEquals(50, coffeeMakerMock.makeCoffee(0, 50)); // call getRecipes 2 times
        assertEquals(100, coffeeMakerMock.makeCoffee(0, 100)); // call getRecipes 2 times
        assertEquals(300, coffeeMakerMock.makeCoffee(0, 300)); // call getRecipes 2 times
        verify(dummyRecipeBook, times(6)).getRecipes();
    }

    /**
     * Test that the user have enough payment for their beverage with many recipes.
     */
    @Test
    public void testTheUserPayWithNotEnoughMoneyRecipes() {
        Recipe  recipe2 = mock(Recipe.class);
        Recipe  recipe3 = mock(Recipe.class);
        recipes = new Recipe[] {recipe, recipe2, recipe3};
        when(recipe.getPrice()).thenReturn(1000);
        when(recipe2.getPrice()).thenReturn(5000);
        when(recipe3.getPrice()).thenReturn(1000);
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        assertEquals(50, coffeeMakerMock.makeCoffee(1, 50)); // call getRecipes 2 times
        assertEquals(100, coffeeMakerMock.makeCoffee(0, 100)); // call getRecipes 2 times
        assertEquals(600, coffeeMakerMock.makeCoffee(2, 600)); // call getRecipes 2 times
        verify(dummyRecipeBook, times(6)).getRecipes();
    }

    /**
     * Test the coffeemaker that have enough ingredients for the user order.
     * Then the user can get the beverage if we have enough ingredient.
     */
    @Test
    public void testEnoughIngredientsForBeverages() {
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        when(recipe.getAmtSugar()).thenReturn(5);
        when(recipe.getAmtMilk()).thenReturn(3);
        when(recipe.getPrice()).thenReturn(200);
        assertEquals(0, coffeeMakerMock.makeCoffee(0,200)); // call getRecipes 4 times
        assertEquals(100, coffeeMakerMock.makeCoffee(0,300)); // call getRecipes 4 times
        assertEquals(40, coffeeMakerMock.makeCoffee(0,240)); // call getRecipes 4 times
        verify(dummyRecipeBook, times(12)).getRecipes();
    }

    /**
     * Test that we can not order the non-existing recipe.
     */
    @Test
    public void testNullRecipe() {
        Recipe [] r = new Recipe[] {null};
        when(dummyRecipeBook.getRecipes()).thenReturn(r);
        assertEquals(20, coffeeMakerMock.makeCoffee(0,20)); // call getRecipes 1 times
        assertEquals(100, coffeeMakerMock.makeCoffee(0,100)); // call getRecipes 1 times
        assertEquals(50, coffeeMakerMock.makeCoffee(0,50)); // call getRecipes 1 times
        verify(dummyRecipeBook, times(3)).getRecipes();
    }
    /**
     * Test the coffeemaker that have enough ingredients for the user order.
     * Then the user can not get the beverage if we don't have enough ingredient.
     */
    @Test
    public void testNotEnoughIngredientsForBeverages() {
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        when(recipe.getAmtSugar()).thenReturn(3);
        when(recipe.getAmtMilk()).thenReturn(7);
        when(recipe.getPrice()).thenReturn(200);
        assertEquals(0, coffeeMakerMock.makeCoffee(0,200)); // call getRecipes 4 times
        assertEquals(100, coffeeMakerMock.makeCoffee(0,300)); // call getRecipes 4 times
        assertEquals(240, coffeeMakerMock.makeCoffee(0,240)); // call getRecipes 3 times
        assertEquals(200, coffeeMakerMock.makeCoffee(0,200)); // call getRecipes 3 times
        verify(dummyRecipeBook, times(14)).getRecipes();
    }

    /**
     * Test that the amount sugar subtract the use of ingredients correctly.
     */
    @Test
    public void testCalculateAmtSugar() {
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        when(recipe.getPrice()).thenReturn(0);
        when(recipe.getAmtSugar()).thenReturn(10);
        coffeeMakerMock.makeCoffee(0,0);
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 5\nChocolate: 15\n", coffeeMakerMock.checkInventory());
    }

    /**
     * Test that the amount milk subtract the use of ingredients correctly.
     */
    @Test
    public void testCalculateAmtMilk() {
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        when(recipe.getPrice()).thenReturn(0);
        when(recipe.getAmtMilk()).thenReturn(10);
        coffeeMakerMock.makeCoffee(0,0);
        assertEquals("Coffee: 15\nMilk: 5\nSugar: 15\nChocolate: 15\n", coffeeMakerMock.checkInventory());
    }

    /**
     * Test that the amount coffee subtract the use of ingredients correctly.
     */
    @Test
    public void testCalculateAmtCoffee() {
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        when(recipe.getPrice()).thenReturn(0);
        when(recipe.getAmtCoffee()).thenReturn(10);
        coffeeMakerMock.makeCoffee(0,0);
        assertEquals("Coffee: 5\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMakerMock.checkInventory());
    }

    /**
     * Test that the amount chocolate subtract the use of ingredients correctly.
     */
    @Test
    public void testCalculateAmtChocolate() {
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        when(recipe.getPrice()).thenReturn(0);
        when(recipe.getAmtChocolate()).thenReturn(10);
        coffeeMakerMock.makeCoffee(0,0);
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 5\n", coffeeMakerMock.checkInventory());
    }

    /**
     * Verify that the method has called correctly.
     * When we call coffeemaker with normal execution.
     */
    @Test
    public void testValidMakeCoffeeCallMethodCorrectly() {
        Recipe  recipe2 = mock(Recipe.class);
        Recipe  recipe3 = mock(Recipe.class);
        recipes = new Recipe[] {recipe, recipe2, recipe3};
        when(recipe.getPrice()).thenReturn(100);
        when(recipe2.getPrice()).thenReturn(100);
        when(recipe3.getPrice()).thenReturn(100);
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        assertEquals(0, coffeeMakerMock.makeCoffee(1, 100));
        assertEquals(0, coffeeMakerMock.makeCoffee(0, 100));
        assertEquals(0, coffeeMakerMock.makeCoffee(2, 100));
        assertEquals(50, coffeeMakerMock.makeCoffee(0, 50));

        // each makecoffee method it will call  getAmtSugar(), getAmtChocolate(), getAmtCoffee(),
        // getAmtMilk(), and getPrice() 2 times
        verify(recipe, times(2)).getAmtSugar();
        verify(recipe, times(2)).getAmtChocolate();
        verify(recipe, times(2)).getAmtCoffee();
        verify(recipe, times(3)).getPrice();

        verify(recipe2, times(2)).getAmtSugar();
        verify(recipe2, times(2)).getAmtChocolate();
        verify(recipe2, times(2)).getAmtCoffee();
        verify(recipe2, times(2)).getPrice();

        verify(recipe3, times(2)).getAmtSugar();
        verify(recipe3, times(2)).getAmtChocolate();
        verify(recipe3, times(2)).getAmtCoffee();
        verify(recipe3, times(2)).getPrice();
    }
}
