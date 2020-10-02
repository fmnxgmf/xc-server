package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * CmsSiteRepository
 *
 * @author gmf
 * @version V1.0
 * @date 2020/9/26
 **/
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
