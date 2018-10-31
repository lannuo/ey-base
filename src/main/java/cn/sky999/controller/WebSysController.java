package cn.sky999.controller;

import cn.sky999.aspect.Authority;
import cn.sky999.aspect.AuthorityType;
import cn.sky999.baseEntity.WebSys;
import cn.sky999.common.page.Page;
import cn.sky999.common.response.OperInfo;
import cn.sky999.service.WebSysServiceIntFc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/webSys/")
public class WebSysController extends BaseController {
    @Autowired
    private WebSysServiceIntFc webSysServiceIntFc;

    @Authority(AuthorityType.Validate)
    @RequestMapping("add")
    @ResponseBody
    public OperInfo add(WebSys webSys, OperInfo operInfo) throws Exception{
        webSysServiceIntFc.saveEntity(webSys);
        return operInfo;
    }
    @Authority(AuthorityType.Validate)
    @RequestMapping("modify")
    @ResponseBody
    public OperInfo modify(WebSys webSys, OperInfo operInfo) throws Exception{
        webSysServiceIntFc.updateEntity(webSys);
        return operInfo;
    }
    @Authority(AuthorityType.Validate)
    @RequestMapping("delByIds")
    @ResponseBody
    public OperInfo delByIds(String ids,OperInfo operInfo) throws Exception{
        webSysServiceIntFc.deleteByIds(ids);
        return operInfo;
    }
    @RequestMapping("findList")
    @ResponseBody
    public OperInfo findList(Page page, WebSys webSys, OperInfo operInfo) throws Exception {
        operInfo.setVo(webSysServiceIntFc.findList(page,webSys));
        return operInfo;
    }
}
