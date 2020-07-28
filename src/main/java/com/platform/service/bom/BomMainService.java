package com.platform.service.bom;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.entity.bom.BomMain;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2020/07/27.
 */

public interface BomMainService {

    public ServiceResult<Map<String, Object>> getBomMainList(Map<String, Object> paramMap, PagerInfo pagerInfo);

    public ServiceResult<Integer> insert(BomMain bomMain);

    /**
     * 获取系统中所有
     * @return
     */
    public List<BomMain> getAll();

}
