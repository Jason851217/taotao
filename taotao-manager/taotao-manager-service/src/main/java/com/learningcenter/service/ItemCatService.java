package com.learningcenter.service;

import java.util.List;

import com.learningcenter.common.pojo.EUTreeNode;

public interface ItemCatService {

	List<EUTreeNode> getCatList(long parentId);
}
