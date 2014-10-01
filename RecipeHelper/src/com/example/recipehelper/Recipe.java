package com.example.recipehelper;

import java.util.Date;
import java.util.UUID;

import android.provider.ContactsContract.Data;

public class Recipe {

	private UUID mId;
	private String mTitle;
	private Date mData;
	private boolean mDone;
	
	public Recipe() {
		mId = UUID.randomUUID();
		mData = new Date();
	}
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String title) {
		mTitle = title;
	}
	public UUID getId() {
		return mId;
	}
	public Date getData() {
		return mData;
	}
	public void setData(Date data) {
		mData = data;
	}
	public boolean isDone() {
		return mDone;
	}
	public void setDone(boolean done) {
		mDone = done;
	}
	@Override
	public String toString() {
		return mTitle;
	}
}
