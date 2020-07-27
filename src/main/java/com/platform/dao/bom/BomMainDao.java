package com.platform.dao.bom;


import com.platform.dao.AbstractDao;
import com.platform.entity.bom.BomMain;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2020/07/27.
 */
@Repository
public class BomMainDao extends AbstractDao {

    public List<BomMain> getBomMainList(Map<String, Object> paramMap){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getBomMainList", paramMap);
    }

    public Integer getBomMainListCount(Map<String, Object> params){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getBomMainListCount", params);
    }

    public Integer insert(BomMain bomMain){
        return this.getSqlSession().insert(getNamespacePrefix() + "insert", bomMain);
    }

    /**
     * 获取所有
     *
     */
    public List<BomMain> getAll(){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getAll");
    }

}