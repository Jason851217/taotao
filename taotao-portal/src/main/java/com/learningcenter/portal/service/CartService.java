package com.learningcenter.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learningcenter.common.pojo.TaotaoResult;
import com.learningcenter.portal.pojo.CartItem;

public interface CartService {

	TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);
	List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);
	TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);
}
