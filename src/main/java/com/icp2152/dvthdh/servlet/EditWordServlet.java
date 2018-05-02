package com.icp2152.dvthdh.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;
import java.sql.SQLException;
import javafx.util.Pair;


/**
 * Edit word servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "EditWordServlet", urlPatterns = {"/edit-word"})
public class EditWordServlet extends BaseServlet
{
	/**
	 * Edited word.
	 */
	private HashMap<String, Object> word;


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "edit-word.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return "Edit word";
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
				"Only instructors and administrators are allowed to edit words."
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
			word = getDatabase().getFirstRowByCriteria("word", "id", wordId);
			if (word == null) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid word ID.");
				resp.sendRedirect(req.getContextPath() + "/vocabulary-list");
			}
		} catch (SQLException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
			resp.sendRedirect(req.getContextPath() + "/vocabulary-list");
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

		String english = req.getParameter("english"),
			welsh = req.getParameter("welsh"),
			gender = req.getParameter("gender");

		int wordId;
		try {
			wordId = Integer.parseInt(req.getParameter("wordId"));
		} catch (NumberFormatException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid word ID.");
			resp.sendRedirect(req.getContextPath() + "/vocabulary-list");
			return;
		}

		try {
			word = getDatabase().getFirstRowByCriteria("word", "id", wordId);
			if (word == null) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid word ID.");
				resp.sendRedirect(req.getContextPath() + "/vocabulary-list");
				return;
			}
		} catch (SQLException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
			resp.sendRedirect(req.getContextPath() + "/vocabulary-list");
			return;
		}

		if (english == null || welsh == null || gender == null) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Please enter all required fields.");
			return;
		}

		try {
			getDatabase().update("word", "id", wordId, new ArrayList<Pair<String, Object>>() {{
				add(new Pair<>("english", english));
				add(new Pair<>("welsh", welsh));
				add(new Pair<>("welsh_gender", gender));
			}});

			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_SUCCESS, "Word id: " + wordId + " has been successfully updated.");
			resp.sendRedirect(req.getContextPath() + "/vocabulary-list");
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
		req.setAttribute("word", word);
	}
}
