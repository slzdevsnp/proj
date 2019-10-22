#!/bin/bash

path=/tmp
generator=./data-generator-osx

#small dataset
#cnt=1000
#rval=50
#nval=100
#fname=stream_smpl.jsonl 

#large dataset
cnt=1000000
rval=1000
nval=100000
fname=stream_prd.jsonl

${generator} -c ${cnt} -o ${path}/${fname} -r ${rval} -n ${nval}

