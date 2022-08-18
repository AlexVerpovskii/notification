CREATE SCHEMA demo;
alter schema demo owner to notification;

CREATE SEQUENCE demo.notification_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE demo.service_notification_seq NO MINVALUE NO MAXVALUE;

CREATE TABLE demo.notification
(
    id          BIGSERIAL               NOT NULL,
    title       text,
    user_id     text                    NOT NULL,
    service_id  text,
    type_id     text                    NOT NULL,
    status      char                    not null,
    description text                    NOT NULL,
    sys_crt     timestamp default now() not null,
    date_read   timestamp,
    PRIMARY KEY (id)
);
COMMENT ON TABLE demo.notification IS 'Журнал уведомлений';
COMMENT ON COLUMN demo.notification.id IS 'Уникальный индификатор уведмомления';
COMMENT ON COLUMN demo.notification.title IS 'Заголовок';
COMMENT ON COLUMN demo.notification.user_id IS 'Пользователь, который создал уведомление';
COMMENT ON COLUMN demo.notification.service_id IS 'Сервис полученного уведомления';
COMMENT ON COLUMN demo.notification.status IS 'Статус уведмоления. Y - прочтено, N - не прочтено';
COMMENT ON COLUMN demo.notification.description IS 'Описание уведомления';
COMMENT ON COLUMN demo.notification.sys_crt IS 'Дата и время создания записи';
COMMENT ON COLUMN demo.notification.date_read IS 'Дата и время прочтения пользователем записи';
CREATE INDEX notification_user_id ON demo.notification (user_id);
CREATE INDEX notification_service_id ON demo.notification (service_id);


CREATE TABLE demo.service
(
    id   BIGSERIAL NOT NULL,
    name text      NOT NULL,
    PRIMARY KEY (id)
);
COMMENT ON TABLE demo.service IS 'Сервисы уведомлений';
COMMENT ON COLUMN demo.service.name IS 'Навзание сервиса';
CREATE INDEX service_id ON demo.service (id);


CREATE TABLE demo.service_notification
(
    id              BIGSERIAL NOT NULL,
    notification_id int8      NOT NULL,
    service_id      int8      NOT NULL,
    PRIMARY KEY (id)
);
COMMENT ON TABLE demo.service_notification IS 'Оповещение для пользоватлей';
COMMENT ON COLUMN demo.service_notification.service_id IS 'Id сервиса';
COMMENT ON COLUMN demo.service_notification.notification_id IS 'Id уведомления';


ALTER TABLE demo.service_notification
    ADD CONSTRAINT service_fk FOREIGN KEY (service_id) REFERENCES demo.service (id);
ALTER TABLE demo.service_notification
    ADD CONSTRAINT notification_fk FOREIGN KEY (notification_id) REFERENCES demo.notification (id);



