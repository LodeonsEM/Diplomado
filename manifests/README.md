# Tekton  
- Es una herramienta de código abierto diseñada para facilitar la construcción y el despliegue automatizado de software en entornos basados en contenedores.  
- Su enfoque principal es permitir la creación de pipelines declarativos y reutilizables, optimizando la automatización de tareas relacionadas con el desarrollo y la implementación de aplicaciones.  

## Elementos principales para la creación de una tarea en Tekton
### Step (Paso):
- Unidad básica para ejecutar comandos o scripts.
- Se ejecuta dentro de un contenedor.
### Task (Tarea):
- Conjunto ordenado de pasos.
- Se ejecuta como un Pod con un punto de entrada personalizado.
- Representa una abstracción reutilizable de un componente.
### Pipeline:
- Representa un grafo acíclico dirigido compuesto de tareas.
- Permite el intercambio de datos entre tareas a través de resultados y espacios de trabajo (volúmenes persistentes).
- Admite configuraciones avanzadas como reintentos, condiciones de ejecución y bloques "finalmente" para controlar el flujo.
### Workspace:
- Espacio de trabajo compartido del clúster.
### Results:
- Salida que comunica el resultado final de una tarea  específica.
## Ejemplo de ejecución de una tarea
#### En este caso, se mostrará un ejemplo de ejecución en Tekton que tiene como resultado el mensaje: Hello, World from Tekton!
1. Primero, se crea un archivo llamado "hello-world-task.yaml", el cual define la tarea encargada de mostrar el mensaje.
![image](https://github.com/user-attachments/assets/9373da38-74ef-45f2-9ff5-26a446ccc60d)

3. Se despliega la tarea definida en el archivo `hello-world-task.yaml` utilizando el siguiente comando:  
`kubectl apply -f hello-world-task.yaml -n diploe2-emm`

4. Se verifica que la tarea haya sido desplegada correctamente.
![image](https://github.com/user-attachments/assets/1dd2c70e-dc52-46e6-a391-507e9dc192c5)

5. Se crea un archivo llamado `hello-world-run.yaml`, que se encargará de ejecutar la tarea previamente configurada.
![image](https://github.com/user-attachments/assets/c2cd1b22-468e-4e6e-b31b-007bc889d47c)

6. Se despliega el TaskRun definido en `hello-world-run.yaml` con el comando:  
`kubectl create -f hello-world-run.yaml -n diploe2-emm`

7. Se comprueba que el TaskRun se haya ejecutado exitosamente.
![image](https://github.com/user-attachments/assets/b12f6dc4-d0e8-4759-afcd-1b8c947980c7)

8. Se verifica el pod generado durante la ejecución del TaskRun.
![image](https://github.com/user-attachments/assets/4a0150cf-4304-410b-affd-be0f1d063558)

9. Finalmente, se revisa el resultado de la tarea a través de los logs generados por el pod correspondiente.
![image](https://github.com/user-attachments/assets/46262016-0ea9-48e2-a7c1-12e7f950d891)

## Construcción de una Aplicación Java mediante Tareas de Tekton
En este ejemplo, se ilustrará el proceso de creación de una imagen de contenedor utilizando tareas de Tekton. El flujo contempla la descarga del código fuente desde un repositorio Git, la generación de un artefacto con Maven, la creación de la imagen Docker, y su posterior subida al repositorio DockerHub.

### Configuración Inicial: Clonación del Repositorio Git
1. Para iniciar, se instala en el namespace correspondiente la tarea `git-clone`, que permite descargar el código del repositorio, mediante el siguiente comando:
   ```
   kubectl apply -f https://api.hub.tekton.dev/v1/resource/tekton/task/git-cli/0.4/raw -n diploe2-emm
   ```
2. A continuación, se genera el manifiesto `git-clone.yaml`, el cual contendrá la configuración necesaria para ejecutar un `TaskRun` que descargará el código del repositorio Git especificado.
![image](https://github.com/user-attachments/assets/efec906f-5255-4a1b-8ee8-63fe67c2df41)

### Verificación del Contenido del Directorio

1. Una vez clonado el repositorio, se instalará la tarea `list-directory` en el namespace para listar el contenido del directorio clonado:
   ```
   kubectl apply -f https://raw.githubusercontent.com/redhat-scholars/tekton-tutorial/refs/heads/master/workspaces/list-directory-task.yaml -n diploe2-emm
   ```

2. Se verifica que la tarea haya sido correctamente instalada en el namespace de nombres.
![image](https://github.com/user-attachments/assets/48ec0255-5717-4f86-854f-d036bf9e2dff)

3. Posteriormente, se genera un manifiesto denominado `list-directory.yaml`, el cual ejecutará un `TaskRun` para mostrar los archivos contenidos en el directorio descargado.
![image](https://github.com/user-attachments/assets/0db9c360-a7c9-48fd-b45d-b138e5d414d4)

4. Se crea el `TaskRun` asociado al archivo de manifiesto generado, con el siguiente comando:
   ```
   kubectl create -f list-directory.yaml -n diploe2-emm
   ```

5. Se verifica que el `TaskRun` esté en ejecución y se revisan los pods generados por esta tarea.
![image](https://github.com/user-attachments/assets/2fed71a6-9a2f-400d-a6da-a810a892c6be)
![image](https://github.com/user-attachments/assets/85412a64-7702-4543-ad72-9efe020d2e7b)

6. Finalmente, se inspeccionan los logs generados por los pods para validar que los archivos del directorio han sido correctamente listados.
![image](https://github.com/user-attachments/assets/de053888-cad5-49b7-963c-bf6b1a478c8d)

### Construcción del Archivo .jar mediante Maven

1. Después de verificar el contenido del directorio, se instala la tarea `maven` en el namespace:
   ```
   kubectl apply -f https://api.hub.tekton.dev/v1/resource/tekton/task/maven/0.4/raw -n diploe2-emm
   ```

2. Se confirma que la tarea `maven` se encuentra registrada en el namespace.
![image](https://github.com/user-attachments/assets/5294423d-bf0a-4c72-823d-3fd7e78d1893)

3. A continuación, se genera el manifiesto `maven-taskrun.yaml`, que contendrá la configuración para construir el archivo `.jar` de la aplicación mediante Maven.
![image](https://github.com/user-attachments/assets/d441cc55-3b75-42c9-a1dd-3423968c6501)

4. Se inicia el `TaskRun` especificado en el manifiesto con el siguiente comando:
   ```
   kubectl create -f maven-taskrun.yaml -n diploe2-emm
   ```

5. Se verifica si el `TaskRun` está en ejecución y se revisan los pods creados por esta tarea.
![image](https://github.com/user-attachments/assets/d9f2fa76-fd57-40ce-b4cf-77524684ddb3)
![image](https://github.com/user-attachments/assets/b1cfa9b1-24ce-4f7e-95c2-51ec8ad96d11)

6. Finalmente, se inspeccionan los logs de los pods generados para confirmar que el archivo `.jar` ha sido construido exitosamente.
![image](https://github.com/user-attachments/assets/341c63f5-6b61-4493-8ab4-eaacfc83fb74)
![image](https://github.com/user-attachments/assets/955f5cd2-a051-434a-90e7-0c051163e771)

### Contenerización y Subida de la Imagen a DockerHub

1. Una vez creado el archivo `.jar`, se procederá a contenerizarlo mediante una imagen Docker y a subirla al repositorio DockerHub. Para esto, es necesario instalar la tarea `buildah` en el namespace:
   ```
   kubectl apply -f https://api.hub.tekton.dev/v1/resource/tekton/task/buildah/0.9/raw
   ```
2. Se verifica que la tarea `buildah` esté instalada en el namespace.
![image](https://github.com/user-attachments/assets/ba982ac0-4c55-4ca9-be7d-a26e0ae18315)

3. Se genera un nuevo manifiesto denominado `build-taskrun.yaml`, que contendrá las instrucciones para construir la imagen Docker y subirla al repositorio DockerHub.
![image](https://github.com/user-attachments/assets/c10a31f1-a1cb-4c69-91cb-a1a7c9829d16)

4. Además, es necesario generar un secreto (`secret`) en Kubernetes para autenticar las credenciales utilizadas en DockerHub al momento de subir la imagen.
![image](https://github.com/user-attachments/assets/f353dbc2-defe-4238-92ae-f6be01e19ff6)

5. Se ejecuta el siguiente comando para crear el `TaskRun` asociado al manifiesto generado:
   ```
   kubectl create -f build-taskrun.yaml -n diploe2-emm
   ```

6. Una vez más, se verifica que el `TaskRun` esté en ejecución y se revisan los logs de los pods generados por la tarea `buildah`.
![image](https://github.com/user-attachments/assets/269f2c8b-4391-4e4e-9d03-cd2ff72569a8)
![image](https://github.com/user-attachments/assets/2d6218ad-a860-493e-81d1-91d97e9e5dd6)

7. Finalmente, se confirma en DockerHub que la imagen del contenedor ha sido subida correctamente.
![image](https://github.com/user-attachments/assets/fd545f3a-9601-4e96-b674-5974cfaa1d84)

### Conclusión
Siguiendo este flujo detallado, se logra construir una aplicación Java completa contenerizada y alojada en DockerHub utilizando Tekton para orquestar las tareas correspondientes


