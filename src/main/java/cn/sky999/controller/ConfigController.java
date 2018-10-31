package cn.sky999.controller;

import cn.sky999.aspect.Authority;
import cn.sky999.aspect.AuthorityType;
import cn.sky999.baseEntity.Config;
import cn.sky999.common.page.Page;
import cn.sky999.common.response.OperInfo;
import cn.sky999.service.ConfigServiceIntFc;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/config/")
public class ConfigController extends BaseController {
    @Autowired
    private ConfigServiceIntFc configServiceIntFc;
    @Authority(AuthorityType.Validate)
    @RequestMapping("add")
    @ResponseBody
    public OperInfo add(Config config, OperInfo operInfo) throws Exception{
        if(StringUtils.isEmpty(config.getConfigId())||configServiceIntFc.getById(config.getConfigId())==null)
            configServiceIntFc.saveEntity(config);
        else
            configServiceIntFc.updateEntity(config);
        return operInfo;
    }
    @Authority(AuthorityType.Validate)
    @RequestMapping("modify")
    @ResponseBody
    public OperInfo modify(Config config, OperInfo operInfo) throws Exception{
        configServiceIntFc.updateEntity(config);
        return operInfo;
    }
    @Authority(AuthorityType.Validate)
    @RequestMapping("delByIds")
    @ResponseBody
    public OperInfo delByIds(String ids,OperInfo operInfo) throws Exception{
        configServiceIntFc.deleteByIds(ids);
        return operInfo;
    }
    @RequestMapping("findList")
    @ResponseBody
    public OperInfo findList(Page page, Config config, OperInfo operInfo) throws Exception {
        operInfo.setVo(configServiceIntFc.findList(page,config));
        return operInfo;
    }
}
