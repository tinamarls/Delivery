<%--suppress ELSpecValidationInJSP --%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>Курьерская работенка</title>
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
            <a href="<c:url value="/courier"/>">Рабочая страница</a>

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

    <c:if test="${requestScope.status.equals('отдыхает')}">

        <section class="admin-product">
                <form action="<c:url value="/courier"/>" method="post">
                    <button type="submit" class="admin-product" name="work" value="work">Начать смену</button>
                </form>
        </section>

    </c:if>


    <c:if test="${requestScope.status.equals('работает')}">
        <section class="admin-product">
            <h1>${sessionScope.message}</h1>
            <h2> Текущие заказы</h2>
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
                                <form action="<c:url value="/courier"/>" method="post">
                                    <button type="submit" name="finish" value="${order.id}">Done</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <form action="<c:url value="/courier"/>" method="post">
                <button type="submit" name="work" value="relax">Завершить смену</button>
            </form>

        </section>



    </c:if>
</body>
</html>
