package com.learningcenter.rest.service;

import java.util.List;

import com.learningcenter.pojo.TbContent;

public interface ContentService {

	List<TbContent> getContentList(long contentCid);
}
