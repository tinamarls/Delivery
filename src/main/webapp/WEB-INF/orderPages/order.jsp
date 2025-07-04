<%--suppress ELSpecValidationInJSP --%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Оформление заказа</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/swiper-bundle.min.css">

</head>

<head>
    <title>Оформление заказа</title>
</head>

<body>

    <section class="order" id="order">

        <div class="heading">
            <span>Закажи сейчас</span>
            <h3>Оформление заказа</h3>
        </div>

        <form method="post" action="<c:url value="/order"/>">
            <div class="box-container">
                <div class="box">
                    <div class="inputBox">
                        <span>${sessionScope.messageForOrder}</span>
                    </div>
                    <div class="inputBox">
                        <span>Имя</span>
                        <input name="name" type="text" placeholder="Укажите ваше имя">
                    </div>
                    <div class="inputBox">
                        <span>Номер телефона</span>
                        <input name="phone" type="text" placeholder="УКажите номер телефона">
                    </div>
                    <div class="inputBox">
                        <span>Адрес доставки</span>
                        <input name="address" type="text" placeholder="Укажите адрес доставки">
                    </div>
                    <div class="inputBox">
                        <span>Комментарий</span>
                        <label for=""></label><textarea name="comment" placeholder="Комментарий/пожелания" id="" cols="30" rows="10"></textarea>
                    </div>
                </div>

                <div class="box">
                    <div class="inputBox">
                        <h3>Ваш заказ</h3>
                        <c:forEach items="${sessionScope.allProducts.keySet()}" var="product">
                            <li>${product.nameOfProduct}</li>
                        </c:forEach>
                        <p></p>
                        <div class="price">Total price: ${sessionScope.fullPrice}₽</div>
                        <p></p>
                    </div>

                </div>

            </div>

            <input type="submit" value="Оформить заказ" class="btn">

        </form>


    </section>


</body>
</html>
