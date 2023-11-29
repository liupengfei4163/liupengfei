package com.sample.practice3;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.database.IDatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.base.UnitTestBase;
import com.cms.entity.invoice.CmsInvoiceBean;
import com.cms.service.practice3.CalculateInvoiceService;


@SuppressWarnings("rawtypes")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculateInvoiceServiceTest_MOCK {

	private static IDatabaseConnection conn;

	private final static String path = "src\\test\\data\\practice3\\";
	/* テストデータを登録する */
	@BeforeAll
	public static void init() throws Exception {
		conn = UnitTestBase.connect();
    	UnitTestBase.clearData(path,conn);
		// CSVデータを取り込む
		UnitTestBase.initData(path,conn);
	}

	@AfterAll
	public static void closeConnection() throws Exception {
		// 計算後のデータが正常に登録されるかを確認するため、クリアされない
		// CSVデータを取り込む
		UnitTestBase.closeConnection(conn);
	}

	@Autowired
	CalculateInvoiceService service;

	/**
	 * ケース①：社員IDが設定されない場合
	 */
	@Test
	public void testNg01() {
		try {
			List<CmsInvoiceBean> retList = service.calculateWorkHourAndSalary("", "202203");

		} catch (Exception e) {
			// 異常メッセージ比較
			assertEquals("社員IDをご設定ください", e.getMessage());
		}
	}

	/**
	 * ケース②：社員IDが社員マスタに存在しない場合
	 */
	@Test
	public void testNg02() {
		try {
			List<CmsInvoiceBean> retList = service.calculateWorkHourAndSalary("EM909", "202203");

		} catch (Exception e) {
			assertEquals("社員はありません。", e.getMessage());
		}
	}

	/**
	 * ケース③：異常（社員IDと勤務年月のデータが存在しない場合）
	 */
	@Test
	public void testNg03() {
		try {
			List<CmsInvoiceBean> retList = service.calculateWorkHourAndSalary("1", "202401");

		} catch (Exception e) {
			assertEquals("該当社員の勤怠情報はありません。", e.getMessage());
		}
	}

	/**
	 * ケース④：異常（同様な社員IDと勤務年月のデータを2回実施する場合）
	 */
	@Test
	public void testNg04() {
		try {
			List<CmsInvoiceBean> retList1 = service.calculateWorkHourAndSalary("1", "202201");
			List<CmsInvoiceBean> retList2 = service.calculateWorkHourAndSalary("1", "202201");

		} catch (Exception e) {
			assertEquals("指定条件の請求書がすでに登録されました。", e.getMessage());
		}
	}

	/**
	 * ケース⑥：データ組み合わせシート：パターン①
	 */
	@Test
	public void testNormal01() {
		try {
			List<CmsInvoiceBean> retList = service.calculateWorkHourAndSalary("1", "202201");

			// 予想値の構築
			List<CmsInvoiceBean> expectedList = new ArrayList<CmsInvoiceBean>();
			expectedList.add(createInvoiceBean("1", "202201", 9, 9, new BigDecimal(90000.0), new BigDecimal(0)));

			// 件数比較
			assertEquals(retList.size(), expectedList.size());
			// レコード比較
			compareListBean(retList, expectedList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * ケース⑦：データ組み合わせシート：パターン①
	 */
	@Test
	public void testNormal02() {
		try {
			List<CmsInvoiceBean> retList = service.calculateWorkHourAndSalary("2", "202202");

			// 予想値の構築
			List<CmsInvoiceBean> expectedList = new ArrayList<CmsInvoiceBean>();
			expectedList.add(createInvoiceBean("2", "202202", 26, 27, new BigDecimal(260000.0), new BigDecimal(10000)));

			// 件数比較
			assertEquals(retList.size(), expectedList.size());

			// レコード比較
			compareListBean(retList, expectedList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * ケース⑧：データ組み合わせシート：パターン①
	 */
	@Test
	public void testNormal03() {
		try {
			List<CmsInvoiceBean> retList = service.calculateWorkHourAndSalary("3", "");

			// 予想値の構築
			List<CmsInvoiceBean> expectedList = new ArrayList<CmsInvoiceBean>();
			expectedList
					.add(createInvoiceBean("3", "202203", 8, 9, new BigDecimal(80000.0), new BigDecimal(10000)));
			expectedList
					.add(createInvoiceBean("3", "202204", 8, 9, new BigDecimal(80000.0), new BigDecimal(10000)));

			// 件数比較
			assertEquals(retList.size(), expectedList.size());

			// レコード比較
			compareListBean(retList, expectedList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * ケース⑨：データ組み合わせシート：パターン①
	 */
	@Test
	public void testNormal04() {
		try {
			List<CmsInvoiceBean> retList = service.calculateWorkHourAndSalary("4", "");

			// 予想値の構築
			List<CmsInvoiceBean> expectedList = new ArrayList<CmsInvoiceBean>();
			expectedList.add(
					createInvoiceBean("4", "202205", 28, 27, new BigDecimal(280000.0), new BigDecimal(-10000)));
			expectedList.add(
					createInvoiceBean("4", "202206", 28, 27, new BigDecimal(280000.0), new BigDecimal(-10000)));

			// 件数比較
			assertEquals(retList.size(), expectedList.size());

			// レコード比較
			compareListBean(retList, expectedList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 請求書レコードを新規する
	 * 
	 * @param employeeId
	 * @param month
	 * @param standardBusinessHours
	 * @param realWorkHous
	 * @param salary
	 * @param disabledGeneration
	 */
	private CmsInvoiceBean createInvoiceBean(String employeeId, String month, Integer standardBusinessHours,
			Integer realWorkHous, BigDecimal salary, BigDecimal disabledGeneration) {
		CmsInvoiceBean bean = new CmsInvoiceBean();
		bean.setEmployeeId(employeeId);
		bean.setWorkMonth(month);
		bean.setStandardBusinessHours(standardBusinessHours);
		bean.setRealWorkHous(realWorkHous);
		bean.setSalary(salary);
		bean.setDisabledGeneration(disabledGeneration);
		return bean;
	}

	/**
	 * 登録済の請求書レコードと予想値を比較する
	 * 
	 * @param recs         登録済請求書
	 * @param expectedList 予想値
	 */
	private void compareListBean(List<CmsInvoiceBean> recs, List<CmsInvoiceBean> expectedList) {

		int index = 0;
		for (CmsInvoiceBean rec : recs) {
			CmsInvoiceBean expectedBean = expectedList.get(index);
			assertEquals(rec.getEmployeeId(), expectedBean.getEmployeeId());
			assertEquals(rec.getWorkMonth(), expectedBean.getWorkMonth());
			assertEquals(rec.getStandardBusinessHours(), expectedBean.getStandardBusinessHours());
			assertEquals(rec.getRealWorkHous(), expectedBean.getRealWorkHous());
			assertEquals(rec.getSalary().toBigInteger(), expectedBean.getSalary().toBigInteger());
			assertEquals(rec.getDisabledGeneration().toBigInteger(),
					expectedBean.getDisabledGeneration().toBigInteger());
			index++;
		}

	}
}