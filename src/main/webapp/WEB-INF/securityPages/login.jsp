<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>

<html>
<head>
    <title>Вход в личный аккаунт</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
</head>


<body style="background-color: #d7a57d;">

    <section class="vh-100">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col col-xl-10">
                    <div class="card" style="border-radius: 1rem;">
                        <div class="row g-0">
                            <div class="col-md-6 col-lg-5 d-none d-md-block">
                                <img src="${pageContext.request.contextPath}/storage/login.jpg" alt=""
                                     style="border-radius: 1rem 0 0 1rem; height: 45.5rem" />
                            </div>
                            <div class="col-md-6 col-lg-7 d-flex align-items-center">
                                <div class="card-body p-4 p-lg-5 text-black">

                                    <form action="<c:url value="/login"/>" method="POST" id="loginForm">

                                        <div class="d-flex align-items-center mb-3 pb-1">
                                            <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
                                            <span class="h1 fw-bold mb-0">Dodo</span>
                                        </div>

                                        <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Вход в аккаунт</h5>

                                        <div class="form-outline mb-4">
                                            <input name="loginForAuth" type="email" id="loginForAuth" class="form-control form-control-lg" />
                                            <label class="form-label" for="loginForAuth">Email</label>
                                        </div>

                                        <div class="form-outline mb-4">
                                            <input name="passwordForAuth" type="password" id="passwordForAuth" class="form-control form-control-lg" />
                                            <label class="form-label" for="passwordForAuth">Пароль</label>
                                        </div>

                                        <div class="pt-1 mb-4">
                                            <button class="btn btn-dark btn-lg btn-block" type="submit">Войти</button>
                                        </div>

                                    </form>

                                    <p class="mb-5 pb-lg-2" style="color: #393f81; font-size: 1.2rem;">
                                        <c:out value="${sessionScope.messageForEntry}"/>
                                    </p>

                                    <p style="color: #393f81; font-size: 1.2rem;">Нет аккаунта?
                                        <a href="<c:url value="/registration"/>" style="color: #b85959;">Регистрация</a>
                                    </p>

                                    <p class="mb-5 pb-lg-2" style="color: #393f81; font-size: 1.2rem;">
                                        <a href="<c:url value="/registration"/>" style="color: #b85959;">Вернуться на главную</a></p>

                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

</body>

</html>

