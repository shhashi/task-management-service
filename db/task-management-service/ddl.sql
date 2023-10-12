-- Project Name : service-db-tast-management-service
-- Date/Time    : 2023/10/09 21:45:20
-- Author       : shhashi
-- RDBMS Type   : PostgreSQL
-- Application  : A5:SQL Mk-2

/*
  << 注意！！ >>
  BackupToTempTable, RestoreFromTempTable疑似命令が付加されています。
  これにより、drop table, create table 後もデータが残ります。
  この機能は一時的に $$TableName のような一時テーブルを作成します。
  この機能は A5:SQL Mk-2でのみ有効であることに注意してください。
*/

-- タスク
-- * RestoreFromTempTable
create table tasks (
                       task_id bigserial not null
    , task_name varchar(400)
    , stage_id integer not null
    , worker_account_id integer not null
    , description text
    , status varchar(100) not null
    , start_date date
    , end_date date
    , created_at timestamp with time zone not null
    , deleted_at timestamp with time zone
    , constraint tasks_PKC primary key (task_id)
) ;

-- ステージ
-- * RestoreFromTempTable
create table stages (
                        stage_id serial not null
    , project_id bigint not null
    , stage_name varchar(400)
    , start_date date
    , end_date date
    , status varchar(100)
    , created_at timestamp with time zone
    , deleted_at timestamp with time zone
    , constraint stages_PKC primary key (stage_id)
) ;

-- プロジェクト参画
-- * RestoreFromTempTable
create table assignments (
                             assignment_id bigserial not null
    , project_id integer not null
    , account_id integer not null
    , created_at timestamp with time zone
    , deleted_at timestamp with time zone
    , constraint assignments_PKC primary key (assignment_id)
) ;

-- ログイン情報
-- * RestoreFromTempTable
create table authentications (
                                 account_id bigint not null
    , login_id varchar(100) not null
    , password varchar(100) not null
    , created_at timestamp with time zone not null
    , updated_at timestamp with time zone
    , deleted_at timestamp with time zone
    , constraint authentications_PKC primary key (account_id)
) ;

-- アカウント
-- * RestoreFromTempTable
create table accounts (
                          account_id serial not null
    , account_name varchar(80) not null
    , created_at timestamp with time zone not null
    , constraint accounts_PKC primary key (account_id)
) ;

alter table accounts add constraint accounts_IX1
    unique (account_name) ;

-- プロジェクト
-- * RestoreFromTempTable
create table projects (
                          project_id serial not null
    , project_name varchar(400)
    , created_at timestamp with time zone
    , closed_at date
    , constraint projects_PKC primary key (project_id)
) ;

alter table tasks
    add constraint tasks_FK1 foreign key (worker_account_id) references accounts(account_id);

alter table tasks
    add constraint tasks_FK2 foreign key (stage_id) references stages(stage_id);

alter table stages
    add constraint stages_FK1 foreign key (project_id) references projects(project_id);

alter table assignments
    add constraint assignments_FK1 foreign key (account_id) references accounts(account_id);

alter table assignments
    add constraint assignments_FK2 foreign key (project_id) references projects(project_id);

comment on table tasks is 'タスク';
comment on column tasks.task_id is 'タスクID';
comment on column tasks.task_name is 'タスク名';
comment on column tasks.stage_id is 'ステージID';
comment on column tasks.worker_account_id is '担当者アカウントID';
comment on column tasks.description is 'タスク説明';
comment on column tasks.status is 'タスク状態';
comment on column tasks.start_date is 'タスク開始日';
comment on column tasks.end_date is 'タスク終了日';
comment on column tasks.created_at is 'レコード作成日';
comment on column tasks.deleted_at is 'レコード終了日';

comment on table stages is 'ステージ';
comment on column stages.stage_id is 'ステージID';
comment on column stages.project_id is 'プロジェクトID';
comment on column stages.stage_name is 'ステージ名';
comment on column stages.start_date is 'ステージ開始日';
comment on column stages.end_date is 'ステージ終了日';
comment on column stages.status is 'ステージ状態';
comment on column stages.created_at is 'レコード作成日';
comment on column stages.deleted_at is 'レコード論理削除日';

comment on table assignments is 'プロジェクト参画';
comment on column assignments.assignment_id is 'アサインID';
comment on column assignments.project_id is 'プロジェクトID';
comment on column assignments.account_id is 'アカウントID';
comment on column assignments.created_at is 'レコード作成日時';
comment on column assignments.deleted_at is 'レコード論理削除日時';

comment on table authentications is 'ログイン情報';
comment on column authentications.account_id is 'アカウントID';
comment on column authentications.login_id is 'ログインID';
comment on column authentications.password is 'パスワード';
comment on column authentications.created_at is 'レコード作成日時';
comment on column authentications.updated_at is 'レコード更新日時';
comment on column authentications.deleted_at is 'レコード削除日時';

comment on table accounts is 'アカウント';
comment on column accounts.account_id is 'アカウントID';
comment on column accounts.account_name is 'アカウント名';
comment on column accounts.created_at is 'レコード作成日時';

comment on table projects is 'プロジェクト';
comment on column projects.project_id is 'プロジェクトID';
comment on column projects.project_name is 'プロジェクト名';
comment on column projects.created_at is '作成日';
comment on column projects.closed_at is 'クローズ日';

