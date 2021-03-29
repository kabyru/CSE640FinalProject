package edu.louisville.cse.cse640.finalproject;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.louisville.cse640.cotrollers.DatabaseConnectionController;
import edu.louisville.cse640.cotrollers.UsersController;

/**
 * Servlet implementation class GoBackToWelcomeServlet
 */
@WebServlet("/GoBackToWelcomeServlet")
public class GoBackToWelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection            dbConnection	= null;
    private DatabaseConnectionController dcc			= null;
    private UsersController              uc				= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoBackToWelcomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void connect2database()
    {
        dcc = new DatabaseConnectionController("COMPANY");
        if (dcc != null)
        {
            dbConnection = dcc.getDbConnection();
            if (dbConnection == null)
            {
                System.out.println("Connection Failed");
            }
            else
            {
                uc = new UsersController(dbConnection);
            } // end if
        }
        else
        {
            System.out.println("Driver Failed");
        }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String url = "./Welcome.jsp";
		String userName = "";
		HttpSession session = request.getSession(true);
		if (session != null)
		{
			userName = (String) session.getAttribute("user");
			request.setAttribute("username", userName);
		}
		if (session == null)
		{
			System.out.println("Session is null!");
		}
		try
		{
			connect2database();
			//At this point, use the userName to find the fullName
			if (uc.findUser(userName) == true)
			{
				request.setAttribute("fullname", uc.getFullName());
			}
			dcc.disconnectFromDatabase();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
