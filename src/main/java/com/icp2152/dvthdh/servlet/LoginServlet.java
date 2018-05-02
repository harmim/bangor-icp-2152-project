package com.icp2152.dvthdh.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


/**
 * Login servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends BaseServlet
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "login.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return "Login";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void startup(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		super.startup(req, resp);

		if (getLoggedUser(req) != null) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_INFO, "You are already logged in.");
			resp.sendRedirect(req.getContextPath() + "/homepage");
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		if (req.getParameter("submit") == null) {
			return;
		}

		String username = req.getParameter("username"),
			password = req.getParameter("password");

		if (username == null || password == null) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Please enter all required fields.");
			return;
		}

		try {
			HashMap<String, Object> user = getDatabase().getFirstRowByCriteria("user", "username", username);
			if (user == null || !BCrypt.checkpw(password, (String) user.get("password"))) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid credentials.");
			} else {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_SUCCESS, "You have been successfully logged in.");

				user.remove("password");
				req.getSession().setAttribute("user", user);
				resp.sendRedirect(req.getContextPath() + "/homepage");
			}
		} catch (SQLException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
		}
	}
}
