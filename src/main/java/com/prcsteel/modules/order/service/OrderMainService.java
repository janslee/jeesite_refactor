/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.prcsteel.modules.order.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.oa.entity.TestAudit;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.prcsteel.modules.order.entity.OrderMain;
import com.google.common.collect.Maps;
import com.prcsteel.modules.order.dao.OrderMainDao;

/**
 * 用于订单系统管理Service
 * @author ericlee
 * @version 2016-08-30
 */
@Service
@Transactional(readOnly = true)
public class OrderMainService extends CrudService<OrderMainDao, OrderMain> {
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private OfficeService officeService;
	
	public OrderMain getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}
	
	/*@Transactional(readOnly = false)
	public void save(OrderMain orderMain) {
		super.save(orderMain);
	}*/
	
	/**
	 * 审核新增或编辑
	 * @param orderMain
	 */
	@Transactional(readOnly = false)
	public void save(OrderMain orderMain) {
		
		// 申请发起
		if (StringUtils.isBlank(orderMain.getId())){
			orderMain.preInsert();
			dao.insert(orderMain);
			
			// 启动流程
			Office office = officeService.get(orderMain.getOffice().getId());
			Map<String, Object> vars = Maps.newHashMap();
			logger.info("The office code is:" + office.getCode());
			vars.put("deptId", orderMain.getOffice().getCode()); //增加部门参数

			actTaskService.startProcess(ActUtils.PD_ORDER_AUDIT[0], ActUtils.PD_ORDER_AUDIT[1], orderMain.getId(), orderMain.getRemark(),vars);
			
		}
		
		// 重新编辑申请,暂时似乎不需要
		/*
		else{
			orderMain.preUpdate();
			dao.update(orderMain);

			orderMain.getAct().setComment(("yes".equals(orderMain.getAct().getFlag())?"[重申] ":"[销毁] ")+orderMain.getAct().getComment());
			
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(orderMain.getAct().getFlag())? "1" : "0");
			actTaskService.complete(orderMain.getAct().getTaskId(), orderMain.getAct().getProcInsId(), orderMain.getAct().getComment(), orderMain.getRemark(), vars);
		}*/
	}

	/**
	 * 审核审批保存
	 * @param orderMain
	 */
	@Transactional(readOnly = false)
	public void auditSave(OrderMain orderMain) {
		
		// 设置意见
		orderMain.getAct().setComment(("yes".equals(orderMain.getAct().getFlag())?"[同意] ":"[驳回] ")+orderMain.getAct().getComment());
		
		orderMain.preUpdate();
		
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = orderMain.getAct().getTaskDefKey();

		// 审核环节
		if ("leadAudit".equals(taskDefKey)){
			
		}
		// 未知环节，直接返回
		else{
			return;
		}
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(orderMain.getAct().getFlag())? "1" : "0");
		actTaskService.complete(orderMain.getAct().getTaskId(), orderMain.getAct().getProcInsId(), orderMain.getAct().getComment(), vars);

	}
	
	

	public OrderMain get(String id) {
		return super.get(id);
	}
	
	public List<OrderMain> findList(OrderMain orderMain) {
		return super.findList(orderMain);
	}
	
	public Page<OrderMain> findPage(Page<OrderMain> page, OrderMain orderMain) {
		return super.findPage(page, orderMain);
	}
	
	
	
	@Transactional(readOnly = false)
	public void delete(OrderMain orderMain) {
		super.delete(orderMain);
	}
	
}