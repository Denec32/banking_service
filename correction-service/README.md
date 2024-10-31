# Сервис корректировки (схема бд t1_demo_correction)

- Слушает топик t1_demo_client_transaction_errors

- При получении сообщения отправляет в сервис 1 http-запрос на разблокировку счета. При успешном ответе удалить запись о транзакции в БД, если таковая существует.

- При получении отказа на разблокировку, создать запись в БД о такой транзакции, если таковой еще не существует.

- Раз в установленный параметром период времени запускать функцию, которая достает из БД записи о таких транзакциях и повторно отправить на обработку в сервис 1.