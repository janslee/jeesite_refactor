

/* Create Tables */

-- 订单数据表
CREATE TABLE order_main
(
	-- 编号
	id varchar(64) NOT NULL,
	
	proc_ins_id varchar(64) COMMENT '流程实例编号',
	
	-- 归属部门
	office_id varchar(64),
	-- 归属用户
	user_id varchar(64),
	-- 归属区域
	area_id varchar(64),
	-- 订单名称
	name varchar(100),
	code varchar(10) NOT NULL UNIQUE,
	-- 性别（字典类型：sex）
	type char(1) NOT NULL,
	status varchar(30) NOT NULL,
	-- 关闭原因：
	-- 审核不通过
	-- 超时
	-- 
	close_reason varchar(30),
	remark varchar(200),
	-- 创建者
	create_by varchar(64) NOT NULL,
	-- 创建时间
	create_date timestamp NOT NULL,
	-- 更新者
	update_by varchar(64) NOT NULL,
	-- 更新时间
	update_date timestamp NOT NULL,
	-- 备注信息
	remarks varchar(255),
	-- 删除标记（0：正常；1：删除）
	del_flag char(1) DEFAULT '0' NOT NULL,
	PRIMARY KEY (id)
);



/* Create Indexes */

CREATE INDEX test_data_del_flag ON order_main ();



