<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E-mail Enviado</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .email-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
            text-align: center;
        }
        h2 {
            color: #4CAF50;
        }
        p {
            margin-bottom: 15px;
            font-size: 16px;
        }
        .email-address {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <h2>¡E-mail enviado!</h2>
        <p>Te hemos enviado un e-mail para restablecer tu contraseña a:</p>
        <p class="email-address" id="correo"></p>
        <p id="mensaje"></p>
        <p>Si no lo encuentras, revisa tu bandeja de spam.</p>
    </div>

    <script>
        // Obtén los valores de localStorage y colócalos en la página
        document.getElementById('correo').textContent = localStorage.getItem('correo') || "No se pudo obtener el correo.";
        document.getElementById('mensaje').textContent = localStorage.getItem('mensaje') || "No hay mensaje adicional.";
    </script>
</body>
</html>
