<jsp:include page="private_header.jsp">
    <jsp:param name="jsfile" value="adminUsers.js" />
</jsp:include>

<h1>Administrar Usuarios</h1>
<table>
    <thead>
        <tr>
            <th>C&oacute;digo</th>
            <th>Usuario</th>
            <th>Nombre</th>
            <th>Profile</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody id="users">
    </tbody>
</table>

<jsp:include page="private_footer.jsp" />