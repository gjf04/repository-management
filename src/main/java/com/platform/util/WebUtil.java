package com.platform.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gao.common.PagerInfo;
import com.gao.common.util.StringUtil;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class WebUtil {
    protected static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                     .getLogger(WebUtil.class);
	// 默认分页每页显示记录条数
	private static final int			   DEFAULT_PAGE_SIZE				 = 20;

    /**
	 * 将json或jsonp的返回数据写入到response中。
	 * 
	 * @param result
	 *            要转换为json或jsonp的对象
	 * @param request
	 *            http request
	 * @param response
	 *            json response
	 */
	public static void writeAjaxResponse(Object result,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType("text/plain;charset=utf-8");
			out = response.getWriter();
			String jsonStr = null;
			String callback = request.getParameter("callback");
			String json = JSON.toJSONString(result,
					SerializerFeature.BrowserCompatible);
			if (StringUtil.isEmpty(callback, true)) {
				jsonStr = json;
			} else {
				jsonStr = callback + "(" + json + ")";
			}
			out.write(jsonStr);
		} catch (IOException e) {
			log.error(
					"[shoppingmall-member-web][WebUtil][writeAjaxResponse]开启输出流错误",
					e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {
					log.error(
							"[shoppingmall-member-web][WebUtil][writeAjaxResponse]关闭输出流错误",
							e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static PagerInfo handlerPagerInfo(HttpServletRequest request, Object map) {

		try {
			int pageSize = "".equals(StringUtil.nullSafeString(request.getParameter("rows"))) ? DEFAULT_PAGE_SIZE
					: Integer.parseInt(request.getParameter("rows"));
			int pageIndex = "".equals(StringUtil.nullSafeString(request.getParameter("page"))) ? 1
					: Integer.parseInt(request.getParameter("page"));
			if(pageIndex == 0){
				pageIndex = 1;
			}

			if (map instanceof ModelAndView) {
				((ModelAndView) map).addObject("pageSize", pageSize);
				((ModelAndView) map).addObject("pageIndex", pageIndex);
			} else if (map instanceof Model) {
				((Model) map).addAttribute("pageSize", pageSize);
				((Model) map).addAttribute("pageIndex", pageIndex);
			} else if (map instanceof ModelMap) {
				((ModelMap) map).addAttribute("pageSize", pageSize);
				((ModelMap) map).addAttribute("pageIndex", pageIndex);
			} else {
				((Map<String, String>) map).put("pageSize", pageSize + "");
				((Map<String, String>) map).put("pageIndex", pageIndex + "");
			}

			return new PagerInfo(pageSize, pageIndex);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
