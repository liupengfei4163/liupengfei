package com.cms.form.marketstore;

import javax.validation.constraints.NotEmpty;

import com.cms.form.BaseForm;

import lombok.Getter;
import lombok.Setter;

/**
 * 販売店情報 検索用リクエストデータ
 */
@Getter
@Setter
public class MarketStoreForm extends BaseForm {


	private String name;
	@NotEmpty
	private String storeId;
	@NotEmpty
	private String storeName;
	@NotEmpty
	private String address;
	@NotEmpty
	private String phone;	
	@NotEmpty
	private String startDay;	
	@NotEmpty
	private String finishDay;	

	
}