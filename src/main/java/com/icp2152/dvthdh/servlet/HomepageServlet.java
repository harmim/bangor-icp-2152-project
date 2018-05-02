package com.icp2152.dvthdh.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;


/**
 * Homepage servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "HomepageServlet", urlPatterns = {"/homepage"})
public class HomepageServlet extends BaseServlet
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "homepage.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void startup(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		super.startup(req, resp);

		if (getLoggedUser(req) == null) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "Please log in.");
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}
}
