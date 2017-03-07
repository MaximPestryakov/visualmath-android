# Мобильное приложение для Blended-Learning системы VisualMath.ru

[![Build Status](https://circleci.com/gh/MaximPestryakov/visualmath-android.svg?style=shield&circle-token=95b6ee46516a0ea7be1f86f768527f3987a2bdf5)](https://circleci.com/gh/MaximPestryakov/visualmath-android/tree/master)

Пестряков Максим Станиславович, 153 группа

Описание
--------

Проект представляет собой создание мобильного приложения для операционной системы Android. Приложение должно иметь функциональность, аналогичную Web-версии, а именно:
  - Авторизация пользователя
  - Отображение списка доступных лекций
  - Подключение к лекции
  - Просмотр материалов лекции (LaTeX формулы, интерактивные графики и т.д.)
  - Участие в онлайн проверочных работах

Используемые технологии
-----------------------

Приложение будет полностью написано на языке программирования Java 7 c использованием автоматизированной системы сборки Gradle и следующих библиотек:
  - Gson — библиотека, которая позволяет конвертировать объекты Java в JSON и наоборот
  - Mosby — MPV-библиотека для Android-приложений
  - RxJava — библиотека, добавляющая возможность реактивного программирования
  - Retrofit — типобезопасный HTTP-клиент, инструмент для работы с API в клиент-серверных приложениях
  - Retrolambda — бэкпорт лямбда-выражений Java 8 в Java 7
  - Dagger — библиотека для удобного внедрения зависимостей в Java
  - Butter Knife — библиотека для инъекции полей и методов View

План
----

План аналогичен списку функциональности Web-версии. Ко второй точке должно быть реализовано все, кроме интерактивных графиков, они должны быть реализованы к третьей контрольной точке
