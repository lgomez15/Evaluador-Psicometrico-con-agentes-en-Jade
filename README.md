# PsycometricAgent

En powershell ejecutar los siguientes comandos:
cd src: Entrar en el fichero src del proyecto.
javac agents/*.java Compila todos los ficheros de src.
Start-Process Powershell;  Start-Process Powershell; Lanza dos terminales mas, para el cliente y el servicio.
java jade.Boot -gui Lanza el gui de JADE.
java jade.Boot -container Client:agents.Client Lanza el cliente.
java jade.Boot -container PsycometricService:agents.PsycometricService Lanza el servidor.
En la consola del cliente responder a las preguntas solicitadas y esperar respuesta del servicio.
