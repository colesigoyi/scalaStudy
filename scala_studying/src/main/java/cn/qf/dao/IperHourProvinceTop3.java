package cn.qf.dao;/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日
 *
 * @author 陶雪峰
 * @version : 1.0
 */

import cn.qf.entity.PerHourProvinceTop3;

import java.sql.SQLException;
import java.util.List;

/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-11-04 16:59
 * @ desc:  
 **/

public interface IperHourProvinceTop3 {
    /**
     * 插入
     * @param entity
     */
    void save(PerHourProvinceTop3 entity) throws SQLException;
    /**
     * 批量插入
     *
     * @param entities
     */
    void batchSave(List<PerHourProvinceTop3> entities);
}
