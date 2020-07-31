package com.platform.controller;

import com.platform.util.SessionSecurityConstants;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by GaoJingFei on 2020/07/25.
 */

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

	/**
	 * 获取当前登录人的userId
	 * @param request
	 * @return
	 */
	public Long getCurrentUserId(HttpServletRequest request) {
		return (Long)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
	}

	/**
	 * 获取当前登录人的nickName
	 * @param request
	 * @return
	 */
	public String getCurrentUserNickName(HttpServletRequest request) {
		return (String)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_NICK_NAME));
	}
	
}
