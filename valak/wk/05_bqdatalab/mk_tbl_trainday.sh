#!/bin/sh

location=US 
dataset=flights
table=trainday
sqlfn=trainday_ddl.sql
bq --location=$location query --destination_table ${dataset}.${table} \
--use_legacy_sql=false "`cat $sqlfn`"

