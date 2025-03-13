Техническое задание проекта №4
Как человек обычно делает покупки? Если ему нужен не один продукт, а несколько, то очень вероятно, что сначала он составит список, чтобы ничего не забыть. Сделать это можно где угодно: на листе бумаги, в приложении для заметок или, например, в сообщении самому себе в мессенджере. 
А теперь представьте, что это список не продуктов, а полноценных дел. И не каких-нибудь простых вроде «помыть посуду» или «позвонить бабушке», а сложных — например, «организовать большой семейный праздник» или «купить квартиру». Каждая из таких задач может разбиваться на несколько этапов со своими нюансами и сроками. А если над их выполнением будет работать не один человек, а целая команда, то организация процесса станет ещё сложнее.
Трекер задач

Как системы контроля версий помогают команде работать с общим кодом, так и трекеры задач позволяют эффективно организовать совместную работу над задачами. Вам предстоит написать бэкенд для такого трекера. В итоге должна получиться программа, отвечающая за формирование модели данных для этой страницы:

💡 Пользователь не будет видеть консоль вашего приложения. Поэтому нужно сделать так, чтобы методы не просто печатали что-то в консоль, но и возвращали объекты нужных типов.
Вы можете добавить консольный вывод для самопроверки в класcе Main, но на работу методов он влиять не должен.
Типы задач

Простейший кирпичик трекера — задача (англ. task). У неё есть следующие свойства:
Название, кратко описывающее суть задачи (например, «Переезд»).
Описание, в котором раскрываются детали.
Уникальный идентификационный номер задачи, по которому её можно будет найти.
Статус, отображающий её прогресс. Вы будете выделять следующие этапы жизни задачи, используя enum: 1. NEW — задача только создана, но к её выполнению ещё не приступили. 2. IN_PROGRESS — над задачей ведётся работа. 3. DONE — задача выполнена.
Иногда для выполнения какой-нибудь масштабной задачи её лучше разбить на подзадачи (англ. subtask). Большая задача, которая делится на подзадачи, называется эпиком (англ. epic). 
Подытожим. В системе задачи могут быть трёх типов: обычные задачи, эпики и подзадачи. Для них должны выполняться следующие условия:
Для каждой подзадачи известно, в рамках какого эпика она выполняется.
Каждый эпик знает, какие подзадачи в него входят.
Завершение всех подзадач эпика считается завершением эпика.
Подсказка: как организовать классы для хранения задач.
У одной и той же проблемы в программировании может быть несколько решений. К примеру, вам нужно представить в программе три вида связанных сущностей: задачи, подзадачи и эпики. Вы можете завести один абстрактный класс и связать три других с ним. Или создать один не абстрактный класс и двух его наследников. Или сделать три отдельных класса. 
Задача программиста — не только сделать выбор, но и обосновать его. Вне зависимости от того, по какому пути вы решите пойти, каждое из этих решений будет лучше в одних ситуациях и хуже в других. 
Для эталонного решения мы выбрали создание публичного не абстрактного класса Task, который представляет отдельно стоящую задачу. Его данные наследуют подклассы Subtask и Epic.
В нашем задании класс Task можно использовать сам по себе, не делая его абстрактным. Для подклассов Subtask и Epic наследуем сразу имплементацию, поскольку нам понадобится такое расширение функциональности, которое совместимо с базовым классом и не отличается от него по поведению.
Идентификатор задачи

В трекере у каждого типа задач есть идентификатор. Это целое число, уникальное для всех типов задач. По нему находят, обновляют, удаляют задачи. При создании задачи менеджер присваивает ей новый идентификатор.
Подсказка: как создавать идентификаторы.
Для генерации идентификаторов можно использовать числовое поле-счётчик внутри класса TaskManager, увеличивая его на 1, когда нужно получить новое значение.
Также советуем применить знания о методах equals() и hashCode(), чтобы реализовать идентификацию задачи по её id.  При этом две задачи с одинаковым id должны выглядеть для менеджера как одна и та же. 
💡 Эти методы нежелательно переопределять в наследниках. Ваша задача — подумать, почему.
Менеджер

Кроме классов для описания задач, вам нужно реализовать класс для объекта-менеджера. Он будет запускаться на старте программы и управлять всеми задачами. В нём должны быть реализованы следующие функции:
Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
Методы для каждого из типа задач(Задача/Эпик/Подзадача):
 a. Получение списка всех задач.
 b. Удаление всех задач.
 c. Получение по идентификатору.
 d. Создание. Сам объект должен передаваться в качестве параметра.
 e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
 f. Удаление по идентификатору.
Дополнительные методы: a. Получение списка всех подзадач определённого эпика.
Управление статусами осуществляется по следующему правилу:
 a. Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
 b. Для эпиков:
если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
во всех остальных случаях статус должен быть IN_PROGRESS.
