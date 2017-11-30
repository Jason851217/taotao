package com.learningcenter.service;

import com.learningcenter.common.pojo.EUDataGridResult;
import com.learningcenter.common.pojo.TaotaoResult;
import com.learningcenter.pojo.TbItem;

public interface ItemService {

	TbItem getItemById(long itemId);
	EUDataGridResult getItemList(int page, int rows);
	TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception;
}
