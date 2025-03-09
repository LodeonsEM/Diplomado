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
1. Primero, se crea un archivo llamado `hello-world-task.yaml`, el cual define la tarea encargada de mostrar el mensaje.
![image](https://github.com/user-attachments/assets/9373da38-74ef-45f2-9ff5-26a446ccc60d)

2. Se despliega la tarea definida en el archivo `hello-world-task.yaml` utilizando el siguiente comando:  
   ```
   kubectl apply -f hello-world-task.yaml -n diploe2-emm`
   ```
3. Se verifica que la tarea haya sido desplegada correctamente.
![image](https://github.com/user-attachments/assets/1dd2c70e-dc52-46e6-a391-507e9dc192c5)

4. Se crea un archivo llamado `hello-world-run.yaml`, que se encargará de ejecutar la tarea previamente configurada.
![image](https://github.com/user-attachments/assets/c2cd1b22-468e-4e6e-b31b-007bc889d47c)

5. Se despliega el TaskRun definido en `hello-world-run.yaml` con el comando:  
   ```
   kubectl create -f hello-world-run.yaml -n diploe2-emm`
   ```
6. Se comprueba que el TaskRun se haya ejecutado exitosamente.
![image](https://github.com/user-attachments/assets/b12f6dc4-d0e8-4759-afcd-1b8c947980c7)

7. Se verifica el pod generado durante la ejecución del TaskRun.
![image](https://github.com/user-attachments/assets/4a0150cf-4304-410b-affd-be0f1d063558)

8. Finalmente, se revisa el resultado de la tarea a través de los logs generados por el pod correspondiente.
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

## Ejemplo de ejecucion de un Pipeline
1.	Primero, se crea un archivo llamado `task-echo.yaml`, el cual define la tarea encargada de mostrar el mensaje.
![image](https://github.com/user-attachments/assets/27ec9cce-0119-4494-9ab0-b9b0837f6afb)

2.	Se despliega la tarea definida en el archivo `task-echo.yaml ` utilizando el siguiente comando:  
   ```
   kubectl apply -f task-echo.yaml -n diploe2-emm
   ```
3.	crea un archivo llamado `tekton-pipeline-helloworld.yaml`, el cual define la pipeline encargada de utilizar la tarea de task-echo. 
![image](https://github.com/user-attachments/assets/81c88f69-4b7e-4009-973d-60fde9b5ea82)
![image](https://github.com/user-attachments/assets/709ca490-c6b2-4627-8118-aa2f65736dee)

4.	Se despliega el pipeline definido en el archivo `tekton-pipeline-helloworld.yaml` utilizando el siguiente comando:  
   ```
   kubectl apply -f tekton-pipeline-helloworld.yaml -n diploe2-emm
   ```
5.	Se verifica que la pipeline haya sido desplegada correctamente.
![image](https://github.com/user-attachments/assets/b1008d7b-cd2f-4a5a-a3b0-d03eacc82fb1)

6.	Se crea un archivo llamado `tekton-pipelinerun-helloworld.yaml`, que se encargará de ejecutar la pipeline previamente configurada.
![image](https://github.com/user-attachments/assets/d3c43ebe-a66f-4b69-ad28-768349109807)

7.	Se despliega el PipelineRun definido en `tekton-pipelinerun-helloworld.yaml` con el comando:  
   ```
   kubectl create -f tekton-pipelinerun-helloworld.yaml -n diploe2-emm
   ```
8.	Se verifica que el PipelineRun se haya ejecutado exitosamente.
![image](https://github.com/user-attachments/assets/d74af9b2-4b50-45da-a34d-296ecb6bc2b3)

9. Finalmente, se revisa el resultado de la tarea a través de los logs generados por el pod correspondiente.
![image](https://github.com/user-attachments/assets/7788efdf-72bd-40c6-b997-69b4cef0f2ae)

## Construcción de una Aplicación Java mediante Pipelines
1. Se reutilizaran las tareas `git-clone`, `maven` y `buildah` instalasdas previamente en la seccion de `Construcción de una Aplicación Java mediante Tareas de Tekton`
2. Crea un archivo llamado `pipeline-git-clone-package.yaml`, el cual define la pipeline encargada de utilizar las tareas: `git-clone`, `maven`, `buildah` y `kubernetes-action`.
![image](https://github.com/user-attachments/assets/7808eaa2-f45f-45be-b49d-8e759a75e7a4)
![image](https://github.com/user-attachments/assets/99545cd0-a2ff-4f34-80fd-ccf60ac3d694)
![image](https://github.com/user-attachments/assets/d78afc3a-1d84-4c59-99c1-c96aa3988764)
3. Se despliega el pipeline definido en el archivo ` pipeline-git-clone-package.yaml ` utilizando el siguiente comando:  
   ```
   kubectl apply -f pipeline-git-clone-package.yaml -n diploe2-emm
   ```
4. Se verifica que la pipeline haya sido desplegada correctamente.
![image](https://github.com/user-attachments/assets/e5be03d3-dc69-464c-89cd-0f54a469f989)
5. Se crea un archivo llamado ` pipelinerun-git-clone-package.yaml `, que se encargará de ejecutar la pipeline previamente configurada.
![image](https://github.com/user-attachments/assets/e75a2340-e2a9-4150-9c53-77a80e011469)
5. Se despliega el PipelineRun definido en ` pipelinerun-git-clone-package.yaml ` con el comando:  
   ```
   kubectl create -f pipelinerun-git-clone-package.yaml -n diploe2-emm
   ```
6. Se verifica que el PipelineRun se haya ejecutado exitosamente.
![image](https://github.com/user-attachments/assets/af078ab0-0dde-4384-957a-d6b8b6c03bf3)
7. Se verifica los pods generados por las task del pipeline
![image](https://github.com/user-attachments/assets/658fedbf-7910-4b7d-8760-c93e28e09e53)
8. Se verifica el pod generado por la taks fetch-repository
![image](https://github.com/user-attachments/assets/cff4a229-ef61-4adf-8ba1-251ddf6f4935)
9. Se verifica el pod generado por la taks maven
![image](https://github.com/user-attachments/assets/9ef3c61e-f01f-472d-a12d-6c1c40cc8c74)
![image](https://github.com/user-attachments/assets/dac43e54-e586-4095-9c83-7443d961db1d)
10. Se verifica el pod generado por la taks build
![image](https://github.com/user-attachments/assets/310121e9-f216-407e-a6c4-2f3af51f0eb4)
11. Se verifica el pod generado por la update-deployment
![image](https://github.com/user-attachments/assets/942e688e-4a73-4ffd-a44d-7c793f08ec7e)
12. Pod con la API-Producto en ejecución
![image](https://github.com/user-attachments/assets/95e335eb-c12b-48e4-a1af-544e35a2ee95)

## Construcción de una Aplicación Java mediante captura de eventos
1.  Se crea un archivol yaml que contiene un ServiceAccount y un RoleBinding donde:
- El ServiceAccount crea una cuenta de servicio para que Tekton Triggers interactúe con el clúster.
- El RoleBinding Asocia el ServiceAccount tekton-triggers-sa con un ClusterRole, esto otorga los permisos necesarios al EventListener de Tekton para escuchar y responder a eventos
![image](https://github.com/user-attachments/assets/f0782f08-3da8-4052-82e1-d55cae6d3433)
2. Se verifica si el namespace se encuentra en el clusterrolebinding
![image](https://github.com/user-attachments/assets/40dbd4a3-a04a-4f46-a8f6-13307807504b)
3. Se crea un TriggerTemplate para escuchar un evento y posteriormente crear automáticamente un PipilineRun con los parámetros recibidos y ejecutar el pipelinerun utilizando la pipeline `pipeline-git-clone-package` previamente creada
![image](https://github.com/user-attachments/assets/376adab0-fd92-4c19-941e-76a1532072ed)
![image](https://github.com/user-attachments/assets/b24b8ebd-f303-425d-adaa-a2d13c12bb12)
4.	Se despliega el archivo `TriggerTemplete.yam` utilizando el siguiente comando:  
   ```
   kubectl apply -f TriggerTemplete.yaml -n diploe2-emm
   ```
5. Verificar que se deplego el TriggerTemplate 
![image](https://github.com/user-attachments/assets/36892775-20d0-4fa0-b50d-1870f9f23d8e)
6. Se crea un TriggerBinding para capturar los datos de un webhook y posteriormente Asigna valores a parámetros para usar en un TriggerTemplate
7. Se crea un TriggerBinding para capturar los datos de un webhook y posteriormente asigna valores a parámetros para usar en un TriggerTemplate
![image](https://github.com/user-attachments/assets/b55fde1a-4a18-4dfa-821e-b8b40d35ed61)
8. Se despliega el archivo `TriggerBinding.yam` utilizando el siguiente comando
   ```
   kubectl apply -f `TriggerBinding.yaml` -n diploe2-emm
   ```
9. Verificar que se deplego el `TriggerBinding `
![image](https://github.com/user-attachments/assets/66d4490d-895f-458b-9e1d-7955627fb567)
10. Crea un EventListener para que actue como servidor HTTP que escucha eventos externos y orquesta la ejecución de pipelines CI/CD.
![image](https://github.com/user-attachments/assets/4d73c6a0-ca40-4e72-ae03-9bdc60993884)
11. Se despliega el archivo `EventListener.yam` utilizando el siguiente comando
   ```
   kubectl apply -f `EventListener.yaml` -n diploe2-emm
   ```
12. Verificar que se deplego el  `EventListener `
![image](https://github.com/user-attachments/assets/0630dd0c-ea08-4ec0-88e1-e8024d655d64)
13. Crear un  `Ingress` para exponer el EventListener de Tekton
![image](https://github.com/user-attachments/assets/a38155c2-1fe4-42ff-9201-25ff37d7be6a)
14. Realizar la siguiente configuración en el webhook en GitHub de nuestro proyecto que se comunica con el EventListener de Tekton en el cluster
![image](https://github.com/user-attachments/assets/cda08184-c47d-4603-9e6d-cb2e2907cfb5)
15. Realiza el despliegue de CI/CD actualizando el README.md del repositorio github y haciendo commit en el branch main
![image](https://github.com/user-attachments/assets/771f32e1-e9fa-4cd9-ba14-3dfe994aa463)
16. Revisar si en el webhooks lanzo el evento que capturara el cluster
![image](https://github.com/user-attachments/assets/acdd8e3b-6a0c-44e4-8a40-e0f13ddd5859)
17. Verifica la creacion del pipeline y pods, creados por el tiggerTemplete
![image](https://github.com/user-attachments/assets/7abd7efb-af2d-46ed-862e-a02e4923f60b)
18. Se verifica los pods creados por las task del pipeline
![image](https://github.com/user-attachments/assets/38e73c8c-b088-46d5-86e5-5f55495924ae)
19. Se verifica los logs de cada pod con los resultados de los task:
- Clonado del repositorio: https://github.com/LodeonsEM/Diplomado.git
![image](https://github.com/user-attachments/assets/1fb18a8e-a421-4107-9323-497deb69fc29)
- Creacion de imagen .JAR del aplicativo API-Producto
![image](https://github.com/user-attachments/assets/a7bbcc78-936d-45dc-8ad9-427b1724e362)
![image](https://github.com/user-attachments/assets/f10fb2e1-c171-44c8-b5bd-06b92ba90430)
- Creacion de imagen doker y subirla en el repositorio de dockerhub
![image](https://github.com/user-attachments/assets/16cc4742-5da9-4528-ab6a-e40d8512ddb0)
- Despliegue del Api-producto
![image](https://github.com/user-attachments/assets/e95e7595-2e37-479b-b509-173464a44226)
### Conclusión
Siguiendo este flujo detallado, se logra clonar un codigo del un repositorio y construir una aplicación Java completa contenerizada, alojada en DockerHub y posteriormente  desplegado la aplicacion utilizando Tekton para orquestar las tareas correspondientes


