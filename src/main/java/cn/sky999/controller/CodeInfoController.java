package cn.sky999.controller;

import cn.sky999.aspect.Authority;
import cn.sky999.aspect.AuthorityType;
import cn.sky999.baseEntity.CodeInfo;
import cn.sky999.common.exception.AppException;
import cn.sky999.common.response.OperInfo;
import cn.sky999.service.CodeInfoServiceIntFc;
import cn.sky999.util.Const;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/codeInfo/")
public class CodeInfoController extends BaseController {
    @Autowired
    private CodeInfoServiceIntFc codeInfoServiceIntFc;

    /**
     * 根据codeType 的id 查找codeInfo 列表
     * @param typeId
     * @param operInfo
     * @return
     * @throws Exception
     */
    @RequestMapping("findByType")
    @ResponseBody
    public OperInfo findByTypeId(String typeId, OperInfo operInfo) throws Exception {
        operInfo.setVo(codeInfoServiceIntFc.getCodeInfoList(typeId));
        return operInfo;
    }

    /**
     * 根据类型和值查找单个
     * @param typeId
     * @param infoValue
     * @param operInfo
     * @return
     * @throws Exception
     */
    @RequestMapping("findByTV")
    @ResponseBody
    public OperInfo findByTV(String typeId,String infoValue,OperInfo operInfo) throws Exception {
        operInfo.setVo(codeInfoServiceIntFc.getInfoContent(typeId,infoValue));
        return operInfo;
    }
    @Authority(AuthorityType.Validate)
    @RequestMapping("save")
    @ResponseBody
    public OperInfo save(CodeInfo codeInfo, OperInfo operInfo) throws Exception{
        CodeInfo ci = (CodeInfo) codeInfoServiceIntFc.getInfoContent(codeInfo.getTypeId(), codeInfo.getInfoValue());
        if(StringUtils.isNotBlank(codeInfo.getInfoId())){
            if(ci!=null&&!ci.getInfoId().equals(codeInfo.getInfoId())){
                throw new AppException(Const.MSG_CH_DATA_HAS_IN);
            }
            codeInfoServiceIntFc.updateEntity(codeInfo);
        }else{
            if(ci!=null){
                throw new AppException(Const.MSG_CH_DATA_HAS_IN);
            }
            codeInfoServiceIntFc.saveEntity(codeInfo);
        }
        operInfo.setVo(codeInfo);
        return operInfo;
    }
    @Authority(AuthorityType.Validate)
    @RequestMapping("delByIds")
    @ResponseBody
    public OperInfo delByIds(String ids,OperInfo operInfo) throws Exception{
        codeInfoServiceIntFc.deleteByIds(ids);
        return operInfo;
    }
}
