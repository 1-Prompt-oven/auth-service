package com.promptoven.authservice.adaptor.web.controller.vo.out;

import com.promptoven.authservice.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseVO {

	private List<Role> RoleList;
}
