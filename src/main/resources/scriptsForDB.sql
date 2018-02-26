
insert into category value (1,'smartphones');
insert into category value (2,'computers');
insert into category value (3,'gadgets');

insert into dictionary value (1,'ROLES','ROLE_ADMIN');
insert into dictionary value (2,'ROLES','ROLE_USER');
insert into dictionary value (3,'ORDER STATE','BASKED');
insert into dictionary value (4,'ORDER STATE','BOUGHT');
insert into app_user value (2,180000,1,'admin',0,'12345',1);
insert into app_user  value (1,1200,1,'Misha',2,'12345',2);
insert into app_user value (3,10200,1,'Petya',2,'12345',2);
insert into app_user value (4,10200,1,'Tolik',3,'12345',2);
insert into app_user value (6,10200,1,'Vasya',4,'12345',2);



insert into properties value (1,'discount',10);
insert into properties value (2,'relatedDiscount',5);

insert into images value (1,'x.jpg');
insert into images value (2,'meizu.jpg');
insert into images value (3,'samsS6.jpg');
insert into images value (4,'samsung_gear_vr2.jpg');
insert into images value (5,'mi4x.jpg');
insert into images value (6,'DrDreSolo3.jpg');
insert into images value (7,'marshall_2.jpg');
insert into images value (8,'macbook.jpg');
insert into images value (9,'dell.jpg');
insert into product value (1,'Apple iPhone X',53999,2,1,1);
insert into product value (2,'Meizu M5 Note',4499,1,1,2);
insert into product value (3,'Samsung S6',18999,2,1,3);
insert into product value (4,'Samsung gear v2',1849,3,3,4);
insert into product value (5,'Xiaomi 4X',4999,4,1,5);
insert into product value (6,'Dr Dre Solo 3',7999,5,3,6);
insert into product value (7,'Marshall 2',1530,6,3,7);
insert into product value (8,'MacBook Pro',58999,7,2,8);
insert into product value (9,'Dell Latitude',12800,8,2,9);

insert into storage value (1,10,1);
insert into storage value (2,10,2);
insert into storage value (3,10,3);
insert into storage value (4,10,4);
insert into storage value (5,10,5);
insert into storage value (6,10,6);
insert into storage value (7,10,7);
insert into storage value (8,10,8);
insert into storage value (9,10,9);

insert into purchase value (5,1,1000,1,3,3);
insert into purchase value (3,1,1000,1,3,2);
insert into purchase value (4,1,1000,1,4,2);
insert into purchase value (2,1,1000,1,4,3);