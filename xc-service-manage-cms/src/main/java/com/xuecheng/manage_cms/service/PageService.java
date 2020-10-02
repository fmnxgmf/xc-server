package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * PageService
 *
 * @author gmf
 * @version V1.0
 * @date 2020/9/21
 **/
@Service
public class PageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    /**
     * @return com.xuecheng.framework.model.response.QueryResponseResult
     * @param page
     * @param size
     * @param queryPageRequest
     * @author gmf
     * @date 2020/9/21
    **/
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        if (null == queryPageRequest){
            queryPageRequest = new QueryPageRequest();
        }
        // 条件匹配
        // 页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());
        // 条件值
        CmsPage cmsPage = new CmsPage();
        // 站点Id
        if (!StringUtils.isEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        // 页面别名
        if (!StringUtils.isEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        // 模板id
        if (!StringUtils.isEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        // 页面类型
        if (!StringUtils.isEmpty(queryPageRequest.getPageType())){
            cmsPage.setPageType(queryPageRequest.getPageType());
        }
        // 页面类型
        if (!StringUtils.isEmpty(queryPageRequest.getPageName())){
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        // 定义条件查询对象example
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        if (page <= 0){
            page = 1;
        }
        page = page - 1;
        if (size <= 0){
            size = 10;
        }
        // 分页对象
        Sort sort = new Sort(Sort.Direction.DESC,"pageCreateTime");
        Pageable pageable =  PageRequest.of(page,size,sort);
        // 分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<CmsPage>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
        // 返回结果
        return new QueryResponseResult(CommonCode.SUCCESS,cmsPageQueryResult);
    }
    /**
     * 添加页面
     * @return com.xuecheng.framework.domain.cms.response.CmsPageResult
     * @param cmsPage
     * @author gmf
     * @date 2020/9/26
    **/
    public CmsPageResult add(CmsPage cmsPage){
        if (cmsPage == null){
            ExceptionCast.cast(CmsCode.CMS_PARAMS_ERROR);
        }
        // 校验页面是否存在，根据页面名称、站点Id、页面webpath查询
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null){
            // 检验页面是否存在
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
            // 添加页面主键由spring data 自动生成
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            // 返回结果
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }
    /**
     * 根据id获取页面对象cmsPage
     * @return com.xuecheng.framework.domain.cms.CmsPage
     * @param id 页面Id
     * @author gmf
     * @date 2020/10/1
    **/
    public CmsPage findById(String id) {
        Optional<CmsPage> byId = cmsPageRepository.findById(id);
        if (byId.isPresent()){
            return  byId.get();
        }
        return null;
    }

    /**
     * 修改页面信息
     * @return com.xuecheng.framework.domain.cms.response.CmsPageResult
     * @param id
     * @param cmsPage
     * @author gmf
     * @date 2020/10/2
    **/
    public CmsPageResult updateById(String id, CmsPage cmsPage) {
        // 根据id获取值
        CmsPage cmsPage1 = this.findById(id);
        if (cmsPage1 != null){
            // 跟新模板值
            cmsPage1.setTemplateId(cmsPage.getTemplateId());
            cmsPage1.setSiteId(cmsPage.getSiteId());
            cmsPage1.setPageAliase(cmsPage.getPageAliase());
            cmsPage1.setPageName(cmsPage.getPageName());
            cmsPage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            cmsPage1.setPageWebPath(cmsPage.getPageWebPath());
            cmsPage1.setPageType(cmsPage.getPageType());
            cmsPage1.setPageCreateTime(cmsPage.getPageCreateTime());
            // 执行更新
            CmsPage save = cmsPageRepository.save(cmsPage1);
            if (save != null){
                // 返回成功
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS,save);
                return  cmsPageResult;
            }
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }
    /**
     * 根据id删除信息
     * @return com.xuecheng.framework.model.response.ResponseResult
     * @param id
     * @author gmf
     * @date 2020/10/2
    **/
    public ResponseResult delete(String id) {
        CmsPage one = this.findById(id);
        if (one != null){
            // 删除页面
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
