<jsp:include page="private_header.jsp">
    <jsp:param name="jsfile" value="configureCourses.js" />
</jsp:include>

<h1>Configurar cursos</h1>

<div class="tabs">
    <ul>
        <li><a href="#course"></a></li>
        <li><a href="#cycle"></a></li>
        <li><a href="#section"></a></li>
    </ul>
    <div id="course">
        <div>
            <div>
                <div>Curso</div>
                <div>
                    <select id="courseList">
                        <option value="new">- Nuevo -</option>
                    </select>
                </div>
            </div>
            <div>
                <div>C&oacute;digo</div>
                <div><input type="text" /></div>
            </div>
            <div>
                <div>Nombre</div>
                <div><input type="text" /></div>
            </div>
            <div>
                <input type="button" text="Guardar" />
            </div>
        </div>
    </div>
    <div id="cycle">
        <div>
            <div>
                <div>Ciclo</div>
                <div>
                    <select id="cycleList">
                        <option value="new">- Nuevo -</option>
                    </select>
                </div>
            </div>
            <div>
                <div>Nombre</div>
                <div><input type="text" /></div>
            </div>
            <div>
                <div>Periodo</div>
                <div>
                    <select id="periodList"></select>
                </div>
            </div>
            <div>
                <div>A&ntilde;o</div>
                <div>
                    <select id="cycleYear"></select>
                </div>
            </div>
            <div>
                <input type="button" text="Guardar" />
            </div>
        </div>
    </div>
    <div id="section">
        <div>
            <div>
                <div>Secci&oacute;n</div>
                <div>
                    <select id="sectionList">
                        <option value="new">- Nuevo -</option>
                    </select>
                </div>
            </div>
            <div>
                <div>Nombre</div>
                <div><input type="text" /></div>
            </div>
            <div>
                <input type="button" text="Guardar" />
            </div>
        </div>
    </div>
</div>

<jsp:include page="private_footer.jsp" />