package com.xiaomo.cloud.sysemppos.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomo.cloud.sysemppos.entity.SysEmpPos;
import com.xiaomo.cloud.sysemppos.mapper.SysEmpPosMapper;
import com.xiaomo.cloud.sysemppos.service.ISysEmpPosService;
import com.xiaomo.cloud.syspos.entity.SysPos;
import com.xiaomo.cloud.syspos.service.ISysPosService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 员工职位关联表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Service
public class SysEmpPosServiceImpl extends ServiceImpl<SysEmpPosMapper, SysEmpPos> implements ISysEmpPosService {


    @Resource
    private ISysPosService sysPosService;

    /**
     * 职位id参数键
     */
    private static final String POS_ID_DICT_KEY = "posId";

    /**
     * 职位编码参数键
     */
    private static final String POS_CODE_DICT_KEY = "posCode";

    /**
     * 职位名称参数键
     */
    private static final String POS_NAME_DICT_KEY = "posName";
    @Override
    public List<Dict> getEmpPosDictList(Long empId, boolean isFillId) {
        List<Dict> dictList = CollectionUtil.newArrayList();

        LambdaQueryWrapper<SysEmpPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpPos::getEmpId, empId);

        this.list(queryWrapper).forEach(sysEmpPos -> {
            Dict dict = Dict.create();
            Long posId = sysEmpPos.getPosId();
            SysPos sysPos = sysPosService.getById(posId);
            if (isFillId) {
                dict.put(POS_ID_DICT_KEY, posId);
            }
            dict.put(POS_CODE_DICT_KEY, sysPos.getCode());
            dict.put(POS_NAME_DICT_KEY, sysPos.getName());
            dictList.add(dict);
        });
        return dictList;
    }
}
