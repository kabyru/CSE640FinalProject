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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private DatabaseConnectionController dcc			= null;
    private UsersController              uc				= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
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
        // System.out.println(request.getContextPath());
        String url = "/Welcome.jsp";
        String user = request.getParameter("user");
        String password = request.getParameter("pw");
        if (user == null || user.length() == 0)
        {
            url = "/Login.jsp";
            request.setAttribute("error", "User name must not be empty");
        }
        else
        {
            try
            {
                connect2database();
                if (uc.findUser(user, password) == true)
                {
                    request.setAttribute("fullname", uc.getFullName());
                    HttpSession session = request.getSession();
                    if (session != null)
                    {
                    	session.setAttribute("user",user);
                    }
                    if (session == null)
                    {
                    	System.out.println("Session is null!");
                    }
                }
                else
                {
                    url = "/Login.jsp";
                    request.setAttribute("error", "Invalid credentials entered!");
                } // end if
                dcc.disconnectFromDatabase();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            } // end try catch
        } // end if
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
