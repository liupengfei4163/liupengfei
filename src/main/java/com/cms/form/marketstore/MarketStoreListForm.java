package com.cms.form.marketstore;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.cms.entity.marketstore.CmsMarketStoreBean;
import com.cms.form.BaseForm;

import lombok.Getter;
import lombok.Setter;

/**
 * 販売店情報 検索用リクエストデータ
 */
@Getter
@Setter
public class MarketStoreListForm extends BaseForm {

	/* 検索結果 */
	private List<CmsMarketStoreBean> results;

	private String name;
	
	private String storeId;

	@NotEmpty
	private String storeName;

	@NotEmpty
	private String address;
	
	//選択対象(ID+最終更新日)
	private String deletedItemIds;
	
	//ボタンフラグ（削除、編集ボタン：True；参照：False）
	private boolean btnFlg;
	
	//----------Session----------
	//社員ID(session引数保存)
	private String sessionStoreId;
	//職種(session引数保存)
	private String sessionJobType;

}