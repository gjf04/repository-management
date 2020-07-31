package com.platform.dao.bom;


import com.platform.dao.AbstractDao;
import com.platform.entity.bom.BomMain;
import com.platform.entity.bom.BomSub;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2020/07/28.
 */
@Repository
public class BomSubDao extends AbstractDao {

    public BomSub getById(Long id){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "getById", id);
    }

    public BomSub getByBomId(Long bomId){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "getByBomId", bomId);
    }

    public List<BomSub> getBomSubList(Map<String, Object> paramMap){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getBomSubList", paramMap);
    }

    public Integer getBomSubListCount(Map<String, Object> params){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getBomSubListCount", params);
    }

    public Integer insert(BomSub bomSub){
        return this.getSqlSession().insert(getNamespacePrefix() + "insert", bomSub);
    }

    public Integer batchInsert(List<BomSub> bomSubList){
        return this.getSqlSession().insert(getNamespacePrefix() + "batchInsert", bomSubList);
    }

}