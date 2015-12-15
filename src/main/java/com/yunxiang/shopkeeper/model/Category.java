package com.yunxiang.shopkeeper.model;

public class Category {
     
	private String name;
	private long categoryId;

	public Category() {}

	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
