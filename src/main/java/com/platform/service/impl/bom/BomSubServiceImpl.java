package com.platform.service.impl.bom;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.dao.bom.BomSubDao;
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

}
