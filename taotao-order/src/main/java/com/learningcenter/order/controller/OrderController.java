package com.learningcenter.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learningcenter.common.pojo.TaotaoResult;
import com.learningcenter.common.utils.ExceptionUtil;
import com.learningcenter.order.pojo.Order;
import com.learningcenter.order.service.OrderService;

/**
 * 订单Controller
 * <p>Title: OrderController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	Jason
 * @date	2015年9月16日上午10:46:50
 * @version 1.0
 */
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createOrder(@RequestBody Order order) {
		try {
			TaotaoResult result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
