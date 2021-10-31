package com.xiaomo.cloud.sysempextorgpos.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomo.cloud.sysempextorgpos.entity.SysEmpExtOrgPos;
import com.xiaomo.cloud.sysempextorgpos.mapper.SysEmpExtOrgPosMapper;
import com.xiaomo.cloud.sysempextorgpos.service.ISysEmpExtOrgPosService;
import com.xiaomo.cloud.sysorg.entity.SysOrg;
import com.xiaomo.cloud.sysorg.service.ISysOrgService;
import com.xiaomo.cloud.syspos.entity.SysPos;
import com.xiaomo.cloud.syspos.service.ISysPosService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 员工附属机构岗位表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Service
public class SysEmpExtOrgPosServiceImpl extends ServiceImpl<SysEmpExtOrgPosMapper, SysEmpExtOrgPos> implements ISysEmpExtOrgPosService {


    @Resource
    private ISysOrgService sysOrgService;

    @Resource
    private ISysPosService sysPosService;

    /**
     * 附属机构id参数键
     */
    private static final String EXT_ORG_ID_DICT_KEY = "orgId";

    /**
     * 附属机构编码参数键
     */
    private static final String EXT_ORG_CODE_DICT_KEY = "orgCode";

    /**
     * 附属机构名称参数键
     */
    private static final String EXT_ORG_NAME_DICT_KEY = "orgName";

    /**
     * 附属职位id参数键
     */
    private static final String EXT_POS_ID_DICT_KEY = "posId";

    /**
     * 附属职位编码参数键
     */
    private static final String EXT_POS_CODE_DICT_KEY = "posCode";

    /**
     * 附属职位名称参数键
     */
    private static final String EXT_POS_NAME_DICT_KEY = "posName";


    @Override
    public List<Dict> getEmpExtOrgPosDictList(Long empId, boolean isFillId) {
        List<Dict> dictList = CollectionUtil.newArrayList();

        LambdaQueryWrapper<SysEmpExtOrgPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpExtOrgPos::getEmpId, empId);

        this.list(queryWrapper).forEach(sysEmpExtOrgPos -> {
            Dict dict = Dict.create();
            Long orgId = sysEmpExtOrgPos.getOrgId();
            SysOrg sysOrg = sysOrgService.getById(orgId);
            dict.put(EXT_ORG_CODE_DICT_KEY, sysOrg.getCode());
            dict.put(EXT_ORG_NAME_DICT_KEY, sysOrg.getName());

            Long posId = sysEmpExtOrgPos.getPosId();
            SysPos sysPos = sysPosService.getById(posId);
            dict.put(EXT_POS_CODE_DICT_KEY, sysPos.getCode());
            dict.put(EXT_POS_NAME_DICT_KEY, sysPos.getName());

            if (isFillId) {
                dict.put(EXT_ORG_ID_DICT_KEY, orgId);
                dict.put(EXT_POS_ID_DICT_KEY, posId);
            }
            dictList.add(dict);
        });

        return dictList;
    }
}
