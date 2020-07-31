package com.platform.dao.bom;


import com.platform.dao.AbstractDao;
import com.platform.entity.bom.BomMain;
import com.platform.entity.bom.BomSub;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2020/07/27.
 */
@Repository
public class BomMainDao extends AbstractDao {

    public BomMain getById(Long id){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "getById", id);
    }

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

    //更新
    public Integer update(BomMain bomMain){
        return this.getSqlSession().update(getNamespacePrefix()+"updateByPrimaryKeySelective", bomMain);
    }

}