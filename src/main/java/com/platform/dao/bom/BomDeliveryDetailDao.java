package com.platform.dao.bom;


import com.platform.dao.AbstractDao;
import com.platform.entity.bom.BomDeliveryDetail;
import com.platform.entity.bom.BomSub;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2020/07/28.
 */
@Repository
public class BomDeliveryDetailDao extends AbstractDao {

    public BomDeliveryDetail getById(Long id){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "getById", id);
    }

    public List<BomDeliveryDetail> getByBomId(Long bomId){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getByBomId", bomId);
    }

    public List<BomDeliveryDetail> getBomDeliveryDetailList(Map<String, Object> paramMap){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getBomDeliveryDetailList", paramMap);
    }

    public Integer getBomDeliveryDetailListCount(Map<String, Object> params){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getBomDeliveryDetailListCount", params);
    }

    public Integer insert(BomDeliveryDetail bomDeliveryDetail){
        return this.getSqlSession().insert(getNamespacePrefix() + "insert", bomDeliveryDetail);
    }

    public Integer batchInsert(List<BomDeliveryDetail> bomDeliveryDetailList){
        return this.getSqlSession().insert(getNamespacePrefix() + "batchInsert", bomDeliveryDetailList);
    }

}