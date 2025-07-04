<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>Добавление продукта</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">

</head>

<html>
<body>

    <section class="header">
        <a href="<c:url value="/"/>" class="logo">
            <img src="${pageContext.request.contextPath}/storage/dodo_icon.png" alt="">
        </a>

        <nav class="navbar">
            <a href="<c:url value="/admin#orders"/>">Контроль заказов</a>
            <a href="<c:url value="/admin#courier"/>">Добавление персонала</a>
            <a href="<c:url value="/admin/changes"/>">Изменение меню</a>
            <a href="<c:url value="/admin/changes/add"/>">Добавление новой позиции</a>
            <c:if test="${sessionScope.user != null}">
                <a href="<c:url value="/"/>">
                    <form action="<c:url value="/"/>" method="post">
                        <button type="submit" name="exit" value="true"><img src="${pageContext.request.contextPath}/storage/exit.png" alt="" height="27" width="27"/></button>
                    </form>
                </a>
            </c:if>
            <a>            </a><a>            </a><a>            </a><a>            </a><a>            </a>
        </nav>

        <div id="menu-btn" class="fas fa-bars"></div>
    </section>

    <div class="clearfix"></div>

    <section class="change-product">
        <form method="post" action="<c:url value="/admin/changes/add"/>" enctype="multipart/form-data">

            <h1>${sessionScope.messageForAddProduct}</h1>

            <h2>Добавляем новую позицию в меню</h2>

            <label>
                <input type="text" name="nameOfProduct" size="40" placeholder="Название позиции" id="nameOfProduct">
            </label>

            <div class="clearfix"></div>

            <label>
                <input type="text" name="category" size="40" placeholder="Категория" id="category">
            </label>

            <div class="clearfix"></div>

            <label>
                <input type="text" name="price" size="40" placeholder="Цена" id="price">
            </label>

            <div class="clearfix"></div>

            <label>
                <input class="desc" type="text" name="desc" size="80" placeholder="Описание" id="desc">
            </label>

            <div class="clearfix"></div>

            <input type="file" name="photo">

            <button type="submit">загрузить</button>
        </form>
    </section>


</body>

</html>

