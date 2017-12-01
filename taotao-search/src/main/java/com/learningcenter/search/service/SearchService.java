package com.learningcenter.search.service;

import com.learningcenter.search.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString, int page, int rows) throws Exception;
}
