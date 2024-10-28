package com.promptoven.authservice.adaptor.web.controller.vo.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequestVO {

	private int id;
	private String name;
	@Nullable
	private String description;
}
