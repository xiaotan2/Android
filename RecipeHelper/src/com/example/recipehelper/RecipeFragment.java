package com.example.recipehelper;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class RecipeFragment extends Fragment {

	private Recipe mRecipe;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mDoneCheckBox;
	
	public static final String EXTRA_RECIPE_ID = "com.bignerdranch.android.RecipeHelper.recipe_id";
	private static final String DIALOG_DATE = "date";
	private static final int REQUEST_DATE = 0;
	
	public static RecipeFragment newInstance(UUID recipeId){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_RECIPE_ID, recipeId);
		
		RecipeFragment fragment = new RecipeFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//UUID recipeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
		UUID recipeId = (UUID)getArguments().getSerializable(EXTRA_RECIPE_ID);
		mRecipe = RecipeKitchen.get(getActivity()).getRecipe(recipeId);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != Activity.RESULT_OK) return;
		if(requestCode == REQUEST_DATE) {
			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mRecipe.setData(date);
			updateDate();
		}
	}
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.recipe_fragment, parent, false);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if(NavUtils.getParentActivityName(getActivity()) != null)
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		mTitleField = (EditText)v.findViewById(R.id.recipe_title);
		mTitleField.setText(mRecipe.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(
					CharSequence c, int start, int before, int count){
				mRecipe.setTitle(c.toString());
			}
			
			public void beforeTextChanged(
					CharSequence c, int start, int count, int after){
				
			}
			
			public void afterTextChanged(Editable c) {
				
			}
		});
		
		mDateButton = (Button)v.findViewById(R.id.recipe_data);
		updateDate();
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment.newInstance(mRecipe.getData());
				dialog.setTargetFragment(RecipeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});
		
		mDoneCheckBox = (CheckBox)v.findViewById(R.id.recipe_done);
		mDoneCheckBox.setChecked(mRecipe.isDone());
		mDoneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				mRecipe.setDone(isChecked);
			}
		});
		
		return v;
	}
	
	private void updateDate() {
		mDateButton.setText(mRecipe.getData().toString());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				if(NavUtils.getParentActivityName(getActivity()) != null) {
					NavUtils.navigateUpFromSameTask(getActivity());
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
