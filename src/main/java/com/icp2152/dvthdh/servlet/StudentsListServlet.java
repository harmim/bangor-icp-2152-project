package com.icp2152.dvthdh.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Students list servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "StudentsListServlet", urlPatterns = {"/students-list"})
public class StudentsListServlet extends BaseServlet
{
	/**
	 * List of all students.
	 */
	private ArrayList<HashMap<String, Object>> students = new ArrayList<>();


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "students-list.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return "Students";
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

		if (!user.get("role").equals("instructor")) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "Only instructors are allowed to view students list.");
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
			students = getDatabase().getRowsByCriteria("user", "role", "student");
			if (students.isEmpty()) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "There are no students.");
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
		req.setAttribute("students", students);
	}
}
