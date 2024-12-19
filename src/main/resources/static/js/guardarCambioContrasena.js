async function restablecerContrasena() {
    const password = document.getElementById("password").value;
    const repetirPassword = document.getElementById("repetirPassword").value;
    const token = document.querySelector("input[name='token']").value;

    // Verificar que ambas contraseñas coincidan
    if (password !== repetirPassword) {
        alert("Las contraseñas no coinciden");
        return;
    }

    // Crear el objeto con los datos a enviar
    const data = {
        password: password,
        token: token
    };

    try {
        // Hacer la solicitud POST
        const response = await fetch("/user/procesar-restablecimiento", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        // Verificar si la respuesta fue exitosa
        if (response.ok) {
            // Redirigir a la página de éxito
            window.location.href = "/url/formularioLogin";
        } else {
                      
            const errorMessage = await response.text();
            alert("Error: " + errorMessage);
             }
    } catch (error) {
        alert("Error al procesar la solicitud. Por favor, intenta nuevamente.");
        console.error("Error:", error);
    }
}