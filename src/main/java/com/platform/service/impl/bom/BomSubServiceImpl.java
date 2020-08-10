package com.platform.service.impl.bom;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.dao.bom.BomSubDao;
import com.platform.entity.bom.BomMain;
import com.platform.entity.bom.BomSub;
import com.platform.service.bom.BomSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by GaoJingFei on 2020/07/28.
 */
@Slf4j
@Service
public class BomSubServiceImpl implements BomSubService {
    @Autowired
    private BomSubDao bomSubDao;

    @Override
    public BomSub getById(Long id){
        return bomSubDao.getById(id);
    }

    @Override
    public List<BomSub> getByBomId(Long bomId){
        return bomSubDao.getByBomId(bomId);
    }

    @Override
    public ServiceResult<Map<String, Object>> getBomSubList(Map<String, Object> paramMap, PagerInfo pagerInfo) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 记录总数
        int rowsCount = bomSubDao.getBomSubListCount(paramMap);
        int start = pagerInfo.getStart();
        int size = pagerInfo.getPageSize();
        if (rowsCount > 0) {
            int totalPage = (rowsCount + size - 1) / size;// 总页数
            int pageIndex = pagerInfo.getPageIndex();// 当前页码
            if (pageIndex > totalPage) {
                // 总页数作为当前页
                start = (totalPage - 1) * size;
            }
        }
        paramMap.put("start", start);
        paramMap.put("size", size);
        List<BomSub> bomSubs = bomSubDao.getBomSubList(paramMap);
        map.put("data", bomSubs);
        map.put("total", rowsCount);
        result.setResult(map);
        return result;
    }

    @Override
    public ServiceResult<Integer> insert(BomSub bomSub) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        result.setResult(bomSubDao.insert(bomSub));
        return result;
    }

    @Override
    public ServiceResult<Integer> batchInsert(List<BomSub> bomSubList) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        result.setResult(bomSubDao.batchInsert(bomSubList));
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateBomSub(BomSub bomSub) {
        ServiceResult<Boolean> executeResult = new ServiceResult<Boolean>();
        BomSub dbBomSub = bomSubDao.getById(bomSub.getId());
        if(dbBomSub == null){
            executeResult.setError("", "该BOM不存在或已经被删除。");
            executeResult.setResult(false);
            return executeResult;
        }
        Integer count = bomSubDao.update(bomSub);
        executeResult.setResult(count == 1);
        return executeResult;
    }

    @Override
    public ServiceResult<Integer> deleteByBomId(Long bomId) {
        ServiceResult<Integer> executeResult = new ServiceResult<Integer>();
        executeResult.setResult(bomSubDao.deleteByBomId(bomId));
        return executeResult;
    }

    @Override
    public ServiceResult<Integer> deleteById(Long id) {
        ServiceResult<Integer> executeResult = new ServiceResult<Integer>();
        executeResult.setResult(bomSubDao.deleteById(id));
        return executeResult;
    }

    @Override
    public ServiceResult<Boolean> updateDeliveryAmount(BomSub bomSub) {
        ServiceResult<Boolean> executeResult = new ServiceResult<Boolean>();
        BomSub dbBomSub = bomSubDao.getById(bomSub.getId());
        if(dbBomSub == null){
            executeResult.setError("", "该BOM不存在或已经被删除。");
            executeResult.setResult(false);
            return executeResult;
        }
        Integer count = bomSubDao.updateDeliveryAmount(bomSub);
        executeResult.setResult(count == 1);
        return executeResult;
    }

}
