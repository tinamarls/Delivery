<%--suppress ELSpecValidationInJSP --%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>Личный кабинет</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">

</head>

<html>
<body>

    <section class="header">

        <a href="<c:url value="/"/>" class="logo">
            <img src="${pageContext.request.contextPath}/storage/dodo_icon.png" alt="">
        </a>

        <nav class="navbar">
            <a href="<c:url value="/"/>">Меню</a>

            <a>            </a><a>            </a><a>            </a><a>            </a><a>            </a>

            <a href="<c:url value="/account"/>">
                <img src="${pageContext.request.contextPath}/storage/account.png" alt="" height="29" width="29"/>
            </a>

            <a href="<c:url value="/bag"/>">
                <img src="${pageContext.request.contextPath}/storage/cart.png" alt="" height="29" width="29"/>
            </a>


            <a href="<c:url value="/"/>">
                <form action="<c:url value="/"/>" method="post">
                    <button type="submit" name="exit" value="true"><img src="${pageContext.request.contextPath}/storage/exit.png" alt="" height="27" width="27"/></button>
                </form>
            </a>

        </nav>

        <div id="menu-btn" class="fas fa-bars"></div>

    </section>

    <section class="client" id="client">

        <div class="heading">
            <span>Аккаунт</span>
            <h3>Личные данные</h3>
        </div>

        <form method="post" action="<c:url value="/account"/>">
            <div class="box-container">
                <div class="box">
                        <div class="inputBox">
                            <span>Имя: ${requestScope.client.nameOfClient} </span>
                            <label for="nameOfProduct"></label><input type="text" name="name" size="40" placeholder="Имя" id="nameOfProduct">
                            <button type="submit" class="button" >Изменить</button>
                        </div>

                    <div class="clearfix"></div>
                    <div class="inputBox">
                        <span>Номер телефона: ${requestScope.client.numberOfPhone}</span>
                        <label for="phone"></label><input type="text" name="phone" size="40" placeholder="Номер телефона" id="phone">
                        <button type="submit" class="button" >Изменить</button>
                    </div>
                    <div class="clearfix"></div>
                    <div class="inputBox">
                        <span>Дата рождения: ${requestScope.client.dateOfBirth}</span>
                        <label for="date"></label><input type="date" name="date" size="40" placeholder="Дата рождения" id="date">
                        <button type="submit" class="button" >Изменить</button>
                    </div>
                </div>

                <div class="box">
                    <div class="inputBox">
                        <div class="price">Ваши додокоины: ${requestScope.client.bonuses}</div>
                    </div>

                </div>

            </div>

        </form>

    </section>




    <section class="store-1" id="store">

        <div class="heading">
            <span>История заказов</span>
            <h3>Ваши заказы за все время</h3>
        </div>

        <div class="swiper home-slider">
            <div class="swiper-wrapper">
                <div class="swiper-slide all">
                    <h3 class="title">Текущие заказы</h3>
                    <div class="bag-cont">
                        <c:forEach items="${requestScope.preparingOrders.keySet()}" var="order">
                            <div class="one">
                                <div class="info">
                                    <h3>Номер заказа: №${order.id}</h3>
                                    <p>Блюда:
                                        <%--@elvariable id="preparingOrders" type="java"--%>
                                        <c:forEach items="${preparingOrders.get(order)}" var="product">
                                            ${product.nameOfProduct} x${product.count},
                                        </c:forEach>
                                    </p>
                                </div>
                                <div class="price">Сумма заказа: ${order.fullPrice}₽</div>
                            </div>
                        </c:forEach>
                    </div>

                </div>

                <div class="swiper-slide all">
                    <h3 class="title">Выполненные заказы</h3>
                    <div class="bag-cont">
                        <c:forEach items="${requestScope.doneOrders.keySet()}" var="order">
                            <div class="one">
                                <div class="info">
                                    <h3>Номер заказа: №${order.id}</h3>
                                    <p>Блюда:
                                        <%--@elvariable id="doneOrders" type="java"--%>
                                        <c:forEach items="${doneOrders.get(order)}" var="product">
                                            ${product.nameOfProduct} x${product.count},
                                        </c:forEach>
                                    </p>
                                </div>
                                <div class="price">Сумма заказа: ${order.fullPrice}₽</div>
                            </div>
                        </c:forEach>
                    </div>

                </div>
            </div>

        </div>

    </section>

</body>
</html>


