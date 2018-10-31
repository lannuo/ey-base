package cn.sky999.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.sky999.common.page.Page;
import cn.sky999.common.page.PageData;
import cn.sky999.common.uuid.UUIDUtil;
import org.springframework.stereotype.Service;

import cn.sky999.baseEntity.InterfaceManagement;
import cn.sky999.service.IntefaceManagementServiceIntFc;

@Service
public class IntefaceManagementService extends BService implements IntefaceManagementServiceIntFc {
	
	public IntefaceManagementService() {
		this.mapper = "InterfaceManagementMapper";
	}
	/**
	 * 接口新增
	 */
	@Override
	public void saveEntity(String ifManagementName ,String ifManagementLink,String ifManagementWay) throws Exception {
		InterfaceManagement im=new InterfaceManagement();
		im.setIfManagementId(UUIDUtil.get32UUID());
		im.setIfManagementLink(ifManagementLink);
		im.setIfManagementName(ifManagementName);
		im.setIfManagementWay(ifManagementWay);
		this.save(im);
		
	}
	/**
	 * 接口更新
	 */
	@Override
	public void updateEntity(String ifManagementName ,String ifManagementLink,String ifManagementWay,String ifManagementId) throws Exception {
		InterfaceManagement im=new InterfaceManagement();
		im.setIfManagementId(ifManagementId);
		im.setIfManagementLink(ifManagementLink);
		im.setIfManagementName(ifManagementName);
		im.setIfManagementWay(ifManagementWay);
		this.update(im);
		
	}
	/**
	 * 接口删除
	 */
	@Override
	public void deleteEntity(String ifManagementId) throws Exception {
		InterfaceManagement im=this.get(InterfaceManagement.class, ifManagementId);
		if(im!=null){
			this.delete(im);
		}else{
			throw new Exception("删除失败");
		}
		
	}
	/**
	 * 接口查询
	 */
	@Override
	public PageData seleteEntity(Page page, String ifManagementName) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ifManagementName", ifManagementName);
		return this.findPage("getlistIfManagementName", map, page);
	}

}
