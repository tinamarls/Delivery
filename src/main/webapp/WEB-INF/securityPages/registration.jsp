<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>

<html>
<head>
    <title>Регистрация</title>
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
                            <img src="${pageContext.request.contextPath}/storage/reg.jpg" alt=""
                                 style="border-radius: 1rem 0 0 1rem; height: 45.5rem" />
                        </div>
                        <div class="col-md-6 col-lg-7 d-flex align-items-center">
                            <div class="card-body p-4 p-lg-5 text-black">

                                <form action="<c:url value="/registration"/>" method="POST" id="regForm">

                                    <div class="d-flex align-items-center mb-3 pb-1">
                                        <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
                                        <span class="h1 fw-bold mb-0">Logo</span>
                                    </div>

                                    <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Регистрация</h5>

                                    <div class="form-outline mb-4">
                                        <input name="loginForReg" type="email" id="loginForReg" class="form-control form-control-lg" />
                                        <label class="form-label" for="loginForReg">Email</label>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <input name="passwordForReg" type="password" id="passwordForReg" class="form-control form-control-lg" />
                                        <label class="form-label" for="passwordForReg">Пароль</label>
                                    </div>

                                    <div class="pt-1 mb-4">
                                        <button class="btn btn-dark btn-lg btn-block" type="submit">Зарегестрироваться</button>
                                    </div>

                                </form>

                                <p class="mb-5 pb-lg-2" style="color: #393f81; font-size: 1.2rem;">
                                    <c:out value="${sessionScope.messageForEntry}"/>
                                </p>

                                <p style="color: #393f81; font-size: 1.2rem;">Есть аккаунт?
                                    <a href="<c:url value="/login"/>" style="color: #b85959;">Вход</a>
                                </p>

                                <p class="mb-5 pb-lg-2" style="color: #393f81; font-size: 1.2rem;">
                                    <a href="<c:url value="/"/>" style="color: #b85959;">Вернуться на главную</a></p>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script type="text/javascript" src="<c:url value="/js/index.js"/>"></script>

</body>

</html>

