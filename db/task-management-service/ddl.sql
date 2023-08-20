-- Project Name : service-db-tast-management-service
-- Date/Time    : 2023/08/20 19:46:19
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

-- 状態
-- * RestoreFromTempTable
create table task_statuses (
                             status_id smallint not null
  , status varchar(50)
  , constraint task_statuses_PKC primary key (status_id)
) ;

-- タスク
-- * RestoreFromTempTable
create table tasks (
                     task_id bigint not null
  , task_name varchar(100)
  , stage_id bigint not null
  , worker_account_id bigint not null
  , description varchar(500)
  , status_id smallint not null
  , start_date date
  , end_date date
  , created_at date
  , deleted_at date
  , constraint tasks_PKC primary key (task_id)
) ;

-- ステージ
-- * RestoreFromTempTable
create table stages (
                      stage_id bigint not null
  , project_id bigint not null
  , stage_name varchar(100)
  , start_date date
  , end_date date
  , created_at date
  , deleted_at date
  , constraint stages_PKC primary key (stage_id)
) ;

-- プロジェクト参画
-- * RestoreFromTempTable
create table assignment (
                          assignment_id bigint not null
  , project_id bigint not null
  , account_id bigint not null
  , created_at date
  , deleted_at date
  , constraint assignment_PKC primary key (assignment_id)
) ;

-- ログイン情報
-- * RestoreFromTempTable
create table authentications (
                               account_id bigint not null
  , password varchar(100) not null
  , created_at date not null
  , updated_at date
  , deleted_at date
  , constraint authentications_PKC primary key (account_id)
) ;

-- アカウント
-- * RestoreFromTempTable
create table accounts (
                        account_id bigint not null
  , login_id varchar(100) not null
  , account_name varchar(30) not null
  , created_at date
  , constraint accounts_PKC primary key (account_id)
) ;

-- プロジェクト
-- * RestoreFromTempTable
create table projects (
                        project_id bigint not null
  , project_name varchar(100)
  , constraint projects_PKC primary key (project_id)
) ;

alter table tasks
  add constraint tasks_FK1 foreign key (status_id) references task_statuses(status_id);

alter table tasks
  add constraint tasks_FK2 foreign key (worker_account_id) references accounts(account_id);

alter table tasks
  add constraint tasks_FK3 foreign key (stage_id) references stages(stage_id);

alter table stages
  add constraint stages_FK1 foreign key (project_id) references projects(project_id);

alter table assignment
  add constraint assignment_FK1 foreign key (account_id) references accounts(account_id);

alter table assignment
  add constraint assignment_FK2 foreign key (project_id) references projects(project_id);

alter table accounts
  add constraint accounts_FK1 foreign key (account_id) references authentications(account_id);

comment on table task_statuses is '状態';
comment on column task_statuses.status_id is '状態ID';
comment on column task_statuses.status is '状態';

comment on table tasks is 'タスク';
comment on column tasks.task_id is 'タスクID';
comment on column tasks.task_name is 'タスク名';
comment on column tasks.stage_id is 'ステージID';
comment on column tasks.worker_account_id is '担当者アカウントID';
comment on column tasks.description is 'タスク説明';
comment on column tasks.status_id is '状態ID';
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
comment on column stages.created_at is 'レコード作成日';
comment on column stages.deleted_at is 'レコード論理削除日';

comment on table assignment is 'プロジェクト参画';
comment on column assignment.assignment_id is 'アサインID';
comment on column assignment.project_id is 'プロジェクトID';
comment on column assignment.account_id is 'アカウントID';
comment on column assignment.created_at is 'レコード作成日時';
comment on column assignment.deleted_at is 'レコード論理削除日時';

comment on table authentications is 'ログイン情報';
comment on column authentications.account_id is 'アカウントID';
comment on column authentications.password is 'パスワード';
comment on column authentications.created_at is 'レコード作成日時';
comment on column authentications.updated_at is 'レコード更新日時';
comment on column authentications.deleted_at is 'レコード削除日時';

comment on table accounts is 'アカウント';
comment on column accounts.account_id is 'アカウントID';
comment on column accounts.login_id is 'ログインID';
comment on column accounts.account_name is 'アカウント名';
comment on column accounts.created_at is 'レコード作成日時';

comment on table projects is 'プロジェクト';
comment on column projects.project_id is 'プロジェクトID';
comment on column projects.project_name is 'プロジェクト名';

