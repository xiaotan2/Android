package com.example.recipehelper;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class RecipeKitchen {

	private static RecipeKitchen sRecipeKitchen;
	private Context mAppContext;
	
	private ArrayList<Recipe> mRecipes;
	
	private RecipeKitchen(Context appContext) {
		mAppContext = appContext;
		mRecipes = new ArrayList<Recipe>();
	}
	
	public static RecipeKitchen get(Context c) {
		if(sRecipeKitchen == null) {
			sRecipeKitchen = new RecipeKitchen(c.getApplicationContext());
		}
		return sRecipeKitchen;
	}

	public ArrayList<Recipe> getRecipes() {
		return mRecipes;
	}
	
	public Recipe getRecipe(UUID id) {
		for(Recipe c : mRecipes) {
			if(c.getId().equals(id))
				return c;
		}
		return null;
	}
	
	public void addRecipe(Recipe r) {
		mRecipes.add(r);
	}
}
