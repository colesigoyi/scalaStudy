package cn.qf.dao.impl;/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日
 *
 * @author 陶雪峰
 * @version : 1.0
 */

import cn.qf.dao.IperHourProvinceTop3;
import cn.qf.entity.PerHourProvinceTop3;
import cn.qf.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;
import java.util.List;


/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-11-04 17:01
 * @ desc:  
 **/

public class PerHourProvinceTop3Impl implements IperHourProvinceTop3 {
    /**
     * 线程池
     */
    private QueryRunner qr = new QueryRunner(DBCPUtil.getConnectionPool());

    @Override
    public void save(PerHourProvinceTop3 entity){
        try {
            qr.update("insert into tb_per_hour_province_top3(province,hour,adId,cnt) values(?,?,?,?)",
                    entity.getProvince(), entity.getHour(), entity.getAdId(), entity.getCnt());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void batchSave(List<PerHourProvinceTop3> entities) {
        try {
            String sql = "insert into tb_per_hour_province_top3(province,hour,adId,cnt) values(?,?,?,?)";
            Object[][] params = new Object[entities.size()][];

            //将参数指定的合集中的数据存入到上述二维数组中
            for (int i = 0; i < params.length; i++) {
                PerHourProvinceTop3 entity =  entities.get(i);
                params[i] = new Object[]{entity.getProvince(), entity.getHour(), entity.getAdId(), entity.getCnt()};
            }
            //批处理
            qr.batch(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
