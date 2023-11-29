package com.cms.controller.marketstore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cms.common.UrlConst;
import com.cms.controller.base.ControllerBase;
import com.cms.form.marketstore.MarketStoreListForm;
import com.cms.mapper.common.CommonMapper;
import com.cms.service.marketstore.MarketStoreService;
import com.exception.BusinessException;
import com.utils.ServiceUtils;

/**
 * ログイン コントローラー
 */

@Controller
@RequestMapping(value = "/marketstore/marketstorelist")
public class CmsMarketStoreListController extends ControllerBase {

	@Autowired
	MarketStoreService service;

	@Autowired
	ServiceUtils serviceUtils;

	@Autowired
	CommonMapper commonMapper;

	/**
	 * 販売店画面初期化
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String init(Model model, HttpServletRequest request) {

		MarketStoreListForm form = new MarketStoreListForm();
		
		model.addAttribute("form", form);
		

		return UrlConst.marketstorelist;
	}
	/**
	 * 画面再検索を実施する
	 * 
	 * @param form 一覧画面フォーム
	 * @param model モデル
	 */
	private void searchScreenList(MarketStoreListForm form, Model model) {

		try {

			// 検索実施
			MarketStoreListForm responseForm = service.select(form);
			model.addAttribute("form", responseForm);
			
		} catch (Exception se) {
			System.out.println(se.getMessage());
		}
	}
	/**
	 * メニュー画面初期化
	 */
	@RequestMapping(params = "select", method = RequestMethod.POST)
	public String select(@ModelAttribute("form") MarketStoreListForm form, Model model, HttpServletRequest request) {
		
		/**-----sessionからログインの社員IDを取得する-----*/
		HttpSession session = request.getSession();
		//社員IDを設定する
		String storeId = String.valueOf(session.getAttribute("storeId"));
		form.setSessionStoreId(storeId);
		//職種を設定する
//		String jobType = String.valueOf(session.getAttribute("jobType"));
//		form.setSessionJobType(jobType);
		
		// 検索実施
		MarketStoreListForm searchResultform = service.select(form);
		
		try {
			service.select(form);


		} catch (BusinessException be) {

			// 業務エラーを画面に表示する
			form.setErrorMessage(be.getMessage());
			
		}
		model.addAttribute("form", searchResultform);
		return UrlConst.marketstorelist;
	}
	
	/**
	 * 新規ボタン
	 */
	@RequestMapping(params = "addstore", method = RequestMethod.POST)
	public String add(Model model) {

		//return "redirect:/marketstore/marketstoreadd";
		return UrlConst.redirect_marketstoreadd;
	}
	
	/**
	 * 参照ボタン
	 */
	@RequestMapping(params = "read", method = RequestMethod.POST)
	public String read(@ModelAttribute("form") MarketStoreListForm form, 
			Model model, RedirectAttributes redirectAttributes, @RequestParam String read) {

		String[] param = read.split("_");

		
		// 更新画面へ渡す引数：販売店ＩＤ
		redirectAttributes.addAttribute("selectedStoreId", param[0]);
		return UrlConst.redirect_marketstoreview;

	}
	
	/**
	 * 更新ボタン
	 */
	@RequestMapping(params = "update", method = RequestMethod.POST)
	public String update( RedirectAttributes redirectAttributes, @RequestParam String update) {
        
		String[] param = update.split("_");

		// 更新画面へ渡す引数：社員ＩＤ
		redirectAttributes.addAttribute("selectedStoreId", param[0]);
		return UrlConst.redirect_marketstoreedit;

	}
	/**
	 * 削除ボタン
	 */
	@RequestMapping(params = "delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute("form") MarketStoreListForm form, 
			Model model, @RequestParam String delete) {
		
			form.setStoreId(delete);
		 	service.delete(form);
		 	searchScreenList(form, model);
		return UrlConst.marketstorelist;
	}
	
	/**
	 * 全削除
	 */
	@RequestMapping(params = "deleteAll", method = RequestMethod.POST)
	public String deleteAll(@ModelAttribute("form") MarketStoreListForm form, Model model) {

		//全削除サービスを呼び出す
		service.deleteAll(form);

		// 画面データ初期化
		searchScreenList(form, model);
		
		return UrlConst.marketstorelist;

	}
	// ----------PopUp画面（社員選択画面）・検索ボタン start----------

//	@PostMapping(value = "/searchStore")
//	@ResponseBody
//	public MarketStoreListForm searchStore(@RequestParam Map<String, Object> params) {
//
//		MarketStoreListForm bean = new MarketStoreListForm();
//		bean.setStoreName(params.get("storeName").toString());
//		MarketStoreListForm result = service.select(bean);
//
//		return result;
//	}
	// ----------PopUp画面（社員選択画面）・検索ボタン end ----------
}