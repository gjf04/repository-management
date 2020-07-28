package com.platform.service.impl.bom;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.dao.bom.BomDeliveryDetailDao;
import com.platform.entity.bom.BomDeliveryDetail;
import com.platform.service.bom.BomDeliveryDetailService;
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
public class BomDeliveryDetailServiceImpl implements BomDeliveryDetailService {
    @Autowired
    private BomDeliveryDetailDao bomDeliveryDetailDao;

    @Override
    public ServiceResult<Map<String, Object>> getBomDeliveryDetailList(Map<String, Object> paramMap, PagerInfo pagerInfo) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 记录总数
        int rowsCount = bomDeliveryDetailDao.getBomDeliveryDetailListCount(paramMap);
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
        List<BomDeliveryDetail> bomDeliveryDetails = bomDeliveryDetailDao.getBomDeliveryDetailList(paramMap);
        map.put("data", bomDeliveryDetails);
        map.put("total", rowsCount);
        result.setResult(map);
        return result;
    }

    @Override
    public ServiceResult<Integer> insert(BomDeliveryDetail bomDeliveryDetail) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        result.setResult(bomDeliveryDetailDao.insert(bomDeliveryDetail));

        return result;
    }

}
