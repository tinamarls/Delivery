-- возможно добавлю еще какие-нибудь параметры

-- позиция в меню
create table if not exists product
    (id bigserial primary key,
    name_of_product varchar(50),
    price int,
    category_of_product varchar(30),
    description varchar(512)
);

-- пользователи(для авторизации и регистрации)
create table if not exists users(
    id bigserial primary key,
    login_of_user varchar(50) not null unique,
    password_of_user varchar(50) not null unique,
    role_of_user varchar(40)
);

--посмотреть, как будет себя ввести ввод данных с полем даты
-- клиент
create table if not exists client(
    id bigserial primary key,
    name_of_client varchar(80),
    phone_number varchar(20) unique,
    date_of_birth date,
    user_id bigint not null references users(id)
);

-- курьер
create table if not exists courier(
    id bigserial primary key,
    name_of_courier varchar(50),
    phone_number varchar(20),
    user_id bigint not null references users(id)
);

-- заказы
create table if not exists ordering(
    id bigserial primary key,
    name_of_client varchar(30),
    number varchar(30),
    full_price int,
    status varchar(20),
    address varchar(60),
    comment_for_order varchar(1024),
    client_id bigint not null references client(id),
    courier_id bigint references courier(id)
);

-- таблица для М2М, заказы и их продукты
create table if not exists product_in_ordering(
    id bigserial primary key,
    product_id bigint not null references product(id),
    count_of_product int,
    ordering_id bigint not null references ordering(id)
);

create table if not exists photo(
    id bigserial primary key,
    path varchar(1024),
    unique_name varchar(127)
);

alter table client
    add bonuses int default 0;

alter table courier
    add status varchar(30) default 'отдыхает';

-- добавить not null
alter table product
    add photo varchar(127);

-- 9,,,,14
-- 10,,,,15
-- 11,,,,21
-- 1,,,,22
-- 2,Кристина,89870379906,2003-09-11,24
-- 3,,,,25

-- 1,Кристина,+8 (987) 037-99-06,419,принят,"420000, Респ Татарстан, г Казань",Быстреееееее,2,
-- 2,Кристина Владимировна Коржуева,+8 (987) 037-99-06,847,принят,"420000, Респ Татарстан, г Казань, ДУ","",2,


-- 9,,,,,,14
-- 10,,,,,,15
-- 11,,,,,,21

