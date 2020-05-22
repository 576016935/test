package com.qf.shop.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {

	@Autowired
	private SolrClient solrClient;

	/**
	 * 添加索引库
	 */
	@Test
	public void contextLoads() {

		for(int i=0;i<10;i++){
			//1，创建索引库对象
			SolrInputDocument solrInputFields=new SolrInputDocument();
			solrInputFields.addField("id",i);
			solrInputFields.addField("title","华为手机"+i);
			solrInputFields.addField("ginfo","手机中的战斗机"+i);
			solrInputFields.addField("gprice","2222"+i);
			solrInputFields.addField("gimage","aaaaaaa"+i);

			try {
				//2，将索引对象添加到索引库中
				solrClient.add(solrInputFields);
				//3，提交
				solrClient.commit();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

	/**
	 * 删除索引库
	 *
	 */
	@Test
	public void delete(){
		try {
			solrClient.deleteById("1");
			solrClient.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 搜索索引库
	 */

	@Test
	public void queryAll(){
		SolrQuery solrQuery=new SolrQuery();
		solrQuery.setQuery("*:*");

		try {
			//执行查询
			QueryResponse response = solrClient.query(solrQuery);
			//获取结果
			SolrDocumentList results = response.getResults();

			for (SolrDocument result : results) {
				System.out.println(result.getFieldValue("id"));
				System.out.println(result.getFieldValue("title"));
				System.out.println(result.getFieldValue("gprice"));
				System.out.println(result.getFieldValue("goods_info"));
				System.out.println(result.getFieldValue("gimage"));
				System.out.println("-----------------------");

			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
