package com.platform.dao.bom;


import com.platform.dao.AbstractDao;
import com.platform.entity.bom.BomMain;
import com.platform.entity.bom.BomSub;
import com.platform.entity.system.UserInfo;
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

    public List<BomSub> getByBomId(Long bomId){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getByBomId", bomId);
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

    //更新
    public Integer update(BomSub bomSub){
        return this.getSqlSession().update(getNamespacePrefix()+"updateByPrimaryKeySelective", bomSub);
    }

    //根据bomId删除
    public Integer deleteByBomId(Long bomId){
        return this.getSqlSession().update(getNamespacePrefix()+"deleteByBomId", bomId);
    }

    //根据Id删除
    public Integer deleteById(Long id){
        return this.getSqlSession().update(getNamespacePrefix()+"deleteById", id);
    }

    //更新已发货数量
    public Integer updateDeliveryAmount(BomSub bomSub){
        return this.getSqlSession().update(getNamespacePrefix()+"updateDeliveryAmount", bomSub);
    }

}