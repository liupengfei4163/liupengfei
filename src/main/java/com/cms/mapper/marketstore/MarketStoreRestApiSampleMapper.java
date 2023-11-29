package com.cms.mapper.marketstore;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cms.entity.marketstore.CmsMarketStoreBean;

/**
 * 社員情報 Mapper（RestApi）
 */
@Mapper
public interface MarketStoreRestApiSampleMapper {
	
	/**
	 * 販売店情報検索
	 * 
	 * @param bean 検索条件
	 */
	List<CmsMarketStoreBean> select(CmsMarketStoreBean bean);
	
	/**
	 * 販売店情報保存
	 * 
	 * @param bean 登録項目
	 * @return CmsEmployeeBean 登録後の社員情報
	 */
	CmsMarketStoreBean save(CmsMarketStoreBean bean);
}