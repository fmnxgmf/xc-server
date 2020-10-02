package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * CmsPageRepositoryTest
 *
 * @author gmf
 * @version V1.0
 * @date 2020/9/16
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    // 分页测试
    @Test
    public void testFindPage(){
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        all.forEach(System.out::println);
    }
    // 修改
    @Test
    public void testUpdate(){
        Optional<CmsPage> cmsPage = cmsPageRepository.findById("5a754adf6abb500ad05688d9");
        if (cmsPage.isPresent()){
            CmsPage cmsPage1 = cmsPage.get();
            System.out.println("cmsPage1 = " + cmsPage1);
            cmsPage1.setPageAliase("testUpdate");
            CmsPage save = cmsPageRepository.save(cmsPage1);
            System.out.println("save = " + save);
        }
    }
    @Test
    public void testAll(){
        int page = 0;
        int size = 5;
        // 条件匹配
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        //页面别名模糊查询，需要自定义字符串的匹配器实现模糊查询
        // ExampleMatcher.GenericPropertyMatchers.contains() 包含
        // ExampleMatcher.GenericPropertyMatchers.startsWith()//开头匹配
        // 设置条件值
        CmsPage cmsPage = new CmsPage();
        // 站点Id
//        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        // 模板ID
//        cmsPage.setTemplateId("5a962c16b00ffc514038fafd");
        // 设置别名
        cmsPage.setPageAliase("课程详情页面");
         // 创建条件实列
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Pageable pageable = PageRequest.of(page, 1);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        System.out.println(all.getContent());
    }
}
