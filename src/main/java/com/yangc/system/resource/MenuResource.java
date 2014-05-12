package com.yangc.system.resource;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.yangc.bean.ResultBean;
import com.yangc.exception.WebApplicationException;
import com.yangc.system.bean.oracle.MenuTree;
import com.yangc.system.bean.oracle.TSysMenu;
import com.yangc.system.service.MenuService;
import com.yangc.utils.UserThreadUtils;

@Path("/menu")
public class MenuResource {

	public static final Logger logger = Logger.getLogger(MenuResource.class);

	private MenuService menuService;

	/**
	 * @功能: 显示顶层tab
	 * @作者: yangc
	 * @创建日期: 2012-9-10 上午12:00:10
	 * @return
	 */
	@POST
	@Path("showTopFrame")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showTopFrame(@FormParam("parentMenuId") Long parentMenuId) {
		try {
			Long userId = UserThreadUtils.get().getId();
			logger.info("showTopFrame - parentMenuId=" + parentMenuId + ", userId=" + userId);
			List<TSysMenu> menus = this.menuService.getTopFrame(parentMenuId, userId);
			return Response.ok(menus).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return WebApplicationException.build();
		}
	}

	/**
	 * @功能: 显示主页左侧和主页内容
	 * @作者: yangc
	 * @创建日期: 2012-9-10 上午12:00:10
	 * @return
	 */
	@POST
	@Path("showMainFrame")
	@Produces(MediaType.APPLICATION_JSON)
	public Response showMainFrame(@FormParam("parentMenuId") Long parentMenuId) {
		try {
			Long userId = UserThreadUtils.get().getId();
			logger.info("showMainFrame - parentMenuId=" + parentMenuId + ", userId=" + userId);
			List<TSysMenu> menus = this.menuService.getMainFrame(parentMenuId, userId);
			return Response.ok(menus).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return WebApplicationException.build();
		}
	}

	/**
	 * @功能: 根据parentMenuId获取菜单树
	 * @作者: yangc
	 * @创建日期: 2014年1月2日 下午4:04:09
	 * @return
	 */
	@POST
	@Path("getMenuTreeList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMenuTreeList(@FormParam("parentMenuId") Long parentMenuId) {
		logger.info("getMenuTreeList - parentMenuId=" + parentMenuId);
		try {
			List<MenuTree> menuTreeList = this.menuService.getMenuListByParentMenuId(parentMenuId);
			return Response.ok(menuTreeList).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return WebApplicationException.build();
		}
	}

	/**
	 * @功能: 添加菜单
	 * @作者: yangc
	 * @创建日期: 2014年1月2日 下午2:06:05
	 * @return
	 */
	@POST
	@Path("addOrUpdateMenu")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOrUpdateMenu(@FormParam("id") Long id, @FormParam("menuName") String menuName, @FormParam("menuUrl") String menuUrl, @FormParam("parentMenuId") Long parentMenuId,
			@FormParam("serialNum") Long serialNum, @FormParam("isshow") Long isshow, @FormParam("description") String description) {
		logger.info("addOrUpdateMenu - id=" + id + ", menuName=" + menuName + ", menuUrl=" + menuUrl + ", parentMenuId=" + parentMenuId + ", serialNum=" + serialNum + ", isshow=" + isshow
				+ ", description=" + description);
		ResultBean resultBean = new ResultBean();
		try {
			resultBean.setSuccess(true);
			if (id == null) {
				resultBean.setMessage("添加成功，请授权进行查看");
			} else {
				resultBean.setMessage("修改成功，请刷新页面进行查看");
			}
			this.menuService.addOrUpdateMenu(id, menuName, menuUrl, parentMenuId, serialNum, isshow, description);
			return Response.ok(resultBean).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return WebApplicationException.build();
		}
	}

	/**
	 * @功能: 修改所属父节点
	 * @作者: yangc
	 * @创建日期: 2014年1月2日 下午2:09:41
	 * @return
	 */
	@POST
	@Path("updParentMenuId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updParentMenuId(@FormParam("id") Long id, @FormParam("parentMenuId") Long parentMenuId) {
		logger.info("updParentMenuId - id=" + id + ", parentMenuId=" + parentMenuId);
		try {
			this.menuService.updParentMenuId(id, parentMenuId);
			return Response.ok(new ResultBean(true, "修改成功")).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return WebApplicationException.build();
		}
	}

	/**
	 * @功能: 删除菜单
	 * @作者: yangc
	 * @创建日期: 2014年1月2日 下午2:06:17
	 * @return
	 */
	@POST
	@Path("delMenu")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delMenu(@FormParam("id") Long id) {
		logger.info("delMenu - id=" + id);
		ResultBean resultBean = new ResultBean();
		try {
			this.menuService.delMenu(id);
			resultBean.setSuccess(true);
			resultBean.setMessage("删除成功");
			return Response.ok(resultBean).build();
		} catch (IllegalStateException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
			return Response.ok(resultBean).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return WebApplicationException.build();
		}
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
