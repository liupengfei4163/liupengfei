package com.cms.controller.marketstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cms.common.UrlConst;
import com.cms.controller.base.ControllerBase;
import com.cms.form.marketstore.MarketStoreForm;
import com.cms.service.marketstore.MarketStoreService;

/**
 * ログイン コントローラー
 */
@Controller
@RequestMapping(value = "/marketstore/marketstoreview")
public class CmsMarketStroeViewController extends ControllerBase {

	@Autowired
	MarketStoreService service;

	/**
	 * ユーザ画面初期化
	 */
	@RequestMapping(method = RequestMethod.GET)
	private String init(Model model, @ModelAttribute("selectedStoreId") String selectedStoreId) {

		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId(selectedStoreId);

		
		MarketStoreForm initForm = service.read(form);
		
		model.addAttribute("form", initForm);

		return UrlConst.marketstoreview;
	}
	
	/**
	 * 「戻る」ボタンを押下する
	 */
	@RequestMapping(params = "gotoMarketStoreList", method = RequestMethod.POST)
	public String gotoMarketStoreList(@ModelAttribute("form") MarketStoreForm form, BindingResult result,
			Model model) {

		return UrlConst.redirect_marketstorelist;
	}
}