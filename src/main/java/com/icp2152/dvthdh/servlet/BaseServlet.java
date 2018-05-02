package com.icp2152.dvthdh.servlet;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import java.util.ArrayList;
import java.util.HashMap;

import com.icp2152.dvthdh.Database;
import java.io.IOException;
import java.sql.SQLException;
import javafx.util.Pair;


/**
 * Base servlet which should extend all other servlets.
 *
 * @author DVT HDH
 */
abstract public class BaseServlet extends HttpServlet
{
	/**
	 * Messages types enum.
	 */
	protected enum MESSAGE_TYPE
	{
		MESSAGE_TYPE_SUCCESS("success"),
		MESSAGE_TYPE_INFO("info"),
		MESSAGE_TYPE_WARNING("warning"),
		MESSAGE_TYPE_DANGER("danger");


		private String type;


		MESSAGE_TYPE(String type)
		{
			this.type = type;
		}


		public String getType()
		{
			return type;
		}
	}


	/**
	 * List of messages.
	 */
	private ArrayList<Pair<String, String>> messages = new ArrayList<>();

	/**
	 * Database connection.
	 */
	private Database database;


	/**
	 * Returns name of template to be rendered.
	 *
	 * @return template name to be rendered
	 */
	abstract protected String getTemplateName();


	/**
	 * Returns page title.
	 *
	 * @return page title
	 */
	abstract protected String getPageTitle();


	/**
	 * Returns database connection.
	 *
	 * @return database connection
	 */
	final protected Database getDatabase()
	{
		if (database == null) {
			try {
				database = new Database();
			} catch (IOException | ClassNotFoundException | SQLException e) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database connection error.");
			}
		}

		return database;
	}


	/**
	 * Add new message.
	 *
	 * @param type type of message
	 * @param text text of message
	 */
	final protected void addMessage(MESSAGE_TYPE type, String text)
	{
		messages.add(new Pair<>(type.getType(), text));
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	}


	/**
	 * Servlet invocation startup.
	 *
	 * @param req HTTP request
	 * @param resp HTTP response
	 *
	 * @throws IOException in case of unsupported encoding
	 */
	@SuppressWarnings("unchecked")
	protected void startup(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		// set encoding
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		// initialize messages
		this.messages.clear();
		HttpSession session = req.getSession();
		Object messages = session.getAttribute("messages");
		if (messages != null) {
			this.messages = (ArrayList<Pair<String, String>>) ((ArrayList<Pair<String, String>>) messages).clone();
			session.setAttribute("messages", null);
		}
	}


	/**
	 * Servlet, before render action.
	 *
	 * @param req HTTP request
	 * @param resp HTTP response
	 */
	protected void beforeRender(HttpServletRequest req, HttpServletResponse resp)
	{
		req.setAttribute("messages", messages.clone());
		messages.clear();
		req.setAttribute("title", getPageTitle());
		req.setAttribute("servletName", getServletName());
		req.setAttribute("basePath", req.getContextPath());
		req.setAttribute("templateName", getTemplateName());
		req.setAttribute("user", getLoggedUser(req));
	}


	/**
	 * Servlet, render action. Basically renders JSP.
	 *
	 * @param req HTTP request
	 * @param resp HTTP response
	 *
	 * @throws ServletException if there is a problem with forward the request
	 * @throws IOException if there is a problem with forward the request
	 */
	protected void render(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("text/html");
		req.getRequestDispatcher("templates/@layout.jsp").forward(req, resp);
	}


	/**
	 * Servlet invocation shutdown.
	 *
	 * @param req HTTP request
	 * @param resp HTTP response
	 */
	protected void shutDown(HttpServletRequest req, HttpServletResponse resp)
	{
		// close database connection
		if (database != null) {
			try {
				database.close();
				database = null;
			} catch (Exception e) {
				addMessage(MESSAGE_TYPE.MESSAGE_TYPE_DANGER, "Database connection close error.");
			}
		}

		// store messages
		req.getSession().setAttribute("messages", this.messages.clone());
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	final protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		startup(req, resp);

		if (!resp.isCommitted()) {
			super.service(req, resp);
		}

		// invoke render method
		String method = req.getMethod();
		if (!resp.isCommitted() && (method.equals("GET") || method.equals("POST"))) {
			beforeRender(req, resp);
			render(req, resp);
		}

		shutDown(req, resp);
	}


	/**
	 * Returns logged in user or null if user is not logged in.
	 *
	 * @param req HTTP request
	 * @return logged in user or null if user is not logged in
	 */
	@SuppressWarnings("unchecked")
	final protected HashMap<String, Object> getLoggedUser(HttpServletRequest req)
	{
		Object user = req.getSession().getAttribute("user");

		return user == null ? null : (HashMap<String, Object>) user;
	}
}
