/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.prcsteel.modules.order.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.TestAudit;
import com.prcsteel.modules.order.entity.OrderMain;

/**
 * 用于订单系统管理DAO接口
 * @author ericlee
 * @version 2016-08-30
 */
@MyBatisDao
public interface OrderMainDao extends CrudDao<OrderMain> {
	
	public OrderMain getByProcInsId(String procInsId);
	
}