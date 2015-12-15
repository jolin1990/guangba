package com.yunxiang.shopkeeper.service.interFace;

import com.yunxiang.shopkeeper.model.Merchandise;

import java.util.List;


public interface IMerchandiseService {

	public void addMerchandise(Merchandise merchandise);
	public void addMerchandiseList(List<Merchandise> merchandiseList);

	public List<Merchandise> getAll();
	public List<Merchandise> getMerchandisesForCategory(int categoryId);
	
	public Merchandise getMerchandise(int id);
	
	public void deleteAll();
	public void modify(Merchandise merchandise);
	public List<Merchandise> getRecommendCategorys();
}
