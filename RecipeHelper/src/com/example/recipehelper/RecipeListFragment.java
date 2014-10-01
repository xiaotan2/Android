package com.example.recipehelper;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class RecipeListFragment extends ListFragment {

	private ArrayList<Recipe> mRecipes;
	private boolean mSubtitleVisible;
	private static final String TAG = "RecipeListFragment";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true); //let fragmentmanager know there is option menu
		
		getActivity().setTitle(R.string.recipe_title);
		mRecipes = RecipeKitchen.get(getActivity()).getRecipes();
		
		RecipeAdapter adapter = new RecipeAdapter(mRecipes);
		setListAdapter(adapter);
		
		setRetainInstance(true);
		mSubtitleVisible = false;
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, parent, savedInstanceState);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if(mSubtitleVisible) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
		}
		return v;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Recipe c = ((RecipeAdapter)getListAdapter()).getItem(position);
		
		Intent i = new Intent(getActivity(), RecipePagerActivity.class);
		i.putExtra(RecipeFragment.EXTRA_RECIPE_ID, c.getId());
		startActivity(i);
	}
	
	private class RecipeAdapter extends ArrayAdapter<Recipe> {
		
		public RecipeAdapter(ArrayList<Recipe> recipes) {
			super(getActivity(), 0, recipes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_recipe, null);
			}
			
			Recipe c = getItem(position);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.recipe_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			
			TextView dateTextView = (TextView)convertView.findViewById(R.id.recipe_list_item_dateTextView);
			dateTextView.setText(c.getData().toString());
			
			CheckBox doneCheckBox = (CheckBox)convertView.findViewById(R.id.recipe_list_item_doneCheckBox);
			doneCheckBox.setChecked(c.isDone());
			
			return convertView;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((RecipeAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_recipe_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if(mSubtitleVisible && showSubtitle != null)
			showSubtitle.setTitle(R.string.hide_subtitle);
	}
	
	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.menu_item_new_recipe:
				Recipe recipe = new Recipe();
				RecipeKitchen.get(getActivity()).addRecipe(recipe);
				Intent i = new Intent(getActivity(), RecipePagerActivity.class);
				i.putExtra(RecipeFragment.EXTRA_RECIPE_ID, recipe.getId());
				startActivityForResult(i,0);
				return true;
			case R.id.menu_item_show_subtitle:
				if(getActivity().getActionBar().getSubtitle() == null) {
					getActivity().getActionBar().setSubtitle(R.string.subtitle);
					item.setTitle(R.string.hide_subtitle);
					mSubtitleVisible = true;
				}
				else {
					getActivity().getActionBar().setSubtitle(null);
					item.setTitle(R.string.show_subtitle);
					mSubtitleVisible = false;
				}
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
