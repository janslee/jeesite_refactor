/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.prcsteel.modules.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.oa.entity.TestAudit;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.prcsteel.modules.order.entity.OrderMain;
import com.prcsteel.modules.order.service.OrderMainService;

/**
 * 用于订单系统管理Controller
 * @author ericlee
 * @version 2016-08-30
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderMain")
public class OrderMainController extends BaseController {

	@Autowired
	private OrderMainService orderMainService;
	
	@ModelAttribute
	public OrderMain get(@RequestParam(required=false) String id) {
		OrderMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderMainService.get(id);
		}
		if (entity == null){
			entity = new OrderMain();
		}
		return entity;
	}
	
	/**
	 * 申请单填写
	 * @param testAudit
	 * @param model
	 * @return
	 */
	@RequiresPermissions("order:orderMain:view")
	@RequestMapping(value = "form")
	public String form(OrderMain orderMain, Model model) {
		
		String view = "orderMainForm";
		
		// 查看审批申请单
		if (StringUtils.isNotBlank(orderMain.getId())){//.getAct().getProcInsId())){

			// 环节编号
			String taskDefKey = orderMain.getAct().getTaskDefKey();
			
			// 查看工单
			if(orderMain.getAct().isFinishTask()){
				view = "orderMainForm";
			}

			// 技术部/研发部/综合部审核
			else if ("techAudit".equals(taskDefKey)||"rdAudit".equals(taskDefKey)||"genAudit".equals(taskDefKey)){
				view = "orderMainForm";
//				String formKey = "/oa/testAudit";
//				return "redirect:" + ActUtils.getFormUrl(formKey, testAudit.getAct());
			}
			// 领导审核
			else if ("leadAudit".equals(taskDefKey)){
				view = "orderMainForm";
			}
		}

		model.addAttribute("orderMain", orderMain);
		return "modules/order/" + view;
	}
	
	/**
	 * 工单执行（完成任务）
	 * @param orderMain
	 * @param model
	 * @return
	 */
	@RequiresPermissions("order:orderMain:view")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(OrderMain orderMain, Model model) {
		if (StringUtils.isBlank(orderMain.getAct().getFlag())
				|| StringUtils.isBlank(orderMain.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(orderMain, model);
		}
		orderMainService.auditSave(orderMain);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	
	@RequiresPermissions("order:orderMain:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderMain orderMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderMain> page = orderMainService.findPage(new Page<OrderMain>(request, response), orderMain); 
		model.addAttribute("page", page);
		return "modules/order/orderMainList";
	}

	/*
	@RequiresPermissions("order:orderMain:view")
	@RequestMapping(value = "form")
	public String form(OrderMain orderMain, Model model) {
		model.addAttribute("orderMain", orderMain);
		return "modules/order/orderMainForm";
	}
	*/

	@RequiresPermissions("order:orderMain:edit")
	@RequestMapping(value = "save")
	public String save(OrderMain orderMain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderMain)){
			return form(orderMain, model);
		}
		orderMainService.save(orderMain);
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderMain/?repage";
	}
	
	@RequiresPermissions("order:orderMain:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderMain orderMain, RedirectAttributes redirectAttributes) {
		orderMainService.delete(orderMain);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/order/orderMain/?repage";
	}

}