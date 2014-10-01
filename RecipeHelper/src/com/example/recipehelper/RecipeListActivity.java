package com.example.recipehelper;

import android.support.v4.app.Fragment;

public class RecipeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new RecipeListFragment();
	}

}
