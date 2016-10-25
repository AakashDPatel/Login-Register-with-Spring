<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form action="./login.html" method="post" id="loginForm">
            <table>
                <tr>
                    <td colspan="2" align="center">Login</td>
                </tr>
                <tr>
                    <td>
                        Username
                    </td>
                    <td>
                        <input type="text" name="username" id="username" value='<%=request.getParameter("username") == null ? "" : request.getParameter("username")%>'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Password
                    </td>
                    <td>
                        <input type="password" name="password" id="password"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="color: red;">
                        ${error}
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        New User? <a href="./register.html">Register</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
                        <!--<input type="submit" value="Login"/>-->
                        <input type="reset" value="Clear"/>
                        <input type="button" value="Login" onclick="validateLogin()"/>
                    </td>
                </tr>
            </table>
        </form>
        <script type="text/javascript" src="./js/jquery-3.1.0.min.js"></script>
        <script type="text/javascript" src="./js/app.js"></script>
    </body>
</html>
