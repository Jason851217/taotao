package com.learningcenter.portal.service;

import com.learningcenter.pojo.TbUser;

public interface UserService {

	TbUser getUserByToken(String token);
}
