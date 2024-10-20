# [←](./README.md) <a id="home"></a> Docker installation (Windows)

## Table of Contents:
- Windows
    - [Enable WSL2](#wsl2)
    - [Kali Linux Installation](#kali)
    - [WSL integration](#integration)
- [Docker installation](#docker)
- [Docker Client (Windows)](#cli)
- [First Run](#first)


## [↑](#home) <a id="wsl2"></a> Enable WSL2
Docker основывается на функциях Linux, а следовательно даже работая на Windows нам нужны эти функции, т.е. нужен Linux.
Удобнее всего будет использовать **WSL**.

**WSL** - это **Windows Subsystem for Linux**, т.е. подсистема для поддержки Linux для Windows.\
Использование **WSL** позволит не использовать **Docker Desktop** и избежать вопросов с его лицензией.\
Установим WSL согласно официально инструкции: **"[How to install Linux on Windows with WSL](https://learn.microsoft.com/en-us/windows/wsl/install)"**.

**WSL** позволяет установить виртуальные дистрибутивы Linux и работать с ними.\
Список установленных дистрибутивов (если такие есть) можно увидеть при помощи команды **list**:
> wsl --list

Если есть установленные дистрибутивы, то один из них будет помечен как **"(Default)"**.\
Каждый раз когда выполнена команда **wsl** без параметров, то будет открыт именно этот дистрибутив.

Дистрибутив по умолчанию можно задать при помощи команды **setdefault**:
> wsl --setdefault DISTRO-NAME

При необходимости дистрибутив можно удалить при помощи команды **unregister**:
> wsl --unregister DISTRO-NAME

Список доступных комманд всегда можно посмотреть запросив помощь:
> wsl --help

После установки имеет смысл обновить WSL:
> wsl --update

----

## [↑](#home) <a id="kali"></a> Kali Linux Installation
Для начала, установим дистрибутив **[Kali Linux](https://www.kali.org/docs/wsl/wsl-preparations/)**:
> wsl --install --distribution kali-linux

**Kali Linux** - это специальный дистрибутив Linux на основе **Debian Linux**.\
Плюс этого дистрибутива для wsl в том, что он меньше, чем тот же Ubuntu.

Кроме того, для удалённого подключения с UI к Kali Linux есть утилита - **[win-kex](https://www.kali.org/docs/wsl/win-kex/)**.\
Это может существенно упростить процесс, т.к. с тем же Ubuntu могут возникнуть проблемы, даже если выполнять один и тот же подход, но с разными версиями wsl и Ubuntu.

После установки при помощи ``wsl --list`` можно проверить, появился ли новый установленные дистрибутив в списке установленных дистрибутивов.\
Кроме этого, можно вывести расширенную информацию при помощи ``wsl --list --verbose`` (или ``wsl -l -v``).\
У дистрибутива по умолчанию стоит знак ``*``.

Если он дистрибутив по умолчанию, то к нему можно подключаться при помощи команды ``wsl``, а если нет, то при помощи ``wsl -d <название дистрибутива>``.\
Дистрибутив по умолчанию можно задать при помощи команды **setdefault**:
> wsl --setdefault DISTRO-NAME

После установки дистрибутива и подключения к нему стоит обновить доступные в дистрибутиве пакеты:
> sudo apt update
sudo apt-get upgrade

Чтобы закончить настройку wsl нам нужно настроить **[systemd](https://learn.microsoft.com/en-us/windows/wsl/systemd)**, управляющий сервисами Linux.\
Для начала, проверим содержимое файла, если таковой есть: ``cat /etc/wsl.conf``.\
Содержимое файла должно быть таким:
>[boot]
systemd=true

В случае, если файла нет или содержимое неверно, выполним команду ``sudo nano /etc/wsl.conf`` и отредактируем данный файл.\
Для сохранения изменений нажмём ``CTRL + O``, а для выхода ``CTRL + X``.

**systemd** позволит нам в будущем управлять службами. Например, позволит запускать установленный докер при старте Kali Linux дистрибутива.

⚠ **ВАЖНО:** ⚠ \
wsl дистрибутив автоматически останавливается, если к нему больше нет подключений.\
Изнутри wsl можно использовать команду ``exit``, чтобы завершить текущую сессию.\
При помощи ``wsl -l -v`` можно увидеть статус дистрибутивов.\
При помощи ``wsl --list --running`` можно увидеть список запущенных дистрибутивов.\
Так же можно использовать команду ``wsl -t ИмяДистрибутива`` чтобы остановить конкретный дистрибутив.

Для удобства, на Windows хосте можно выполнять команду ``start wsl``.\
Команда ``start /min wsl`` позволит запустить сессию wsl и при этом свернуть окно.\
Это позволит запустить сессию с WSL в отдельном процессе и вернуться в текущую командную строку.

----

## [↑](#home) <a id="integration"></a> WSL integration
Интеграция между WSL и Windows хостом для разных аспектов имеет разную степень работоспособности.

Например, IDE **VS Code** имеет интеграцию с WSL, т.к. тоже является детищем Microsoft.\
Подробнее см. **"[Developing in WSL](https://code.visualstudio.com/docs/remote/wsl)"**.

VS Code для работы с WSL использует плагин: **[Visual Studio Code WSL](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-wsl)**.\
Демонстрация: **"[How To Run Linux Code on Windows with WSL 2 & VS Code](https://youtu.be/bRW5r7TK6KM?si=vYtWBm0aQtBjDm0M&t=368)"**.

С **IntelliJ Idea** сложнее, т.к. она разрабатывается не Microsoft.\
Существует проблема, из-за которой IntelliJ Idea не может работать с WSL: **https://github.com/microsoft/WSL/issues/8995**

Интересно, что UI приложения, запущенные в WSL будут открыты в UI Windows хоста.\
Например: можно выполнить ``wsl xeyes`` или ``wsl xclock``. 

Аналогичным образом будут работать любые UI приложения (такие, как IDE вроде IntelliJ Idea) изнутри WSL.\
Например, можно установить **JetBrains Toolbox**: https://www.jetbrains.com/help/idea/installation-guide.html#toolbox

При помощи команды ``sudo ./jetbrains-toolbox`` можно запустить ToolBox.\
В окне ToolBox'а мы уже можем непосредственно выполнить установку на WSL машину нужных JetBrains IDE.

В случае, если с отображением какие-то проблемы, имеет смысл попробовать обновить WSL:
> wsl --update

Файловая система WSL доступна из Windows как ресурс, то работать с файлами можно используя файловый менеджер Windows.\
Подробнее можно прочитать в [блоге Microsoft](https://devblogs.microsoft.com/commandline/whats-new-for-wsl-in-windows-10-version-1903/).

Таким образом, файл можно скопировать из Windows в WSL вручную.\
Но только в каталог ``\\wsl.localhost\kali-linux\home\<пользователь>``.

Альтернативный вариант - скопировать этот файл куда удобно изнутри самой WSL.\
Для этого в самой WSL выполним:
> sudo apt-get update
apt install doublecmd-gtk

Это позволит установить **Double Commander**, который можно открывать из WSL:
> sudo doublecmd

При помощи Double Commander можно скопировать файл из Windows (начинается с ``/mount/c/``) в рекомендуемый каталог ``/opt/``.\
Кроме того, через контекстное меню Double Commander'а можно распаковать архив (сделать ``extract here``).

----

## [↑](#home) <a id="docker"></a> Docker installation
Итак, пришло время установить **Docker**.\
Установка **Docker** на Linux описана в **[инструкции на официальном сайте](https://docs.docker.com/engine/install/#supported-platforms)**.

В случае установки Docker'а на Kali Linux есть некоторые нюансы.\
Хотя **Kali Linux** является дистрибутивом на основе Debian, но установка **Docker** выполняется немного иначе.\
Описание установки Docker для Kali описано в документации: **"[Installing Docker on Kali Linux](https://www.kali.org/docs/containers/installing-docker-on-kali/)"**.

Чтобы проверить, что Docker запущен, в wsl запросим версию Docker'а:
> docker --version

После установки нужно не забыть добавить докер в автостарт (см. **"[Configure Docker to start on boot with systemd](https://docs.docker.com/engine/install/linux-postinstall/#configure-docker-to-start-on-boot-with-systemd)"**):
> sudo systemctl enable docker.service
sudo systemctl enable containerd.service

----

## [↑](#home) <a id="cli"></a> Docker Client (for Windows)
Для работы с Docker Engine (back-end часть докера) используется **Docker Client**.

Docker Client по умолчанию становится во время установки Docker'а. Но его можно скачать отдельно из **binaries** файлов.\
Ссылка на документацию: **[Install client binaries on Windows](https://docs.docker.com/engine/install/binaries/#install-server-and-client-binaries-on-windows)**.

Докер клиент нужно скачать согласно тому, какая версия докера установлена на kali linux:
> docker --version

Теперь настроим подключение к докеру на WSL c Windows хоста через Docker CLI.\
Для этого отредактируем конфигурационный файл сервиса **docker**: ``/lib/systemd/system/docker.service``.

Прочитать файл можно при помощи команды ``cat``, а отредактировать при помощи редактора ``nano``:
> sudo nano /lib/systemd/system/docker.service

В этом файле в параметре ``ExecStart`` не удаляя из него ничего в самый конец допишем:
> -H tcp://0.0.0.0

После изменений нажмём ``CTRL+O`` для сохранения и ``CTRL+X`` для выхода.

Остаётся только перезапустить сервис Docker'а:
> sudo systemctl daemon-reload
sudo systemctl restart docker

Теперь можем завершить сеанс при помощи команды ``exit``.\
Как мы помним, дистрибутив в короткое время остановится сам после завершения всех сессий.\
Можно остановить вручную при помощи ``wsl -t имяДистрибутива`` или остановить все дистрибутивы при помощи ``wsl --shutdown``.

На Windows хосте нужно настроить подключение Docker CLI (т.е. клиента) к докеру на WSL (т.е. к Docker Host'у).
Для этого используется переменная среду окружения (environment variable) с названием **DOCKER_HOST**.

Для начала, отправим в **WSL** специальную команду, чтобы узнать IP адрес, по которому обращаться докер клиентом:
> wsl -- ip addr show eth0 | FINDSTR inet

Далее, полученный IP адрес (указан после **inet**) укажем в DOCKER_HOST (порт по умолчанию всегда **2375**):
> SET DOCKER_HOST=tcp://172.21.152.63:2375

Более подробно про порты см. документацию: **"[Daemon socket option](https://docs.docker.com/reference/cli/dockerd/#daemon-socket-option)"**

⚠ **Важно:** ⚠
Docker Client лишь отправляет просьбы выполнить команды.\
Все команды будут выполняться от лица Docker Engine в рамках файловой системы, где этот Docker Engine стоит.
Для выполнения докер команды с Windows хоста нужно открыть сессию с WSL, т.к. без сессий wsl дистрибутив будет остановлен.

Теперь мы можем выполнять docker команды локально на Windows, а они будут выполняться на Docker в WSL.

Естественно, докер команды можно выполнять непосредственно подключившись к **wsl**.\
Кроме того, можно их отправлять на выполнение (execute) на wsl. Например:
> wsl -e docker ps

----

## [↑](#home) <a id="first"></a> First Run
Опробуем теперь Docker в деле.

Для проекта нам понадобится система сборки проектов **"[Gradle](https://gradle.org/)"**.\
Создадим каталог и инициализируем там Gradle проект согласно инструкции из документации Gradle **"[Step 1. Initializing the Project](https://docs.gradle.org/current/userguide/part1_gradle_init.html#step_1_initializing_the_project)"**:
> mkdir simpleApp
cd simpleApp
gradle init --type java-application --dsl groovy

На все задаваемые вопросы просто нажимаем Enter, чтобы принять заданные Gradle ответы по умолчанию.

Создадим простенькое Spring Boot веб-приложение.

Проверим в документации Spring системные требования. Прежде всего нас интересует версия JDK.
Документация: https://docs.spring.io/spring-boot/system-requirements.html

В докуметации Spring найдём раздел **"[Build Tool Plugins/Gradle Plugin/Getting Started](https://docs.spring.io/spring-boot/gradle-plugin/getting-started.html)"**.\
Согласное этому разделу заполним наш gradle build скрипт:
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.3.0'
}
apply plugin: 'io.spring.dependency-management'

repositories {
    mavenCentral()
}

dependencies {
    implementation('org.springframework.boot:spring-boot-starter-data-rest')
}

// Apply a specific Java toolchain: https://docs.gradle.org/current/userguide/toolchains.html
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
```

Теперь согласно документации создадим Spring Boot приложение.\
Документация: **https://docs.spring.io/spring-boot/reference/using/using-the-springbootapplication-annotation.html**
```java
package simpleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

Стоит обратить внимание, что Spring настоятельно не рекомендует **"default package"**, т.е. когда классы валяются сразу в src/java.
Поэтому классы стоит располагать в пакетах.

Создадим Spring контроллер, который будет отвечать на входящие обращения:
```java
package simpleapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class MyRestController {
    @GetMapping("/{requestId}")
    public String getUser(@PathVariable Long requestId) {
        return "Request " + requestId;
    }
}
```

Если запустить приложение, то мы сможем по адресу ``http://localhost:8080/rest/1`` получить ответ от Spring приложения.

Теперь, попробуем запаковать данное приложение в docker контейнер и развернуть его на WSL.

При помощи Gradle выполним Gradle Task с названием **"bootJar"**:
> gradlew bootJar

После этого в корне проекта создадим файл ``Dockerfile``.\
Начать файл нужно с указания **"[base image](https://docs.docker.com/build/cache/#use-an-appropriate-base-image)"**:
> FROM eclipse-temurin:17-jdk-alpine

Стоит быть внимательным, т.к. в интернете много примеров с другим базовым образом **[openjdk](https://hub.docker.com/_/openjdk)**, который сейчас уже считается **deprecated**.

Дальше нужно добавить в образ наше приложение и описать его запуск:
> WORKDIR /app
COPY ./app/build/libs/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

Создадим образ:
> docker build -t test-docker-app .

Теперь он появится в списке образов Docker:
> docker image ls

После этого проверим, что мы можем создать Docker контейнер:
> docker run -p 8080:8080 -d test-docker-app

``-d`` означает **"detached"**, т.е. контейнер запускается в отдельным процессом. Теперь мы можем увидеть этот контейнер:
> docker ps -a

Теперь мы можем снова на хосте (т.е. на Windows) обратиться к ``http://localhost:8080/rest/1`` и увидим ответ от приложения.\
Windows хост имеет доступ к приложению, которое работает в докер контейнере на WSL.

Чтобы остановить контейнер мы можем воспользоваться командой **docker stop**:
> docker stop <ContainerName>

Удалить остановленные контейнеры можно при помощи команды ``docker container prune``.

Если сервер через localhost недоступен стоит обратить внимание на такую проблему: **"[Local sites running in WSL2 not accessible in browser](https://github.com/microsoft/WSL/issues/5298)"**.