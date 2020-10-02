package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * CmsPageRepository
 *
 * @author gmf
 * @version V1.0
 * @date 2020/9/16
 **/
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    /**
     * 根据页面名称，站点id，页面访问路径查询是否存在
     * @return com.xuecheng.framework.domain.cms.CmsPage
     * @param pageName
     * @param siteId
     * @param pagWebPath
     * @author gmf
     * @date 2020/9/26
    **/
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pagWebPath);
}
