package com.yunxiang.shopkeeper.service.interFace;

import com.yunxiang.shopkeeper.model.Category;

import java.util.List;


public interface ICategoryService {

	public void addCategory(Category category);
	public void addCategoryList(List<Category> categoryList);

	public List<Category> getAll();
	
	public Category getCategory(int id);
	
	public void deleteAll();
	public void delete(Category category);
	public void modify(Category category);
}
