<%--suppress ALL --%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>Додо пицца</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">


</head>

<html>
<body>

    <section class="header">

        <a href="#" class="logo">
            <img src="${pageContext.request.contextPath}/storage/dodo_icon.png" alt="">
        </a>

        <nav class="navbar">
            <a href="#about">О нас</a>
            <a href="#pizza">Пицца</a>
            <a href="#snacks">Закуски</a>
            <a href="#drinks">Напитки</a>
            <a href="#desert">Десерты</a>
            <a>            </a><a>            </a><a>            </a><a>            </a><a>            </a>

            <c:if test="${sessionScope.user != null}">
                <c:if test="${sessionScope.role.equals('client')}">
                    <a href="<c:url value="/account"/>">
                        <img src="${pageContext.request.contextPath}/storage/account.png" alt="" height="29" width="29"/>
                    </a>
                </c:if>
                <c:if test="${sessionScope.role.equals('admin')}">
                    <a href="<c:url value="/admin"/>">
                        <img src="${pageContext.request.contextPath}/storage/account.png" alt="" height="29" width="29"/>
                    </a>
                </c:if>
                <c:if test="${sessionScope.role.equals('courier')}">
                    <a href="<c:url value="/courier"/>">
                        <img src="${pageContext.request.contextPath}/storage/account.png" alt="" height="29" width="29"/>
                    </a>
                </c:if>

            </c:if>

            <c:if test="${sessionScope.user == null}">
                <a href="<c:url value="/login"/>">
                    <img src="${pageContext.request.contextPath}/storage/login.png" alt="" height="27" width="27"/>
                </a>
            </c:if>

            <a href="<c:url value="/bag"/>">
                <img src="${pageContext.request.contextPath}/storage/cart.png" alt="" height="29" width="29"/>
            </a>

            <c:if test="${sessionScope.user != null}">
                <a href="<c:url value="/"/>">
                    <form action="/" method="post">
                        <button type="submit" name="exit" value="true"><img src="${pageContext.request.contextPath}/storage/exit.png" alt="" height="27" width="27"/></button>
                    </form>
                </a>
            </c:if>

        </nav>

        <div id="menu-btn" class="fas fa-bars"></div>

    </section>

    <section class="about" id="about">

        <div class="image">
            <img src="${pageContext.request.contextPath}/storage/about.jpg" alt="">
        </div>

        <div class="content">
            <h3 class="title">Сеть пиццерий № 1 в России</h3>
            <p>Обычно люди приходят в Додо Пиццу, чтобы просто поесть. Но для нас Додо — не только пицца.
                Это и пицца тоже, но в первую очередь это большое дело, которое вдохновляет нас,
                заставляет каждое утро просыпаться и с интересом продолжать работу.</p>
            <div class="icons-container">
                <div class="icons">
                    <img src="${pageContext.request.contextPath}/storage/about-icon-1.png" alt="">
                    <h3>Вкусная еда</h3>
                </div>
                <div class="icons">
                    <img src="${pageContext.request.contextPath}/storage/about-icon-2.png" alt="">
                    <h3>Свежие продукты</h3>
                </div>
                <div class="icons">
                    <img src="${pageContext.request.contextPath}/storage/about-icon-3.png" alt="">
                    <h3>Лучшие повара</h3>
                </div>
            </div>
        </div>

    </section>


    <section class="meals" id="meals">

        <div class="heading-cat" id="pizza">
            <h3>Пицца</h3>

        </div>

        <div class="meal-container">

            <c:forEach items="${pizza}" var="product">

                <div class="meal">

                    <div class="op" data-name="food-${product.getId()}">
                        <img src="${pageContext.request.contextPath}/storage/${product.getPhotoNameFile()}" alt="">
                        <h3>${product.getNameOfProduct()}</h3>
                        <div class="price">${product.getPrice()}₽</div>
                    </div>

                    <form method="post" action="<c:url value="/"/>">
                        <button class="add" type="submit" name="idForProduct" value="${product.getId()}">Добавить в корзину</button>
                    </form>
                </div>

            </c:forEach>

        </div>

        <div class="clearfix"></div>

        <div class="heading-cat" id="snacks">
            <h3>Закуски</h3>
        </div>

        <div class="meal-container">

            <c:forEach items="${snacks}" var="product">

                <div class="meal">

                    <div class="op" data-name="food-${product.getId()}">
                        <img src="${pageContext.request.contextPath}/storage/${product.getPhotoNameFile()}" alt="">
                        <div class="icon"> <i class="fas fa-plus"></i> </div>
                        <h3>${product.getNameOfProduct()}</h3>
                        <div class="price">${product.getPrice()}₽</div>
                    </div>

                    <form method="post" action="<c:url value="/"/>">
                        <button class="bag" type="submit" name="idForProduct" value="${product.getId()}">Добавить в корзину</button>
                    </form>
                </div>

            </c:forEach>

        </div>

        <div class="clearfix"></div>

        <div class="heading-cat" id="drinks">
            <h3>Напитки</h3>
        </div>

        <div class="meal-container">

            <c:forEach items="${drinks}" var="product">

                <div class="meal">

                    <div class="op" data-name="food-${product.getId()}">
                        <img src="${pageContext.request.contextPath}/storage/${product.getPhotoNameFile()}" alt="">
                        <div class="icon"> <i class="fas fa-plus"></i> </div>
                        <h3>${product.getNameOfProduct()}</h3>
                        <div class="price">${product.getPrice()}₽</div>
                    </div>

                    <form method="post" action="<c:url value="/"/>">
                        <button class="bag" type="submit" name="idForProduct" value="${product.getId()}">Добавить в корзину</button>
                    </form>
                </div>

            </c:forEach>

        </div>

        <div class="clearfix"></div>

        <div class="heading-cat" id="desert">
            <h3>Десерты</h3>
        </div>

        <div class="meal-container">

            <c:forEach items="${deserts}" var="product">

                <div class="meal">

                    <div class="op" data-name="food-${product.getId()}">
                        <img src="${pageContext.request.contextPath}/storage/${product.getPhotoNameFile()}" alt="">
                        <div class="icon"> <i class="fas fa-plus"></i> </div>
                        <h3>${product.getNameOfProduct()}</h3>
                        <div class="price">${product.getPrice()}₽</div>
                    </div>

                    <form method="post" action="<c:url value="/"/>">
                        <button class="bag" type="submit" name="idForProduct" value="${product.getId()}">Добавить в корзину</button>
                    </form>
                </div>

            </c:forEach>

        </div>

    </section>

    <section class="food-preview-container">

        <div id="close-preview" class="fas fa-times">X</div>

        <c:forEach items="${products}" var="product">

            <div class="food-preview" data-target="food-${product.getId()}">
                <img src="${pageContext.request.contextPath}/storage/${product.getPhotoNameFile()}" alt="">
                <h3>${product.getNameOfProduct()}</h3>
                <p>${product.getDescription()}</p>
                <div class="price">${product.getPrice()}₽</div>
            </div>

        </c:forEach>

    </section>

    <script type="text/javascript" src="/js/index.js"></script>

</body>

</html>

