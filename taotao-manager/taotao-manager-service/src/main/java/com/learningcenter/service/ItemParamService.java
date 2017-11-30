package com.learningcenter.service;

import com.learningcenter.common.pojo.TaotaoResult;
import com.learningcenter.pojo.TbItemParam;

public interface ItemParamService {

	TaotaoResult getItemParamByCid(long cid);
	TaotaoResult insertItemParam(TbItemParam itemParam);
}
