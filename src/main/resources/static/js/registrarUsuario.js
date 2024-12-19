async function registroUsuarios() {
    // Obtener los datos del formulario
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const repetirContrasena = document.getElementById('repetir-contrasena').value;

    // Validar que las contraseñas coincidan
    if (password !== repetirContrasena) {
        alert('Las contraseñas no coinciden');
        return;
    }

    // Crear el objeto con los datos del formulario
    const usuario = {
        nombre: nombre,
        apellido: apellido,
        email: email,
        password: password
    };

    try {
        // Enviar la solicitud de registro de forma asincrónica
        const response = await fetch('/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuario)
        });

        // Manejar la respuesta
        if (response.ok) {
            // Obtener la respuesta como texto plano
            const respuestaTexto = await response.text();
            const mensaje = respuestaTexto; // Aquí asumimos que el mensaje completo es la respuesta

            // Guardar el mensaje en localStorage (no es necesario el correo ya que viene en el mensaje)
            localStorage.setItem('mensaje', mensaje);

            console.log("Mensaje almacenado:", mensaje);

            // Redireccionar a la página de mensaje exitoso
            window.location.href = '/url/mensaje-Registra'; // Cambia esta URL según sea necesario
        } else {
            // Capturar el mensaje de error como texto
            const errorMessage = await response.text();
            alert("Error: " + errorMessage); // Muestra el mensaje de error
        }

        // Limpiar el formulario después del registro
        document.getElementById('registro-form').reset();

    } catch (error) {
        // Manejar errores en la solicitud fetch
        console.error('Error en la solicitud de registro:', error);
    }
}
