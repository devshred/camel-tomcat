<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Ordershredr</title>
        <style type="text/css">
            table.gridtable {
                font-family: verdana,arial,sans-serif;
                font-size:11px;
                color:#333333;
                border: 1px #666666;
                border-collapse: collapse;
            }
            table.gridtable th {
                padding: 8px;
                border: 1px solid #666666;
                background-color: #dedede;
            }
            table.gridtable td {
                padding: 8px;
                border: 1px solid #666666;
                background-color: #ffffff;
            }
        </style>
    </head>
    <body>
        <a href="/A">Express!</a>&nbsp;<a href="/B">Click!</a>&nbsp;<a href="/C">Click!</a>
        <br/><br/>
        <a href="/order/1">Order 1</a>&nbsp;<a href="/order/2">Order 2</a>&nbsp;<a href="/order/test">Order test</a>
        <c:choose>
            <c:when test="${empty products}">
                No products found.
            </c:when>
            <c:otherwise>
                <table class="gridtable">
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td><b>${product.id}</b></td>
                            <td><a href="/order/${product.id}">order <i>${product.name}</i></a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </body>
</html>