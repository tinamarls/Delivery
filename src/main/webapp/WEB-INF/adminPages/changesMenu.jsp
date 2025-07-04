<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>Меню</title>
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
        <div class="tables">
            <table>
                <tr>
                    <th></th>
                    <th>Фото</th>
                    <th>Имя блюда</th>
                    <th>Цена</th>
                    <th>Категория</th>
                    <th>Описание</th>
                    <th></th>
                </tr>

                <c:forEach items="${requestScope.products}" var="product">
                    <tr>
                        <td>
                            <form action="<c:url value="/admin/changes"/>" method="post">
                                <button type="submit" name="idDelete" value="${product.id}">Удалить</button>
                            </form>
                        </td>

                        <td>
                            <div class="photo-product">
                                <img src="${pageContext.request.contextPath}/storage/${product.photoNameFile}" alt="">
                            </div>
                        </td>

                        <td>${product.nameOfProduct}</td>
                        <td>${product.price}</td>
                        <td>${product.category}</td>
                        <td>${product.description}</td>
                        <td>
                            <form action="<c:url value="/admin/changes/product"/>" method="get">
                                <button type="submit" name="idChange" value="${product.id}">Изменить</button>
                            </form>
                        </td>

                    </tr>
                </c:forEach>

            </table>
        </div>
    </section>

</body>
</html>
