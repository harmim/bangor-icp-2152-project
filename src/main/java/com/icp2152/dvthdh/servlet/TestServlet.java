package com.icp2152.dvthdh.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Test servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class TestServlet extends BaseServlet
{
	/**
	 * Number of words to generate.
	 */
	final private static int NUMBER_OF_WORDS = 20;


	/**
	 * Generated words.
	 */
	private ArrayList<HashMap<String, Object>> words = new ArrayList<>();

	/**
	 * Random words Array list.
	 */
	private ArrayList<HashMap<String, Object>> randomWords = new ArrayList<>();


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "test.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return "Test";
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

		if (!user.get("role").equals("student")) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "Only students are allowed to take tests.");
			resp.sendRedirect(req.getContextPath() + "/homepage");
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	{
		try {
			words = getDatabase().getAllFromTable("word");
			Collections.shuffle(words);

			randomWords = (ArrayList<HashMap<String, Object>>) words.clone();
			Collections.shuffle(words);

			if (words.size() > NUMBER_OF_WORDS) {
				words.subList(NUMBER_OF_WORDS - 1, words.size() - 1).clear();
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
		req.setAttribute("words", words);
		req.setAttribute("randomWords", randomWords);
		req.setAttribute("numberOfWords", NUMBER_OF_WORDS);
	}
}
