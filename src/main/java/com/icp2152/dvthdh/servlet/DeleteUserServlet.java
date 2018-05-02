package com.icp2152.dvthdh.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


/**
 * Delete user servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/delete-user"})
public class DeleteUserServlet extends BaseServlet
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return null;
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

		HashMap<String, Object> user = getLoggedUser(req);
		if (user == null) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "Please log in.");
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (!user.get("role").equals("administrator")) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "Only administrators are allowed to delete users.");
			resp.sendRedirect(req.getContextPath() + "/homepage");
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		int userId;
		try {
			userId = Integer.parseInt(req.getParameter("userId"));
		} catch (NumberFormatException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid user ID.");
			resp.sendRedirect(req.getContextPath() + "/users-list");
			return;
		}

		HashMap<String, Object> loggedUser = getLoggedUser(req);
		if (loggedUser != null && Math.toIntExact((long) loggedUser.get("id")) == userId) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "You can not delete yourself.");
		} else {
			try {
				if (getDatabase().delete("user", "id", userId) > 0) {
					addMessage(
						MESSAGE_TYPE.MESSAGE_TYPE_SUCCESS,
						"User id: " + userId + " has been successfully deleted."
					);
				}
			} catch (SQLException e) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
			}
		}

		resp.sendRedirect(req.getContextPath() + "/users-list");
	}
}
