package com.learningcenter.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learningcenter.common.pojo.TaotaoResult;
import com.learningcenter.search.service.ItemService;

/**
 * 索引库维护
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	Jason
 * @date	2015年9月11日下午2:50:45
 * @version 1.0
 */
@Controller
@RequestMapping("/manager")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	/**
	 * 导入商品数据到索引库
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public TaotaoResult importAllItems() {
		TaotaoResult result = itemService.importAllItems();
		return result;
	}
}
