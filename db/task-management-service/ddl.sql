-- Project Name : service-db-tast-management-service
-- Date/Time    : 2023/08/19 19:01:07
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

-- task_statuses
-- * RestoreFromTempTable
create table "状態" (
  "状態ID" smallint not null
  , "状態" varchar(50)
  , constraint "状態_PKC" primary key ("状態ID")
) ;

-- tasks
-- * RestoreFromTempTable
create table "タスク" (
  "タスクID" bigint not null
  , "タスク名" varchar(100)
  , "ステージID" bigint not null
  , "担当者アカウントID" bigint not null
  , "タスク説明" varchar(500)
  , "状態ID" smallint not null
  , "タスク開始日" date
  , "タスク終了日" date
  , "レコード作成日" date
  , "レコード終了日" date
  , constraint "タスク_PKC" primary key ("タスクID")
) ;

-- stages
-- * RestoreFromTempTable
create table "ステージ" (
  "ステージID" bigint not null
  , "プロジェクトID" bigint not null
  , "ステージ名" varchar(100)
  , "ステージ開始日" date
  , "ステージ終了日" date
  , "レコード作成日" date
  , "レコード論理削除日" date
  , constraint "ステージ_PKC" primary key ("ステージID")
) ;

-- assignment
-- * RestoreFromTempTable
create table "プロジェクト参画" (
  "アサインID" bigint not null
  , "プロジェクトID" bigint not null
  , "アカウントID" bigint not null
  , "レコード作成日時" date
  , "レコード論理削除日時" date
  , constraint "プロジェクト参画_PKC" primary key ("アサインID")
) ;

-- authentications
-- * RestoreFromTempTable
create table "ログイン情報" (
  "アカウントID" bigint not null
  , "パスワード" varchar(100) not null
  , "レコード作成日時" date not null
  , "レコード更新日時" date
  , "レコード削除日時" date
  , constraint "ログイン情報_PKC" primary key ("アカウントID")
) ;

-- accounts
-- * RestoreFromTempTable
create table "アカウント" (
  "アカウントID" bigint not null
  , "ログインID" varchar(100) not null
  , "アカウント名" varchar(30) not null
  , "レコード作成日時" date
  , constraint "アカウント_PKC" primary key ("アカウントID")
) ;

-- projects
-- * RestoreFromTempTable
create table "プロジェクト" (
  "プロジェクトID" bigint not null
  , "プロジェクト名" varchar(100)
  , constraint "プロジェクト_PKC" primary key ("プロジェクトID")
) ;

alter table "タスク"
  add constraint "タスク_FK1" foreign key ("状態ID") references "状態"("状態ID");

alter table "タスク"
  add constraint "タスク_FK2" foreign key ("担当者アカウントID") references "アカウント"("アカウントID");

alter table "タスク"
  add constraint "タスク_FK3" foreign key ("ステージID") references "ステージ"("ステージID");

alter table "ステージ"
  add constraint "ステージ_FK1" foreign key ("プロジェクトID") references "プロジェクト"("プロジェクトID");

alter table "プロジェクト参画"
  add constraint "プロジェクト参画_FK1" foreign key ("アカウントID") references "アカウント"("アカウントID");

alter table "プロジェクト参画"
  add constraint "プロジェクト参画_FK2" foreign key ("プロジェクトID") references "プロジェクト"("プロジェクトID");

alter table "アカウント"
  add constraint "アカウント_FK1" foreign key ("アカウントID") references "ログイン情報"("アカウントID");

comment on table "状態" is 'task_statuses';
comment on column "状態"."状態ID" is 'status_id';
comment on column "状態"."状態" is 'status';

comment on table "タスク" is 'tasks';
comment on column "タスク"."タスクID" is 'task_id';
comment on column "タスク"."タスク名" is 'task_name';
comment on column "タスク"."ステージID" is 'stage_id';
comment on column "タスク"."担当者アカウントID" is 'worker_account_id';
comment on column "タスク"."タスク説明" is 'description';
comment on column "タスク"."状態ID" is 'status_id';
comment on column "タスク"."タスク開始日" is 'start_date';
comment on column "タスク"."タスク終了日" is 'end_date';
comment on column "タスク"."レコード作成日" is 'created_at';
comment on column "タスク"."レコード終了日" is 'deleted_at';

comment on table "ステージ" is 'stages';
comment on column "ステージ"."ステージID" is 'stage_id';
comment on column "ステージ"."プロジェクトID" is 'project_id';
comment on column "ステージ"."ステージ名" is 'stage_name';
comment on column "ステージ"."ステージ開始日" is 'start_date';
comment on column "ステージ"."ステージ終了日" is 'end_date';
comment on column "ステージ"."レコード作成日" is 'created_at';
comment on column "ステージ"."レコード論理削除日" is 'deleted_at';

comment on table "プロジェクト参画" is 'assignment';
comment on column "プロジェクト参画"."アサインID" is 'assignment_id';
comment on column "プロジェクト参画"."プロジェクトID" is 'project_id';
comment on column "プロジェクト参画"."アカウントID" is 'account_id';
comment on column "プロジェクト参画"."レコード作成日時" is 'created_at';
comment on column "プロジェクト参画"."レコード論理削除日時" is 'deleted_at';

comment on table "ログイン情報" is 'authentications';
comment on column "ログイン情報"."アカウントID" is 'account_id';
comment on column "ログイン情報"."パスワード" is 'password';
comment on column "ログイン情報"."レコード作成日時" is 'created_at';
comment on column "ログイン情報"."レコード更新日時" is 'updated_at';
comment on column "ログイン情報"."レコード削除日時" is 'deleted_at';

comment on table "アカウント" is 'accounts';
comment on column "アカウント"."アカウントID" is 'account_id';
comment on column "アカウント"."ログインID" is 'login_id';
comment on column "アカウント"."アカウント名" is 'account_name';
comment on column "アカウント"."レコード作成日時" is 'created_at';

comment on table "プロジェクト" is 'projects';
comment on column "プロジェクト"."プロジェクトID" is 'project_id';
comment on column "プロジェクト"."プロジェクト名" is 'project_name';

