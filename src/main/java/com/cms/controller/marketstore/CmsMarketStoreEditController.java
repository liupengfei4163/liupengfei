package com.cms.controller.marketstore;

import javax.validation.Valid;

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
@RequestMapping(value = "/marketstore/marketstoreedit")
public class CmsMarketStoreEditController extends ControllerBase {

	@Autowired
	MarketStoreService service;

	/**
	 * 販売店画面初期化
	 */
	@RequestMapping(method = RequestMethod.GET)
	private String init(Model model, @ModelAttribute("selectedStoreId") String selectedStoreId) {

		MarketStoreForm form = new MarketStoreForm();
		form.setStoreId(selectedStoreId);

		
		MarketStoreForm initForm = service.editInit(form);
		
		model.addAttribute("form", initForm);
		
		return UrlConst.marketstoreedit;
	}
	
	/**
	 * 更新ボタンを押下する
	 */
	@RequestMapping(params = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("form") @Valid MarketStoreForm form, BindingResult result, Model model) {

		
		if (result.hasErrors()) {
			model.addAttribute("form", form);
			return null;
		}

		service.update(form);

		return UrlConst.redirect_marketstorelist;
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