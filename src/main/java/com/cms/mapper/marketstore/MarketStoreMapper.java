package com.cms.mapper.marketstore;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cms.entity.marketstore.CmsMarketStoreBean;

/**
 * 販売店情報 Mapper
 */
@Mapper
public interface MarketStoreMapper {
	/**
	 * ユーザー情報検索
	 * 
	 * @param 販売店 検索用リクエストデータ
	 * @return 販売店情報
	 */
	List<CmsMarketStoreBean> select(CmsMarketStoreBean bean);

	/**
	 * 最大IDの取得
	 * 
	 * @return 最大の社員ID
	 */
	String selectMaxId();

	/**
	 * 販売店情報登録
	 * 
	 * @param 販売店 検索用リクエストデータ
	 * @return 更新件数
	 */
	int insert(CmsMarketStoreBean bean);
	
	/**
	 * 販売店情報更新
	 * 
	 * @param user 検索用リクエストデータ
	 * @return 更新件数
	 */
	void update(CmsMarketStoreBean updateBean);
	
	/**
	 * 販売店情報参照
	 * 
	 * @param user 検索用リクエストデータ
	 * @return 参照件数
	 */
	void read(CmsMarketStoreBean readBean);

	/**
	 * 販売店レコードを削除する
	 * 
	 * @param form 販売店フォーム
	 * @return 
	 */
	void delete(CmsMarketStoreBean deleteBean);

	/**
	 * 販売店レコードを全削除SQL
	 * 
	 * @param  販売ID
	 * @return 
	 */
	void deleteAll(String[] delIds);
}