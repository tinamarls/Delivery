<%--suppress ELSpecValidationInJSP --%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>Менеджер</title>
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

    <section class="admin-product">
        <h1> Текущие заказы</h1>
        <div class="tables">
            <table>
                <tr>
                    <th>Номер заказа</th>
                    <th>Адрес</th>
                    <th>Информация о получателе</th>
                    <th>Комментарий</th>
                    <th>Продукты</th>
                    <th></th>
                </tr>

                <c:forEach items="${requestScope.orders.keySet()}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.address}</td>
                        <td>${order.nameOfClient}, ${order.phoneNumber}</td>
                        <td>${order.comment}</td>
                        <td>
                            <%--@elvariable id="orders" type="java"--%>
                            <c:forEach items="${orders.get(order)}" var="product">
                                <li>${product.nameOfProduct} x${product.count}</li>
                            </c:forEach>
                        </td>

                        <td>
                            <c:if test="${order.idCourier != 0}">
                                <p>Назначен курьер №${order.idCourier}</p>
                            </c:if>
                            <c:if test="${order.idCourier == 0}">
                                <form action="<c:url value="/admin"/>" method="post">

                                    <label for="select"></label>

                                    <select name="idCourier" id="select">

                                        <option>Выберете курьера</option>
                                        <c:forEach items="${requestScope.couriers}" var="courier">
                                            <option value="${courier.id}">Курьер №${courier.id}</option>
                                        </c:forEach>

                                    </select>

                                    <button type="submit" name="idOrder" value="${order.id}">Назначить курьера</button>
                                </form>
                            </c:if>

                        </td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </section>

    <section class="courier">
        <form action="<c:url value="/admin"/>" method="POST">
            <h2>Добавляем нового раба, хихи</h2>

            <label>
                <input type="email" name="emailForRegCourier" size="40" placeholder="Почта курьера" id="email">
            </label>

            <label>
                <input type="text" name="passwordForRegCourier" size="40" placeholder="Пароль курьера" id="login">
            </label>

            <div class="clearfix"></div>

            <label>
                <input type="text" name="nameOfCourier" size="40" placeholder="Имя" id="name">
            </label>

            <label>
                <input type="text" name="phoneNumber" size="40" placeholder="Номер телефона" id="phone">
            </label>

            <div class="clearfix"></div>

            <button type="submit" class="button" >Добавить курьера</button>
        </form>
    </section>

</body>
</html>
