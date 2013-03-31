<jsp:include page="header.jsp">
    <jsp:param name="jsfile" value="index.js"/>
</jsp:include>
<div>
    <div>
        <span>Iniciar sesión</span>
    </div>
    <div>
        <div>
            <div>Usuario</div>
            <div><input type="text" id="txtUsername" /></div>
        </div>
        <div>
            <div>Contraseña</div>
            <div><input type="password" id="txtPassword" /></div>
        </div>
        <div><input type="button" value="Ingresar" id="btLogin" /></div>
        <div><a href="recoveryPassword.jsp">Recuperar contrase&ntilde;a</a></div>
    </div>
</div>
<jsp:include page="footer.jsp" />