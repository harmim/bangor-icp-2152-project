package com.icp2152.dvthdh.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


/**
 * Delete word servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "DeleteWordServlet", urlPatterns = {"/delete-word"})
public class DeleteWordServlet extends BaseServlet
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

		if (!user.get("role").equals("instructor") && !user.get("role").equals("administrator")) {
			addMessage(
				MESSAGE_TYPE.MESSAGE_TYPE_WARNING,
				"Only instructors and administrators are allowed to delete words."
			);
			resp.sendRedirect(req.getContextPath() + "/homepage");
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		int wordId;
		try {
			wordId = Integer.parseInt(req.getParameter("wordId"));
		} catch (NumberFormatException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid word ID.");
			resp.sendRedirect(req.getContextPath() + "/vocabulary-list");
			return;
		}

		try {
			if (getDatabase().delete("word", "id", wordId) > 0) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_SUCCESS, "Word id: " + wordId + " has been successfully deleted.");
			}
		} catch (SQLException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
		}

		resp.sendRedirect(req.getContextPath() + "/vocabulary-list");
	}
}
