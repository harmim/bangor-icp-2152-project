package com.icp2152.dvthdh.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.util.ArrayList;
import java.util.HashMap;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import javafx.util.Pair;


/**
 * Add user servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "AddUserServlet", urlPatterns = {"/add-user"})
public class AddUserServlet extends BaseServlet
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "add-user.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return "Add user";
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
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "Only administrators are allowed to add users.");
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
			password = req.getParameter("password"),
			role = req.getParameter("role");

		if (username == null || password == null || role == null) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Please enter all required fields.");
			return;
		}

		try {
			getDatabase().insert("user", new ArrayList<Pair<String, Object>>() {{
				add(new Pair<>("username", username));
				add(new Pair<>("password", BCrypt.hashpw(password, BCrypt.gensalt())));
				add(new Pair<>("role", role));
			}});

			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_SUCCESS, "Word has been successfully added.");
			resp.sendRedirect(req.getContextPath() + "/users-list");
		} catch (SQLIntegrityConstraintViolationException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "User with entered username already exists.");
		} catch (SQLException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
		}
	}
}
