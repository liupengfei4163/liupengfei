package com.cms.service.marketstore;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cms.entity.marketstore.CmsMarketStoreBean;
import com.cms.form.marketstore.MarketStoreForm;
import com.cms.form.marketstore.MarketStoreListForm;
import com.cms.mapper.marketstore.MarketStoreMapper;
import com.exception.BusinessException;

import io.micrometer.core.instrument.util.StringUtils;

/**
 * 販売店情報 Service
 */
@Service
public class MarketStoreServiceImpl implements MarketStoreService {

	/****** Mapper ******/
	@Autowired
	MarketStoreMapper mapper;


	/**
	 * 販売店情報検索 @param userSearchRequest リクエストデータ
	 * 
	 * @return 検索結果
	 */
	public MarketStoreListForm select(MarketStoreListForm form) {
		// TODO 自動生成されたメソッド・スタブ
		CmsMarketStoreBean input = new CmsMarketStoreBean();
		if(StringUtils.isNotEmpty(form.getAddress())
			||StringUtils.isNotEmpty(form.getName())
			||StringUtils.isNotEmpty(form.getName())) {
			input.setName(form.getName());
			input.setAddress(form.getAddress());
			input.setStoreName(form.getStoreName());
		}
		/*
		 * input.setStoreName(form.getStoreName()); input.setAddress(form.getAddress());
		 */
		List<CmsMarketStoreBean> results = mapper.select(input);
		if (results == null || results.size() ==0) {
			throw new BusinessException("検索結果はありません。");
		}
		
		form.setResults(results);
		return form;
	}
	/**
	 * 販売店情報登録
	 * 
	 * @param form フォーム
	 */
	public void insert(MarketStoreForm form) {

		// ログイン情報を検索する
		CmsMarketStoreBean bean = new CmsMarketStoreBean();

		String maxId = mapper.selectMaxId();
		
		bean.setStoreId(String.valueOf(Integer.valueOf(maxId) + 1)); // 販売店ID
		bean.setStoreName(form.getStoreName()); // 名前
		bean.setAddress(form.getAddress()); // 住所
		bean.setPhone(form.getPhone()); // 携帯
		bean.setStartDay(form.getStartDay()); // 住営業開始年月日
		bean.setFinishDay(form.getFinishDay()); // 営業終了年月日				
		mapper.insert(bean);

//		return form;
	}
	/**
	 * 販売店 情報検索 @param userSearchRequest リクエストデータ
	 * 
	 * @return 検索結果
	 */
	public MarketStoreForm editInit(MarketStoreForm form) {

			// ログイン情報を検索する
		CmsMarketStoreBean sqlBean = new CmsMarketStoreBean();
			sqlBean.setStoreId(form.getStoreId());
			
			List<CmsMarketStoreBean> searchResults = mapper.select(sqlBean);
			if (!CollectionUtils.isEmpty(searchResults)) {
				CmsMarketStoreBean result = searchResults.get(0);

				form.setStoreId(result.getStoreId()); // 販売店ID
				form.setStoreName(result.getStoreName()); // 店名
				form.setAddress(result.getAddress()); // 住所
				form.setPhone(result.getPhone().toString());    // 生年月日
				form.setStartDay(result.getStartDay()); // 住所
				form.setFinishDay(result.getFinishDay()); // 住所			
			}

			return form;
		}
	/**
	 * 販売店情報を更新する
	 * 
	 * @param form 販売店フォーム
	 */
	public void update(MarketStoreForm form) {
		// 画面から販売店IDを取得する
		String storeId = form.getStoreId();
		// ログイン情報を検索する
		CmsMarketStoreBean bean = new CmsMarketStoreBean();
		bean.setStoreId(storeId);

		List<CmsMarketStoreBean> storeList = mapper.select(bean);
		CmsMarketStoreBean updateBean = storeList.get(0);

		// 8桁不足の場合、前に０を埋める
		updateBean.setStoreId(updateBean.getStoreId()); // 販売店ID
		updateBean.setStoreName(form.getStoreName()); // 名前
		updateBean.setAddress(form.getAddress()); // 住所
		updateBean.setPhone(form.getPhone()); // 携帯
		updateBean.setStartDay(form.getStartDay()); // 住営業開始年月日
		updateBean.setFinishDay(form.getFinishDay()); // 営業終了年月日
		
		mapper.update(updateBean);
	}
	/**
	 * 販売店情報を参照する
	 * 
	 * @param form 販売店フォーム
	 * @return 
	 */
	public MarketStoreForm read(MarketStoreForm form) {
		CmsMarketStoreBean bean = new CmsMarketStoreBean();
		bean.setStoreId(form.getStoreId());

		List<CmsMarketStoreBean> searchResults = mapper.select(bean);
		if (!CollectionUtils.isEmpty(searchResults)) {
			CmsMarketStoreBean result = searchResults.get(0);
		
		form.setStoreId(result.getStoreId()); // 販売店ID
		form.setStoreName(result.getStoreName()); // 名前
		form.setAddress(result.getAddress()); // 住所
		form.setPhone(result.getPhone()); // 携帯
		form.setStartDay(result.getStartDay()); // 住営業開始年月日
		form.setFinishDay(result.getFinishDay()); // 営業終了年月日
		
		}
		return form;
		}
	/**
	 * 販売店レコードを削除する
	 * 
	 * @param form 販売店フォーム
	 * @return 
	 */
	public MarketStoreListForm delete(MarketStoreListForm form) {
		CmsMarketStoreBean deleteBean = new CmsMarketStoreBean();
			deleteBean.setStoreId(form.getStoreId());
			
			mapper.delete(deleteBean);
		
		return form;
	}

	/**
	 * 選択した販売店レコードを全削除
	 * 
	 * @param form 販売店フォーム
	 * @return 
	 */
	public MarketStoreListForm deleteAll(MarketStoreListForm form) {
		
		String[] delIds = form.getDeletedItemIds().split(",");
		
		mapper.deleteAll(delIds);

		return form;
	}	
};