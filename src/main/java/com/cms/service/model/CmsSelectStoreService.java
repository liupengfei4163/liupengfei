package com.cms.service.model;

import java.util.List;

import com.cms.entity.marketstore.CmsMarketStoreBean;
/**
 * 社員選択画面 Service
 */
public interface CmsSelectStoreService {

	/**
	 * 社員選択画面_検索処理
	 * 
	 * @param bean 社員Bean
	 * @return List<CmsStoreBean> 検索結果
	 */	
	public List<CmsMarketStoreBean> select(CmsMarketStoreBean bean);
	
}
