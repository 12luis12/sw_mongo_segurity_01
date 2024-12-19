<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="/js/guardarCambioContrasena.js"></script>
    <title>Formulario de Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #BEBEBE; /* Color plomo */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .input-group {
            margin-bottom: 15px;
        }
        .input-group label {
            display: block;
            margin-bottom: 5px;
        }
        .input-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .login-btn {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 15px; /* Espacio entre el botón y el enlace */
        }
        .login-btn:hover {
            background-color: #45a049;
        }
        .forgot-password {
            margin-bottom: 15px; /* Espacio entre el enlace y el botón */
            text-align: center; /* Opcional: centrar el enlace */
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Cambio de contrasena</h2>
        <form action="/urlogin" method="POST">
        <input type="hidden" name="token" value="${token}">
            <div class="input-group">
                <label for="password">nuevo password</label>
                <input type="password" id="password" name="password" placeholder="Ingresa nuevo password" required>
            </div>
            <div class="input-group">
                <label for="repetirPassword">Repetir password:</label>
                <input type="password" id="repetirPassword" name="repetirPassword" placeholder="Ingresa tu contrasena" required>
            </div>
            
            <button type="button" class="login-btn" onclick="restablecerContrasena()">Restablecer contrasena</button>
        </form>
    </div>
</body>
</html>
