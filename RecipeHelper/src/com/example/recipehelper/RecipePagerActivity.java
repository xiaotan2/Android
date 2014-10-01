package com.example.recipehelper;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class RecipePagerActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private ArrayList<Recipe> mRecipes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mRecipes = RecipeKitchen.get(this).getRecipes();
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return mRecipes.size();
			}
			
			@Override
			public Fragment getItem(int pos) {
				Recipe recipe = mRecipes.get(pos);
				return RecipeFragment.newInstance(recipe.getId());
			}
		});
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				Recipe recipe = mRecipes.get(arg0);
				if(recipe.getTitle() != null)
					setTitle(recipe.getTitle());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
		
		UUID recipeId = (UUID)getIntent().getSerializableExtra(RecipeFragment.EXTRA_RECIPE_ID);
		for(int i = 0; i < mRecipes.size(); i++) {
			if(mRecipes.get(i).getId().equals(recipeId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
				
		}
		
	}
}
