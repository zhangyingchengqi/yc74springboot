drop table account;
drop table inaccount;

create table account
(
   accountid varchar2(18) primary key,
   balance   number(10,2)
);

alter table account
     add constraint ck_balance
        check( balance>=1 );


create table inaccount
(
   accountid varchar2(18),
   inbalance number(10,2)
);

delete from inaccount ;

select * from account where accountid='2';
select * from inaccount;

select count(*) from account;

commit;

drop table account;

create table account 
as select substr( sys_guid(),1,17)  as accountid,   1 as balance
from dual connect by level<=3000000;

insert into account values('1',1);
insert into account values('2',100);

create index account_index on account(accountid);

drop index account_index;

select * from user_indexes where table_name='account';





