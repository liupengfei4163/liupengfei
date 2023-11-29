package com.sample.practice1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cms.entity.employee.CmsEmployeeBean;
import com.cms.mapper.practice1.Practice1Mapper;

@SuppressWarnings("rawtypes")
@RunWith(SpringRunner.class)  
@SpringBootTest
public class ReadcsvServiceTest_Mock_Mapper 
{

	@Mock
    private Practice1Mapper mapper;
    
    /**
     * 正常終了
     * 
     * 検索件数：1件
     */
    @Test
    public void test_readDataFromMysql_Normal_1() 
    {
      	//検索結果Mock
    	List<CmsEmployeeBean> mockResults = new ArrayList<CmsEmployeeBean>();
    	mockResults.add(createBean("1"));
    	
    	//引数
    	CmsEmployeeBean paramBean = new CmsEmployeeBean();
    	
    	Mockito.when(this.mapper.select(paramBean)).thenReturn(mockResults);
    	
    	CmsEmployeeBean bean = new CmsEmployeeBean();
    	List<CmsEmployeeBean> results = mapper.select(bean);
    	//件数
    	assertEquals(1, results.size());
    	
    	//検索結果確認
    	assertEquals("1", results.get(0).getEmployeeId());
    	assertEquals("社員1", results.get(0).getName());
    }
    
    
    /**
     * 正常終了
     * 
     * 検索件数：複数件
     */
    @Test
    public void test_readDataFromMysql_Normal_2() 
    {
      	//検索結果Mock
    	List<CmsEmployeeBean> mockResults = new ArrayList<CmsEmployeeBean>();
    	mockResults.add(createBean("1"));
    	mockResults.add(createBean("2"));
    	
    	//引数
    	CmsEmployeeBean paramBean = new CmsEmployeeBean();
    	
    	Mockito.when(this.mapper.select(paramBean)).thenReturn(mockResults);
    	
    	CmsEmployeeBean bean = new CmsEmployeeBean();
    	List<CmsEmployeeBean> results = mapper.select(bean);
    	//件数比較
    	assertEquals(2, results.size());
    	//1件目の検索結果を比較する
    	assertEquals("1", results.get(0).getEmployeeId());
    	assertEquals("社員1", results.get(0).getName());

    	//2件目の検索結果を比較する
    	assertEquals("2", results.get(1).getEmployeeId());
    	assertEquals("社員2", results.get(1).getName());
    }
    
    private CmsEmployeeBean createBean(String key) {
    	CmsEmployeeBean mockBean = new CmsEmployeeBean();
    	mockBean.setEmployeeId(key);
    	mockBean.setName("社員"+key);
    	
    	return mockBean;
    }
}