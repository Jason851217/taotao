package com.learningcenter.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.learningcenter.common.utils.JsonUtils;
import com.learningcenter.pojo.TbContent;
import com.learningcenter.rest.dao.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.learningcenter.mapper.TbItemCatMapper;
import com.learningcenter.pojo.TbItemCat;
import com.learningcenter.pojo.TbItemCatExample;
import com.learningcenter.pojo.TbItemCatExample.Criteria;
import com.learningcenter.rest.pojo.CatNode;
import com.learningcenter.rest.pojo.CatResult;
import com.learningcenter.rest.service.ItemCatService;

/**
 * 商品分类服务
 * <p>Title: ItemCatServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	Jason
 * @date	2015年9月7日下午2:44:41
 * @version 1.0
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;


	@Autowired
	private JedisClient jedisClient;


	@Value("${INDEX_ITEMCAT_REDIS_KEY}")
	private String index_itemcat_redis_key;


	
	@Override
	public CatResult getItemCatList() {
		
		CatResult catResult = new CatResult();
		//查询分类列表
		catResult.setData(getCatList(0));
		return catResult;
	}
	
	/**
	 * 查询分类列表
	 * <p>Title: getCatList</p>
	 * <p>Description: </p>
	 * @param parentId
	 * @return
	 */
	private List<?> getCatList(long parentId) {
		//从缓存中取内容
		String result ="";
		List<TbItemCat> list =new ArrayList<>();
		try {
		   result = jedisClient.hget(index_itemcat_redis_key,parentId+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!StringUtils.isBlank(result)){
				list=JsonUtils.jsonToList(result, TbItemCat.class);
		}else{
			//创建查询条件
			TbItemCatExample example = new TbItemCatExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(parentId);
			//执行查询
			list = itemCatMapper.selectByExample(example);
			jedisClient.hset(index_itemcat_redis_key, parentId + "", JsonUtils.objectToJson(list));
		}
		//返回值list
		List resultList = new ArrayList<>();
		//向list中添加节点
		int count = 0;
		for (TbItemCat tbItemCat : list) {
			//判断是否为父节点
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				
				resultList.add(catNode);
				count ++;
				//第一层只取14条记录
				if (parentId == 0 && count >=14) {
					break;
				}
			//如果是叶子节点
			} else {
				resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
			}
		}
		return resultList;
	}

}
