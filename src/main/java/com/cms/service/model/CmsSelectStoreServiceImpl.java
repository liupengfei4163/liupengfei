package com.cms.service.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.entity.marketstore.CmsMarketStoreBean;
import com.cms.mapper.marketstore.MarketStoreMapper;

/**
 * 社員選択画面 Service
 */
@Service
public class CmsSelectStoreServiceImpl implements CmsSelectStoreService {

	/****** Mapper ******/
	@Autowired
	MarketStoreMapper businessMapper;

	/**
	 * 社員選択画面_検索処理
	 * 
	 * @param bean 社員Bean
	 * @return 検索結果
	 */
	public List<CmsMarketStoreBean> select(CmsMarketStoreBean bean) {
		
		List<CmsMarketStoreBean> retList = businessMapper.select(bean);
		
		return retList;
	}

}