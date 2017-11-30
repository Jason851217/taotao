package com.learningcenter.portal.service;

import com.learningcenter.portal.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString, int page);
}
