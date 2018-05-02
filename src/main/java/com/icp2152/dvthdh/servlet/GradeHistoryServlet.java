package com.icp2152.dvthdh.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.util.ArrayList;
import java.util.HashMap;

import java.sql.SQLException;
import java.io.IOException;


/**
 * Grade history servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "GradeHistoryServlet", urlPatterns = {"/grade-history"})
public class GradeHistoryServlet extends BaseServlet
{
	/**
	 * User's tests.
	 */
	private ArrayList<HashMap<String, Object>> tests = new ArrayList<>();

	/**
	 * Student to who this tests belong to.
	 */
	private HashMap<String, Object> student;


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "grade-history.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return "Grade history";
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


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> user;
		String userIdParam = req.getParameter("userId");

		if (userIdParam != null) {
			int userId;
			try {
				userId = Integer.parseInt(userIdParam);
			} catch (NumberFormatException e) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid student ID.");
				resp.sendRedirect(req.getContextPath() + "/homepage");
				return;
			}

			try {
				user = getDatabase().getFirstRowByCriteria("user", "id", userId);
				if (user == null) {
					addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid student ID.");
					resp.sendRedirect(req.getContextPath() + "/homepage");
					return;
				}
			} catch (SQLException e) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
				return;
			}
		} else {
			user = getLoggedUser(req);
			if (user == null) {
				return;
			}

			if (!user.get("role").equals("student")) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid student ID.");
				resp.sendRedirect(req.getContextPath() + "/homepage");
				return;
			}
		}

		student = user;

		try {
			tests = getDatabase().getUsersTests(Math.toIntExact((long) user.get("id")));
			if (tests.isEmpty()) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "There are no grades.");
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
		req.setAttribute("tests", tests);
		req.setAttribute("student", student);
	}
}
