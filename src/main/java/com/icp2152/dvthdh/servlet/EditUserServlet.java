package com.icp2152.dvthdh.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.util.ArrayList;
import java.util.HashMap;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.io.IOException;
import javafx.util.Pair;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Edit user servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "EditUserServlet", urlPatterns = {"/edit-user"})
public class EditUserServlet extends BaseServlet
{
	/**
	 * Edited user.
	 */
	private HashMap<String, Object> user;


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "edit-user.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return "Edit user";
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
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "Only administrators are allowed to edit users.");
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

		try {
			user = getDatabase().getFirstRowByCriteria("user", "id", userId);
			if (user == null) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid user ID.");
				resp.sendRedirect(req.getContextPath() + "/users-list");
			}
		} catch (SQLException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
			resp.sendRedirect(req.getContextPath() + "/users-list");
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
			password = req.getParameter("password"),
			role = req.getParameter("role");

		int userId;
		try {
			userId = Integer.parseInt(req.getParameter("userId"));
		} catch (NumberFormatException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid user ID.");
			resp.sendRedirect(req.getContextPath() + "/users-list");
			return;
		}

		try {
			user = getDatabase().getFirstRowByCriteria("user", "id", userId);
			if (user == null) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Invalid user ID.");
				resp.sendRedirect(req.getContextPath() + "/users-list");
				return;
			}
		} catch (SQLException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
			resp.sendRedirect(req.getContextPath() + "/users-list");
			return;
		}

		if (username == null || role == null) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Please enter all required fields.");
			return;
		}

		try {
			getDatabase().update("user", "id", userId, new ArrayList<Pair<String, Object>>() {{
				add(new Pair<>("username", username));
				if (password != null) {
					add(new Pair<>("password", BCrypt.hashpw(password, BCrypt.gensalt())));
				}
				add(new Pair<>("role", role));
			}});

			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_SUCCESS, "User ID: " + userId + " has been successfully updated.");
			resp.sendRedirect(req.getContextPath() + "/users-list");
		} catch (SQLIntegrityConstraintViolationException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "User with entered username already exists.");
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
		req.setAttribute("user", user);
	}
}
