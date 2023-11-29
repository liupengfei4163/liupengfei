package com.sample.practice1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cms.entity.employee.CmsEmployeeBean;
import com.cms.form.practice1.ReadDataBaseForm;
import com.cms.mapper.practice1.Practice1Mapper;
import com.cms.service.practice1.ReadDataBaseServiceImpl;

@SuppressWarnings("rawtypes")
@RunWith(SpringRunner.class)  
@SpringBootTest
public class ReadcsvServiceTest_Mock_Service 
{
	
	@InjectMocks
	private ReadDataBaseServiceImpl service;
     
    @Mock
    private Practice1Mapper mapper;
    
    /**
     * 正常終了
     * 
     * 検索件数：1件
     */
    @Test
    public void test_readDataFromMysql_01_OK() 
    {
    	
    	//検索結果Mock
    	List<CmsEmployeeBean> mockResults = new ArrayList<CmsEmployeeBean>();

    	mockResults.add(createBean("1"));
    	mockResults.add(createBean("2"));
    	mockResults.add(createBean("3"));
    	
    	//引数
    	CmsEmployeeBean paramBean = new CmsEmployeeBean();
    	
    	Mockito.when(this.mapper.select(paramBean)).thenReturn(mockResults);
    	
    	List<CmsEmployeeBean> beanList = null;
		try {
			beanList = service.readDataFromMysql(new ReadDataBaseForm());
			//件数比較
	    	assertEquals(3, beanList.size());
	    	
	    	int index = 0;
	    	for (CmsEmployeeBean bean : beanList) {
	    		//項目比較
	    		mockResults.get(index);
	    		assertEquals(bean.getEmployeeId(),mockResults.get(index).getEmployeeId());
	    		assertEquals(bean.getName(),mockResults.get(index).getName());
	     		assertEquals(bean.getAddress(),mockResults.get(index).getAddress());
	    		index ++;
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 正常終了
     * 
     * 検索件数：1件(検索結果と条件が一致する)
     */
    @Test
    public void test_readDataFromMysql_02_OK() 
    {
    	
    	//検索結果Mock
    	List<CmsEmployeeBean> mockResults = new ArrayList<CmsEmployeeBean>();

    	mockResults.add(createBean("1"));
    	
    	//引数
    	CmsEmployeeBean paramBean = new CmsEmployeeBean();
    	paramBean.setEmployeeId("1");
    	
    	Mockito.when(this.mapper.select(paramBean)).thenReturn(mockResults);
    	
    	List<CmsEmployeeBean> beanList = null;
		try {
			ReadDataBaseForm form = new ReadDataBaseForm();
			form.setEmployeeId("1");
			beanList = service.readDataFromMysql(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	assertEquals(1, beanList.size());
    	
    }

    /**
     * 正常終了
     * 
     * 検索件数：1件(※検索結果と条件が不一致です。)
     */
    @Test
    public void test_readDataFromMysql_03_OK() 
    {
    	
    	//検索結果Mock
    	List<CmsEmployeeBean> mockResults = new ArrayList<CmsEmployeeBean>();

    	mockResults.add(createBean("9"));
    	
    	//引数
    	CmsEmployeeBean paramBean = new CmsEmployeeBean();
    	paramBean.setEmployeeId("3");
    	
    	Mockito.when(this.mapper.select(paramBean)).thenReturn(mockResults);
    	
    	List<CmsEmployeeBean> beanList = null;
		try {
			
			ReadDataBaseForm form = new ReadDataBaseForm();
			form.setEmployeeId("3");
			beanList = service.readDataFromMysql(form);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	assertEquals(1, beanList.size());
    	
    }
    /**
     * 異常終了（検索結果がありません。）
     * 
     */
    @Test
    public void test_readDataFromMysql_01_NG() 
    {
    	System.out.println("■test_readDataFromMysql_01_NG 開始");
    	
    	CmsEmployeeBean bean = new CmsEmployeeBean();
    	bean.setEmployeeId("1111");
    	List<CmsEmployeeBean> mockResults = new ArrayList<CmsEmployeeBean>();
        when(this.mapper.select(bean)).thenReturn(mockResults);
    	
        try {
			ReadDataBaseForm form = new ReadDataBaseForm();
			form.setEmployeeId("1111");
			List<CmsEmployeeBean> beanList = service.readDataFromMysql(form);
		} catch (Exception e) {
	    	assertEquals("検索結果がありません。", e.getMessage());
		}
        
    	
    	System.out.println("■test_readDataFromMysql_01_NG 終了");
    }
    
    private CmsEmployeeBean createBean(String key) {
    	CmsEmployeeBean mockBean = new CmsEmployeeBean();
    	mockBean.setEmployeeId(key);
    	mockBean.setName("社員"+key);
    	mockBean.setAddress("aaaaaaa");
    	
    	return mockBean;
    }
}