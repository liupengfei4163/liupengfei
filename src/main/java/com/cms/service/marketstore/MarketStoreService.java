package com.cms.service.marketstore;

import javax.validation.Valid;

import com.cms.form.marketstore.MarketStoreForm;
import com.cms.form.marketstore.MarketStoreListForm;

public interface MarketStoreService {

	/**
	 * 一覧画面
	 */	
	//一覧画面検索
	public MarketStoreListForm select(MarketStoreListForm form);
	
	/**
	 * 販売店登録画面
	 */	
	public  void insert(MarketStoreForm form);

	/**
	 * 編集画面
	 */	

	public MarketStoreForm editInit(MarketStoreForm form);

	/**
	 * 更新画面
	 */	
	public void update(@Valid MarketStoreForm form);
	
	/**
	 * 参照画面
	 * @return 
	 */	
	public MarketStoreForm read(@Valid MarketStoreForm form);
	
	/**
	 * deleteボタン
	 * @return 
	 */	
	public MarketStoreListForm delete(MarketStoreListForm form);
	
	/**
	 * deleteAllボタン
	 * @return
	 */	
	public MarketStoreListForm deleteAll(MarketStoreListForm form);


	
}
