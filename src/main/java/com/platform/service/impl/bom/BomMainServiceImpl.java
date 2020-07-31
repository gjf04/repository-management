package com.platform.service.impl.bom;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.dao.bom.BomMainDao;
import com.platform.entity.bom.BomMain;
import com.platform.service.bom.BomMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by GaoJingFei on 2020/07/27.
 */
@Slf4j
@Service
public class BomMainServiceImpl implements BomMainService {
    @Autowired
    private BomMainDao bomMainDao;

    @Override
    public ServiceResult<Map<String, Object>> getBomMainList(Map<String, Object> paramMap, PagerInfo pagerInfo) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 记录总数
        int rowsCount = bomMainDao.getBomMainListCount(paramMap);
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
        List<BomMain> bomMains = bomMainDao.getBomMainList(paramMap);
        map.put("data", bomMains);
        map.put("total", rowsCount);
        result.setResult(map);
        return result;
    }

    @Override
    public ServiceResult<BomMain> insert(BomMain bomMain) {
        ServiceResult<BomMain> result = new ServiceResult<BomMain>();
        Integer count = bomMainDao.insert(bomMain);
        result.setSuccess(count == 1);
        result.setResult(bomMain);
        return result;
    }

    @Override
    public List<BomMain> getAll() {
        return bomMainDao.getAll();
    }

}
