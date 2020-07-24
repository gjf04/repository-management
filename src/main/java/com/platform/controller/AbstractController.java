package com.platform.controller;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractController {
	
	/**
	 * 获取sessionId
	 * @param request
	 * @return
	 */
	public String getSessionId(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		return sessionId;
	}
	
}
