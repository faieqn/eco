<jsp:include page="private_header.jsp">
    <jsp:param name="jsfile" value="importCourses.js" />
</jsp:include>
<div>
    <div>
        <div>Importar desde ciclo</div>
        <div>
            <select id="sourceCycleList">
                <option value="empty">- Seleccione una fuente -</option>
            </select>
        </div>
    </div>
    <div>
        <div>Exportar hacia ciclo</div>
        <div>
            <select id="destCycleList"></select>
        </div>
    </div>
    <div>
        <input type="button" value="Importar" id="import" />
    </div>
    <div>
        <table>
            <thead>
                <tr>
                    <th>C&oacute;digo</th>
                    <th>Nombre</th>
                    <th>Secci&oacute;n</th>
                    <th>Profesor</th>
                    <th>D&iacute;a</th>
                    <th>Hora</th>
                </tr>
            </thead>
            <tbody id="courses">
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="private_footer.jsp" />