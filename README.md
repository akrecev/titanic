Тестовое задание "Список пассажиров Титаника"


<details>
  <summary><strong>Описание</strong></summary>
При первом запуске скачать данные с сайта https://web.stanford.edu/class/archive/cs/cs109/cs109.1166/problem12.html, ссылка на файл с данными (https://web.stanford.edu/class/archive/cs/cs109/cs109.1166/stuff/titanic.csv). Данные записать в базу данных, при записи часть данных модифицировать: данные о классе пассажира (Pclass) заменить на Enum, идентификатор выжившего (Survived) заменить на тип данных Boolean. <br>
Пользователь попадает на экран со списком пассажиров Титаника. На странице должно отображаться 50 пассажиров, должна быть возможность изменять количество отображаемых пассажиров на странице. Также должна быть пагинация. <br>
Должна быть возможность сортировать (по возрастанию, по убыванию) список по полям: Имя, Возраст, Оплата. <br>
На странице должно быть поле поиска пассажира по имени. <br>
В нижней части списка должна быть статистика по следующим полям: <br>

- Общая сумма оплаты проезда.

- Количество людей имеющих родственников на борту.

- Количество выживших на борту.

Данная статистика должна меняться в зависимости от фильтрованных данных, но по всем пассажирам, а не только тех что на экране. <br>
На экране должны быть кнопки, которые фильтруют данные: <br>

- показать всех выживших пассажиров.

- показать всех совершеннолетих пассажирова (страше 16 лет)

- показать всех пассажиров мужского пола

- показать всех пассажиров кто не имеет родственников.

Должна быть возможность комбинировать фильтры. <br>

Должно быть реализовано кэширование данных, выбор технологии кэширования на своё усмотрение. <br>
</details>

<details>
  <summary><strong>Требования к результату</strong></summary>
Код приложения необходимо снабдить комментариями. <br>
Приложение должно собираться при помощи maven без установки или настройки каких-либо дополнительных компонент; <br>
Должен быть заполнен текстовый файл readme.md с инструкцией по сборке, настройке, конфигурированию и развертыванию приложения (если необходимо); <br>
Результаты должны быть загружены на GitHub/GitLab (желательно вести последовательную разработку: один коммит - одна фича). <br>
 <br>
</details>

<details>
  <summary><strong>Используемые технологии</strong></summary>

- Java 17
- Maven
- Spring Boot 3, Spring JPA
- PostgreSQL 12, Liquibase
- REStful
- Thymeleaf

</details>

<details>
  <summary><strong>Необходимые ресурсы</strong></summary>

- IDE (Java)
- Docker

</details>

<details>
  <summary><strong>Запуск приложения в среде разработки</strong></summary>

Для запуска приложения в среде разработки необходимо:
- активировать контейнер с базой данных (docker-compose.yml). <br>
- запустить приложение в IDE (TitanicApplication.java) <br>

</details>