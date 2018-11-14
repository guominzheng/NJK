package com.fh.util;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ServiceUtil {
   private static final ReentrantLock lock = new ReentrantLock();

    public static Map serviceUpdate(String sql , PageData pageData, DaoSupport daoSupport,String type){
        Map<Object,Object> map = new HashMap<Object, Object>();
        try{
             lock.lock();
             if(type.equals(SqlCentre.SQL_TYPE_UPDATE))
            {
                daoSupport.update(sql,pageData);
            }else if(type.equals(SqlCentre.SQL_TYPE_INSERT))
            {
                daoSupport.save(sql,pageData);
            }else if(type.equals(SqlCentre.SQL_TYPE_DELETE))
            {
                daoSupport.delete(sql,pageData);
            }
            map.put("code","1");
            map.put("message","返回正确数据!");
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("代理类sql 修改工具出错=============================>");
            map.put("code","2");
            map.put("message","程序错误，请联系客服！");
        }finally {
            lock.unlock();
        }
        return map;
    }

    /**
     * Service层通用代码整合
     * @param sql sql语句
     * @param page page对象
     * @param daoSupport dao层对象
     * @param type 查询类型 list or one
     * @param pd 传输数据对象PageData
     * @return 前端需要的Map对象数据
     */
    public static Map serviceQuery(String sql , Page page, DaoSupport daoSupport, String type,PageData pd){
        //创建returnMap对象
        Map<Object,Object> map = new HashMap<Object, Object>();
        //创建一个接收数据的 PageData对象
        PageData pageData = null;
        //创建一个接收列表数据的 List<PageData>对象
        List<PageData> pdList = null;
        try{
            //判断当前的查询类型是是否是Object
            if(type.equals(SqlCentre.SQL_TYPE_OBJECT))
            {
                //true 执行Object查询 放入sql语句,以及传输数据对象pd
                //并将数据封装至Map对象
                pageData=(PageData)daoSupport.findForObject(sql,pd);
                map.put("data",pageData);
            }
            //判断当前的查询类型是是否是list
            else if(type.equals(SqlCentre.SQL_TYPE_LIST))
            {
                pdList = (List<PageData>)daoSupport.findForList(sql,page);
                map.put("data",pdList);
            }
            map.put("code","1");
        }catch (Exception e){
            e.printStackTrace();
            map.put("code","2");
            map.put("message","程序错误，请联系客服！");
        }
        return map;
    }
}
