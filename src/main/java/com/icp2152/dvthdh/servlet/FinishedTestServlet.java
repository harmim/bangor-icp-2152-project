package com.icp2152.dvthdh.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.io.IOException;
import javafx.util.Pair;


/**
 * Finished test servlet.
 *
 * @author DVT HDH
 */
@WebServlet(name = "FinishedTestServlet", urlPatterns = {"/finished-test"})
public class FinishedTestServlet extends BaseServlet
{
	/**
	 * Final grade.
	 */
	private float grade;

	/**
	 * List of answers.
	 */
	private ArrayList<HashMap<String, Object>> answers = new ArrayList<>();


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTemplateName()
	{
		return "finished-test.jsp";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPageTitle()
	{
		return "Finished test";
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
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_WARNING, "Only students are allowed see finished test page.");
			resp.sendRedirect(req.getContextPath() + "/homepage");
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.sendRedirect(req.getContextPath() + "/test");
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	{
		String pattern = "q(\\d+)(\\p{Alpha}+)(\\d+)";
		grade = 0.f;
		answers = new ArrayList<>();

		try {
			Enumeration<String> parameterNames = req.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String parameterName = parameterNames.nextElement();
				Matcher matcher = Pattern.compile(pattern).matcher(parameterName);
				if (!matcher.matches()) {
					continue;
				}

				String questionType = matcher.group(2);
				int wordId = Integer.parseInt(matcher.group(3));

				HashMap<String, Object> word = getDatabase().getFirstRowByCriteria("word", "id", wordId);
				if (word == null) {
					continue;
				}

				String parameter = req.getParameter(parameterName);
				boolean correct = false;
				switch (questionType) {
					case "Gender":
						if (parameter.equals(word.get("welsh_gender"))) {
							correct = true;
						}
						break;

					case "English":
						if (parameter.equals(word.get("english"))) {
							correct = true;
						}
						break;

					case "Welsh":
						if (parameter.equals(word.get("welsh"))) {
							correct = true;
						}
						break;
				}

				if (correct) {
					grade += 1.f;
				}

				boolean finalCorrect = correct;
				answers.add(new HashMap<String, Object>() {{
					put("word", word);
					put("correct", finalCorrect);
					put("questionType", questionType);
					put("value", parameter);
				}});
			}
		} catch (SQLException e) {
			addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
			return;
		}

		HashMap<String, Object> user = getLoggedUser(req);
		String time = req.getParameter("time");
		if (!answers.isEmpty() && user != null && time != null) {
			try {
				getDatabase().insert("test", new ArrayList<Pair<String, Object>>() {{
					add(new Pair<>("user_id", user.get("id")));
					add(new Pair<>("mark", grade));
					add(new Pair<>("test_date", new Timestamp(Long.parseLong(time))));
				}});
			} catch (SQLIntegrityConstraintViolationException e) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Test is already saved in database.");
			} catch (SQLException e) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database access error.");
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void beforeRender(HttpServletRequest req, HttpServletResponse resp)
	{
		super.beforeRender(req, resp);
		req.setAttribute("grade", grade);
		req.setAttribute("answers", answers);
	}
}
