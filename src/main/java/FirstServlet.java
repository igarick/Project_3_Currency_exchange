import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.Cart;

import java.io.IOException;
import java.io.PrintWriter;

public class FirstServlet extends HttpServlet {

    //    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String user = (String) session.getAttribute("currency_user");

        if(user == null) {
            // response для анонимного пользователя
            // авторизация
            // регистрация
            // session.setAttribute("current_user", ID);
        } else {
            // response для авторизованного пользователя
        }

//        Cart cart = (Cart) session.getAttribute("cart");
//
//        String name = request.getParameter("name");
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//
//        if (cart == null) {
//            cart = new Cart();
//
//            cart.setName(name);
//            cart.setQuantity(quantity);
//        }

//        session.setAttribute("cart", cart);

//        PrintWriter pw = response.getWriter();
//
//        pw.println("<html>");
//        pw.println("<h1> Your count is: " + "</h1>");
//        pw.println("</html>");

        getServletContext().getRequestDispatcher("/showCart.jsp").
                forward(request, response);

        //-------------------------------------------------------------------------------
//        Сервлет обрабатывает бизнес-логику (например, создаёт Cart),
//                а JSP отвечает за отображение (View).
//
//👉 Это подход по принципу MVC:
//        Servlet = Контроллер
//        Cart = Модель
//        JSP = Представление

    }
}
