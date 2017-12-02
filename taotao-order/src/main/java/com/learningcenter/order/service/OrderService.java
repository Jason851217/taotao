package com.learningcenter.order.service;

import java.util.List;

import com.learningcenter.common.pojo.TaotaoResult;
import com.learningcenter.pojo.TbOrder;
import com.learningcenter.pojo.TbOrderItem;
import com.learningcenter.pojo.TbOrderShipping;

public interface OrderService {

	TaotaoResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping);
}
