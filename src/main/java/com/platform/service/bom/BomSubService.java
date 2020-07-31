package com.platform.service.bom;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.entity.bom.BomSub;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2020/07/28.
 */

public interface BomSubService {

    public ServiceResult<Map<String, Object>> getBomSubList(Map<String, Object> paramMap, PagerInfo pagerInfo);

    public ServiceResult<Integer> insert(BomSub bomSub);

    public ServiceResult<Integer> batchInsert(List<BomSub> bomSubList);

}
