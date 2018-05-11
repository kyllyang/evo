package com.ritoinfo.framework.evo.sp.sys.bizz;

import com.ritoinfo.framework.evo.sp.base.starter.assist.BaseHelper;
import com.ritoinfo.framework.evo.sp.base.starter.bizz.BaseBizz;
import com.ritoinfo.framework.evo.sp.sys.condition.FuncCondition;
import com.ritoinfo.framework.evo.sp.sys.dao.FuncDao;
import com.ritoinfo.framework.evo.sp.sys.dto.FuncDto;
import com.ritoinfo.framework.evo.sp.sys.dto.MenuDto;
import com.ritoinfo.framework.evo.sp.sys.dto.PermissionDto;
import com.ritoinfo.framework.evo.sp.sys.entity.Func;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Kyll
 * Date: 2018-03-04 18:05
 */
@Slf4j
@Transactional(readOnly = true)
@Service
public class FuncBizz extends BaseBizz<FuncDao, Func, Long, FuncCondition, FuncDto> {
	@Autowired
	private RoleBizz roleBizz;
	@Autowired
	private MenuBizz menuBizz;

	public List<FuncDto> findByMicro(Long microId) {
		FuncCondition condition = new FuncCondition();
		condition.setMicroId(microId);
		return BaseHelper.toDto(dao.find(condition));
	}

	public List<FuncDto> findByRole(Long roleId) {
		return BaseHelper.toDto(dao.findByRole(roleId));
	}

	public List<PermissionDto> findByUsername(String username) {
		return BaseHelper.mapToDto(dao.findByUsername(username), PermissionDto.class);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		roleBizz.deleteByFunc(id);

		for (MenuDto menuDto : menuBizz.findByFunc(id)) {
			menuDto.setFuncId(null);
			menuBizz.update(menuDto);
		}

		super.delete(id);
	}
}
