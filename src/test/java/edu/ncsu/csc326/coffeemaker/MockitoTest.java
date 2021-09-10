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
        assertEquals(50, coffeeMakerMock.makeCoffee(0, 50));
        assertEquals(0, coffeeMakerMock.makeCoffee(0, 100));
        assertEquals(900, coffeeMakerMock.makeCoffee(0, 1000));
        assertEquals(100, coffeeMakerMock.makeCoffee(0, 200));
        assertEquals(25, coffeeMakerMock.makeCoffee(0, 125));
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
        assertEquals(0, coffeeMakerMock.makeCoffee(1, 50));
        assertEquals(0, coffeeMakerMock.makeCoffee(0, 100));
        assertEquals(990, coffeeMakerMock.makeCoffee(2, 1000));
        assertEquals(100, coffeeMakerMock.makeCoffee(1, 150));
        assertEquals(0, coffeeMakerMock.makeCoffee(2, 10));
    }

    /**
     * Test that the user have enough payment for their beverage.
     */
    @Test
    public void testTheUserPayWithNotEnoughMoney() {
        when(recipe.getPrice()).thenReturn(1000);
        when(coffeeMakerMock.getRecipes()).thenReturn(recipes);
        assertEquals(50, coffeeMakerMock.makeCoffee(0, 50));
        assertEquals(100, coffeeMakerMock.makeCoffee(0, 100));
        assertEquals(300, coffeeMakerMock.makeCoffee(0, 300));
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
        assertEquals(50, coffeeMakerMock.makeCoffee(1, 50));
        assertEquals(100, coffeeMakerMock.makeCoffee(0, 100));
        assertEquals(600, coffeeMakerMock.makeCoffee(2, 600));
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
        assertEquals(0, coffeeMakerMock.makeCoffee(0,200));
        assertEquals(100, coffeeMakerMock.makeCoffee(0,300));
        assertEquals(40, coffeeMakerMock.makeCoffee(0,240));
    }

    /**
     * Test that we can not order the non-existing recipe.
     */
    @Test
    public void testNullRecipe() {
        Recipe [] r = new Recipe[] {null};
        when(dummyRecipeBook.getRecipes()).thenReturn(r);
        assertEquals(20, coffeeMakerMock.makeCoffee(0,20));
        assertEquals(100, coffeeMakerMock.makeCoffee(0,100));
        assertEquals(50, coffeeMakerMock.makeCoffee(0,50));
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
        assertEquals(0, coffeeMakerMock.makeCoffee(0,200));
        assertEquals(100, coffeeMakerMock.makeCoffee(0,300));
        assertEquals(240, coffeeMakerMock.makeCoffee(0,240));
        assertEquals(200, coffeeMakerMock.makeCoffee(0,200));
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
