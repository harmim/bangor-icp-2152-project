package com.icp2152.dvthdh.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Vocabulary list servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "VocabularyListServlet", urlPatterns = {"/vocabulary-list"})
public class VocabularyListServlet extends BaseServlet
{
	/**
	 * List of all vocabulary.
	 */
	private ArrayList<HashMap<String, Object>> vocabulary = new ArrayList<>();


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "vocabulary-list.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return "Vocabulary";
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
				"Only instructors and administrators are allowed to view vocabulary list."
			);
			resp.sendRedirect(req.getContextPath() + "/homepage");
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	{
		try {
			vocabulary = getDatabase().getAllFromTable("word");
			if (vocabulary.isEmpty()) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "There are no words.");
			}
		} catch (SQLException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void beforeRender(HttpServletRequest req, HttpServletResponse resp)
	{
		super.beforeRender(req, resp);
		req.setAttribute("vocabulary", vocabulary);
	}
}
