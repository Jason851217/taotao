package com.learningcenter.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.learningcenter.search.pojo.SearchResult;

public interface SearchDao {

	SearchResult search(SolrQuery query) throws Exception;
}
