<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro Exitoso</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .message-container {
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
        .email {
            color: #00BFFF; /* Color celeste */
            font-weight: bold; /* Negrita para mayor énfasis */
        }
    </style>
</head>
<body>
    <div class="message-container">
        <h2>¡Registro Exitoso!</h2>
        <p id="mensaje" class="email"></p> <!-- Mensaje sobre el registro con clase para color celeste -->
        <p>Para terminar de registrarte, verifica tu correo.</p> <!-- Indicación de verificación -->
        <p>Si no lo encuentras, revisa tu bandeja de spam.</p>
    </div>

    <script>
        // Obtén el mensaje de localStorage y colócalo en la página
        document.getElementById('mensaje').textContent = localStorage.getItem('mensaje') || "No hay mensaje adicional.";
    </script>
</body>
</html>
