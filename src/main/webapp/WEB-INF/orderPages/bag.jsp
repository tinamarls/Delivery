<%--suppress ELSpecValidationInJSP --%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Корзина</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/swiper-bundle.min.css">

    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>

<body>

<section class="bag" id="bag">

    <div class="heading">
        <span>Ваша корзина</span>
        <h3>список товаров</h3>
    </div>

    <div class="all">
        <h3 class="title">Корзина</h3>
        <div class="bag-cont">
            <c:forEach items="${sessionScope.allProducts.keySet()}" var="product"><%--@elvariable id="Integer" type="java"--%>
            <%--@elvariable id="allProducts" type="java"--%>

                <div class="one">
                    <div class="info">
                        <h3>${product.getNameOfProduct()}</h3>
                        <p>${product.getDescription()}</p>
                    </div>
                    <div class="change">
                        <form method="post" action="<c:url value="/bag"/>">
                            <button type="submit" name="minus" value="${product.getId()}">
                                <img src="${pageContext.request.contextPath}/storage/minus1.png" alt="" height="24" width="24"/>
                            </button>
                        </form>
                        <p style="font-size: 2.8rem">  ${allProducts.get(product)}  </p>
                        <form method="post" action="<c:url value="/bag"/>">
                            <button type="submit" name="plus" value="${product.getId()}">
                                <img src="${pageContext.request.contextPath}/storage/plus1.png" alt="" height="24" width="24"/>
                            </button>
                        </form>
                    </div>
                    <div class="price">${Integer.valueOf(product.getPrice())*allProducts.get(product)}₽</div>
                    <form method="post" action="<c:url value="/bag"/>">
                        <button type="submit" name="delete" value="${product.getId()}">
                            <img src="${pageContext.request.contextPath}/storage/delete.png" alt="" height="28" width="26"/>
                        </button>
                    </form>
                </div>

            </c:forEach>

            <div class="one">
                <c:if test="${requestScope.client != null}">
                    <form action="<c:url value="/order"/>" method="get">
                        <div class="inform">
                            <h3>Ваши Додокоины: ${requestScope.client.getBonuses()}</h3>
                        </div>

                        <div class="switch-bonus">
                            <input type="checkbox" id="switch" name="useBonuses" value="${requestScope.client.getId()}"/>
                            <label for="switch">Toggle</label>
                        </div>

                        <c:if test="${sessionScope.allProducts.size() != 0}">
                            <button type="submit" name ="products"  class="btn btn-primary btn-block btn-lg">
                                <div class="d-flex justify-content-between">
                                    <span>Перейти к оформлению заказа : ${sessionScope.fullPrice}₽</span>
                                </div>
                            </button>
                        </c:if>
                </form>
                </c:if>

                <c:if test="${requestScope.client == null}">
                    <form action="<c:url value="/login"/>" method="get">

                        <button type="submit" name ="products"  class="btn btn-primary btn-block btn-lg">
                            <div class="d-flex justify-content-between">
                                <span>Войдите в аккаунт, чтобы оформить заказ: ${sessionScope.fullPrice}₽</span>
                            </div>
                        </button>

                    </form>
                </c:if>

                <c:if test="${requestScope.client != null}">

                    <c:if test="${sessionScope.allProducts.size() == 0}">
                        <form action="<c:url value="/"/>" method="get">

                            <button type="submit" name ="products"  class="btn btn-primary btn-block btn-lg">
                                <div class="d-flex justify-content-between">
                                    <span>Выберете продукты, чтобы оформить заказ</span>
                                </div>
                            </button>

                        </form>
                    </c:if>

                </c:if>

            </div>

        </div>
    </div>

</section>

</body>
</html>
