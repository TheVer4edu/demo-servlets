<%--
  Created by IntelliJ IDEA.
  User: thever4
  Date: 13.12.2021
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
</head>
<body>
<div class="container">
    <div class="user_form">
        <div class="title">
            <h1>Sign up now</h1>
        </div>
        <div class="files_body">
            <div class="files_list">
                <form method="POST">
                    <div class="label_input">
                        <label for="login_input">Your login</label>
                        <input id="login_input" type="text" name="login" placeholder="Enter your login" class="input" />
                    </div>

                    <div class="label_input">
                        <label for="email_input">Your Email</label>
                        <input id="email_input" type="text" name="email" placeholder="Enter your e-mail" class="input" />
                    </div>

                    <div class="label_input">
                        <label for="password_input">Your password</label>
                        <input id="password_input" type="password" name="password" placeholder="Enter your password" class="input" />
                    </div>

                    <button type="submit" class="button_grey">Submit form</button>
                    <a class="bottom_link" href="login">I have an account already</a>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
