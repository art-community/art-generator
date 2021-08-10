# ART Code Generator

## Бейджики
[![ART Generator Main](https://github.com/art-community/art-generator/actions/workflows/push-main.yml/badge.svg)](https://github.com/art-community/art-generator/actions/workflows/push-main.yml)
![GitHub repo size](https://img.shields.io/github/repo-size/art-community/art-generator)

## Требования
- Java 11+
- Gradle 7+

## Фидбек
Баги и пожелания можете оставить на GitHub [Github Issues](https://github.com/art-community/art-generator/issues).

Наша Телега: https://t.me/art_github

## Зачем ?

Основная серверная платформа для ART решений - JVM.

На текущий момент существует три основных подхода для автоматических (не требующих ручного кода) парсеров и генераторов
данных посредством классов:

1. Java Reflection
2. Byte Code runtime generation
3. Source code generation

Первый - медленный, непрозрачный и небезопасный.

Второй - быстрее первого, но такой же непрозрачный и небезопасный + добавляет привязку к конкретным версиям byte-code.

Третий - потребовал написания данного проекта. Но при этом является наиболее быстрым и прозрачным способом.

## Что делает ?

```
Разработчик пишет код:

Java: 

package model;

@Getter
@AllArgsConstructor
public class MyObject {
    private final String id;
} 

Kotlin:

package model;
data class MyObject(val id: String)
```

```
Генератор (отдельно запущенный процесс, управляемый через Gradle рефреш или таски):

1. Анализирует код разработчика
2. Генерирует код мета-информации в один отдельный мета-класс <source-set-root>/meta/Meta<имя-модуля><язык(если используется >1 языков)>
```

```
Разработчик, который хочет работать с мета-информацией пишет код:

Java:
new <имя сгенерированного класса>().modelPackage().myObjectClass().getIdField()

Kotlin:
<имя сгенерированного класса>().modelPackage().myObjectClass().idField
```

## Что позволяет делать ?

Основные сценарии использования:

1. Вызов конструкторов у объектов (без рефлексии или ссылок на методы, вызываются непосредственно сразу конструкторы)
2. Вызов методов у объектов и статик методов у классов
3. Геттеры и сеттеры у полей
4. Мета-операции:
    * Получить все пакеты
    * Получить все классы у пакета
    * Получить все поля, конструкторы, методы (+ геттеры и сеттеры для свойств у Kotlin)
5. Создание Proxy классов на основе интерфейсов, позволяющих делегировать вызов метода интерфейса на объект Function

## Ограничения

1. Generics (игнорируются классы и методы с переменными типа)
2. Kotlin Lamdba (для них в качестве типа будет Any, но сами поля и параметры будут работать)
3. Классы, методы и конструкторы - только публичные. Поля - все области видимости.
4. Классы должны быть в пакетах. Java не поддерживает импорт из default пакета во вложенные (не хочется Мета класс
   выносить на default уровень), а для Kotlin эту функцию специально не поддерживаем, чтобы сохранять единообразие в
   работе
