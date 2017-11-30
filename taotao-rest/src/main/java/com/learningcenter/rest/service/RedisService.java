package com.learningcenter.rest.service;

import com.learningcenter.common.pojo.TaotaoResult;

public interface RedisService {

	TaotaoResult syncContent(long contentCid);
}
