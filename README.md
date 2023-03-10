### JDK Version:

* 18

### Система сборки:

* Apache Maven 3.8.1

### Библиотеки:

* JUnit 4.13.2

JUnit Maven dependency:

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>

* В Maven добавлен plugin для соборки исполняемого jar-файла с зависимостями (смотри pom.xml):

                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>

### Запуск (1 способ):

* Открыть папку с проектом (В Intellij IDEA: открыть pom.xml как проект, подгрузить все необходимые зависимости через
  Reload Project в Maven)
* Выполнить цель ```mvn install```
* Исполняемый файл **sort-it-1.0.jar** будет находиться в папке **target**
* Запустить программу (пример для случая, когда запускаем из корневой директории проекта):

```java -jar target/sort-it-1.0.jar -a -i output.txt input1.txt input2.txt input3.txt```

### Запуск (2 способ, Intellij IDEA):

* Настроить конфигурацию, указав параметры запуска:

![configuration](materials/configuration.PNG)

Рисунок 1 - Настройки конфигурации

* Запустить программу

### Особенности реализации:

* Присутствуют unit-тесты для проверки правильности работы программы: для запуска тестов выполнить цель ```mvn test```
* В процессе работы программы в консоль выводится полная информация о параметрах, заданных пользователем (тип
  сортировки, тип
  данных, выходной файл, принятые входные файлы);
* В случае, если указанный пользователем выходной файл не существует, он будет создан программой;
* Если среди указанных входных файлов существует хотя бы один, то программа выполнится;
* Если не будет указан тип сортировки или хотя бы один существующий выходной файл, то будет выведено сообщение об ошибке
  с объяснением ее причины;
* Если в файле нарушен порядок сортировки, будет выведено соответствующее сообщение, программа при этом продолжит
  работу;
* Строки с пробельными символами не обрабатываются, сразу же считываются следующие за ними значения в файле;
* Как только обнаруживается несоответствие указанного типа сортировки с сортировкой в файле, программа перестает
  считывать значения из файла;
* Программа способна работать с большими файлами, не загружая оперативную память.
