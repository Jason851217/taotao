package com.learningcenter.service;

import java.util.List;

import com.learningcenter.common.pojo.EUTreeNode;
import com.learningcenter.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	List<EUTreeNode> getCategoryList(long parentId);
	TaotaoResult insertContentCategory(long parentId, String name);
}
