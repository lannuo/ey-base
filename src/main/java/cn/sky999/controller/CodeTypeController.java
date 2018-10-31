package cn.sky999.controller;

import cn.sky999.aspect.Authority;
import cn.sky999.aspect.AuthorityType;
import cn.sky999.baseEntity.CodeType;
import cn.sky999.common.page.Page;
import cn.sky999.common.response.OperInfo;
import cn.sky999.service.CodeTypeServiceIntFc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/codeType/")
public class CodeTypeController extends BaseController{
    @Autowired
    private CodeTypeServiceIntFc codeTypeServiceIntFc;

    /**
     * 新增
     * @param codeType
     * @param operInfo
     * @return
     * @throws Exception
     */
    @Authority(AuthorityType.Validate)
    @RequestMapping("add")
    @ResponseBody
    public OperInfo save(CodeType codeType, OperInfo operInfo) throws Exception {
        if(codeTypeServiceIntFc.getById(codeType.getTypeId())==null)
            codeTypeServiceIntFc.saveEntity(codeType);
        else
            codeTypeServiceIntFc.updateEntity(codeType);
        return operInfo;
    }
    /**
     * 修改
     * @param codeType
     * @param operInfo
     * @return
     * @throws Exception
     */
    @Authority(AuthorityType.Validate)
    @RequestMapping("modify")
    @ResponseBody
    public OperInfo modify(CodeType codeType, OperInfo operInfo) throws Exception {
        codeTypeServiceIntFc.updateEntity(codeType);
        return operInfo;
    }

    /**
     * 删除
     * @param ids
     * @param operInfo
     * @return
     * @throws Exception
     */
    @Authority(AuthorityType.Validate)
    @RequestMapping("delByIds")
    @ResponseBody
    public OperInfo delByIds(String ids, OperInfo operInfo) throws Exception{
        codeTypeServiceIntFc.deleteByIds(ids);
        return operInfo;
    }
    /**
     * 分页查找列表
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("findList")
    @ResponseBody
    public OperInfo findList(Page page, CodeType codeType, OperInfo operInfo) throws Exception {
        operInfo.setVo(codeTypeServiceIntFc.findList(page,codeType));
        return operInfo;
    }
}
