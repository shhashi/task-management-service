#!/bin/bash
set -e

# テーブル作成
echo $POSTGRES_USER
psql -f ddl.sql -U $POSTGRES_USER $POSTGRES_DB
