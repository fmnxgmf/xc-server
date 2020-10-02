package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * QueryPageRequest
 * @author gmf
 * @date 2020/6/8
 * @version V1.0
 */
@Data
public class QueryPageRequest<C> extends RequestData {
    /**
     * 站点id
     */
    @ApiModelProperty("站点id")
    private String siteId;
    /**
     * 页面Id
     */
    @ApiModelProperty("页面ID")
    private String pageId;
    /**
     * 页面名称
     */
    @ApiModelProperty("页面名称")
    private String pageName;
    /**
     * 模版id
     */
    @ApiModelProperty("模版id")
    private String templateId;
    /**
     *  页面别名
     */
    @ApiModelProperty("页面别名")
    private String pageAliase;
    @ApiModelProperty("页面类型")
    private String pageType;
}
