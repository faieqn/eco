<jsp:include page="private_header.jsp">
    <jsp:param name="jsfile" value="adminCourses.js" />
</jsp:include>
<h1>Administrar cursos</h1>

<table>
    <thead>
        <tr>
            <th>Estado</th>
            <th>C&oacute;digo</th>
            <th>Nombre del curso</th>
            <th>Sección</th>
            <th>Profesor</th>
            <th>Suscritos</th>
            <th>Conectados</th>
            <th>URI</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody id="courses">
    </tbody>
</table>
<jsp:include page="private_footer.jsp" />