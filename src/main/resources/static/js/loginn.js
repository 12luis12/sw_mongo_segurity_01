async function iniciarSesion() {
  const datos = {
    email: document.getElementById('email').value,
    password: document.getElementById('password').value
  };

  // Validar campos
  if (!datos.email || !datos.password) {
    alert("Por favor, completa todos los campos.");
    return;
  }

  try {
    const response = await fetch('/user/login', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    });

    if (response.ok) {
      const respuesta = await response.json();
      const token = respuesta.tokenJWT;
      localStorage.setItem('token', token);
      localStorage.setItem('email', datos.email);

      // Redirigir al usuario
      window.location.href = '/url/registrarUsuario';
    } else {
      // Manejo de errores específicos
      if (response.status === 401) {
        alert("Credenciales incorrectas. Inténtalo de nuevo.");
      } else if (response.status === 500) {
        alert("Error en el servidor. Por favor, intenta más tarde.");
      } else {
        const errorMessage = await response.text();
        alert("Error: " + errorMessage);
      }
    }
  } catch (error) {
    console.error("Error during login: ", error);
    alert("Hubo un problema al intentar iniciar sesión. Inténtalo de nuevo.");
  }
}

//------------------------------------------------------
