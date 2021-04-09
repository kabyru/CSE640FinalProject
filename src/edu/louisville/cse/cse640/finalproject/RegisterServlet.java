package edu.louisville.cse.cse640.finalproject;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.louisville.cse640.cotrollers.ConnectionPool;
import edu.louisville.cse640.cotrollers.UsersController;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private ConnectionPool				 pool			= null;
    private UsersController              uc				= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }
    
    private void connect2database()
    {
    	pool = ConnectionPool.getInstance("jdbc/COMPANY");
    	if (pool != null)
        {
    		dbConnection = pool.getConnection();
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
        String url = "/Login.jsp";
		String firstname = request.getParameter("firstname");
		String middlename = request.getParameter("middlename");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
        String password = request.getParameter("pw");
        if (firstname == null || firstname.length() == 0 || middlename == null || middlename.length() == 0 || lastname == null || lastname.length() == 0 || username == null || username.length() == 0 || password == null || password.length() == 0)
        {
            url = "/NewUser.jsp";
            request.setAttribute("error", "Please fill out the whole Register form!");
        }
        else
        {
            try
            {
                connect2database();
                if (uc.findUser(username) == true)
                {
                    request.setAttribute("fullname", uc.getFullName());
					url = "/NewUser.jsp";
            		request.setAttribute("error", "User already exists in system!");
                }
                else
                {
                    url = "/Login.jsp";
					int addUser = uc.insertUser(username,password,firstname,middlename,lastname,3);
					System.out.println(addUser);
					if (addUser == 1)
					{
						request.setAttribute("error", "User has been registered into the system.");
					}
					else
					{
						request.setAttribute("error", "Error adding user to the system!");
					}
                } // end if
                pool.freeConnection(dbConnection);
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