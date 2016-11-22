package com.jhkj.mosdc.permission.po;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jhkj.mosdc.permission.po.TsCdzy;

/**
 * MenuRowMapper.java
 * @author:党冬(mrdangdong@163.com)
 * @DATE:2012-6-10  上午9:23:29
 *  Copyright (C) 2012 topMan
 */

/**
 * 功能说明:菜单SQL语句直接对应的VO
 * @author: 党冬(mrdangdong@163.com)
 * @DATE:2012-6-10 @TIME: 上午9:23:29
 */
public class UserMenuRowMapper implements RowMapper {
//	sql = "Select  m.id,m.CDZYMC, m.CDSSFL_ID, m.SJZY_ID,m.SFYZ,(Case When r.JS_ID>=0 Then 'True' Else 'False' End) isCheck "
//			.concat(" from TS_CDZY m left Join TS_JS_CDZY r On m.Id=r.CDZY_ID ");

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		TsCdzy menu = new TsCdzy();
		menu.setId(rs.getLong("CDZY_ID"));
		menu.setMc(rs.getString("MC"));
		menu.setCdlj(rs.getString("CDLJ") == null ? "":rs.getString("CDLJ"));
		menu.setAnlxId(rs.getString("ANLX_ID")== null ? new Long(0):new Long(rs.getString("ANLX_ID")));
		menu.setFjdId(rs.getLong("FJD_ID"));
		menu.setCdssflId(rs.getLong("CDSSFL_ID"));
		menu.setPxh(rs.getLong("PXH"));
		return menu;
	}

}