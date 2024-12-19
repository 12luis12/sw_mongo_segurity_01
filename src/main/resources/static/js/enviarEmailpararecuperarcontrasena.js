async function enviarEmaill() {
  const datos = {
    email: document.getElementById('email').value,
  };

  // Validar campos
  if (!datos.email) {
    alert("Por favor, introduce el email");
    return;
  }

  try {
    const response = await fetch('/user/solicitaRecuperar-contrasena', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    });

    if (response.ok) {
      const respuesta = await response.json();
      const correo = respuesta.correo;   // Extraer solo el correo

      // Guardar el correo en localStorage
      if (correo) {
        localStorage.setItem('correo', correo);

        console.log("Correo enviado a:", correo);
        window.location.href = `/url/mensaje-Exitoso`;
      }
    } else {
      if (response.status === 404) {
        alert("Error: El usuario no existe registrado ");
      } else if (response.status === 500) {
        alert("Error en el servidor. Por favor, intenta más tarde.");
      } else {
        const errorMessage = await response.text();
        alert("Error: " + errorMessage);
      }
    }
  } catch (error) {
    console.error("Error durante la solicitud de envío de correo: ", error);
    alert("Hubo un problema al intentar enviar el email. Inténtalo de nuevo.");
  }
}
