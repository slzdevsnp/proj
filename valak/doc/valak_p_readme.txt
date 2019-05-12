the book is on paper, in iBooks, pdf

valak  book  tasks

plan:
19.05.12 do chap 7, chap 8
19.05.11 do chap 5, chap 6
19.05.11  review chap 4 (later)



chap2
     ingest data into cloud from web scrappig
     periodic monthly update

chap3
     basic data visualization in the sql studio
chap4 
    streaming simulation with pubsub
chap5 
    interactive data exploratino with datalab 
    instances. lrn how to create a datalab vm, how to use magic tags with bq 
    how to perform exploratory analysis with bq, quickly plot pandas Data frames
    APPROX_QUANTILES, FARM_FINGERPRINT funcs
chap6 
    dataproc  (spark)
   


chap7

chap8

chap9

chap10



==========================
   ch2_ingestion
==========================

bts url
https://www.transtats.bts.gov/DL_SelectFields.asp?Table_ID=236

fields mapping

FlightDAte FL_DATE


open chrome developer tools
Network   tick Preserve log


fields mapping
FlightDate 			
UniqueCarrier 		Reporting_Airline
AirlineID  			DOT_ID_Reporting_Airline
CarierCode 			IATA_CODE_Reporting_Airline
FlightNum			Flight_Number_Reporting_Airline
OriginAirportID 	
OriginAirportSeqID 	
OriginCityMarketID	
Origin				
DestAirportID		
DestAirportSeqID	
DestCityMarketID	
Dest				
CRSDepTime			
DepTime
DepDelay
TaxiOut
WheelsOff 
WheelsOn
TaxiIn
CRSArrTime
ArrTime
ArrDelay
Cancelled
CancellationCode
Diverted
Distance

OK
in network tools click on Download_Table.asp POST request
on Headers scroll down to Form Data,  click to view source

#curl command 

## params value is pated from the Form data window in Chrome dev tools ####
## search this string for "YEAR" to find 2016 and "MONTH" to find 1
PARAMS="UserTableName=Reporting_Carrier_On_Time_Performance_1987_present&DBShortName=&RawDataTable=T_ONTIME_REPORTING&sqlstr=+SELECT+FL_DATE%2COP_UNIQUE_CARRIER%2COP_CARRIER_AIRLINE_ID%2COP_CARRIER%2COP_CARRIER_FL_NUM%2CORIGIN_AIRPORT_ID%2CORIGIN_AIRPORT_SEQ_ID%2CORIGIN_CITY_MARKET_ID%2CORIGIN%2CDEST_AIRPORT_ID%2CDEST_AIRPORT_SEQ_ID%2CDEST_CITY_MARKET_ID%2CDEST%2CCRS_DEP_TIME%2CDEP_TIME%2CDEP_DELAY%2CTAXI_OUT%2CWHEELS_OFF%2CWHEELS_ON%2CTAXI_IN%2CCRS_ARR_TIME%2CARR_TIME%2CARR_DELAY%2CCANCELLED%2CCANCELLATION_CODE%2CDIVERTED%2CDISTANCE+FROM++T_ONTIME_REPORTING+WHERE+Month+%3D1+AND+YEAR%3D2016&varlist=FL_DATE%2COP_UNIQUE_CARRIER%2COP_CARRIER_AIRLINE_ID%2COP_CARRIER%2COP_CARRIER_FL_NUM%2CORIGIN_AIRPORT_ID%2CORIGIN_AIRPORT_SEQ_ID%2CORIGIN_CITY_MARKET_ID%2CORIGIN%2CDEST_AIRPORT_ID%2CDEST_AIRPORT_SEQ_ID%2CDEST_CITY_MARKET_ID%2CDEST%2CCRS_DEP_TIME%2CDEP_TIME%2CDEP_DELAY%2CTAXI_OUT%2CWHEELS_OFF%2CWHEELS_ON%2CTAXI_IN%2CCRS_ARR_TIME%2CARR_TIME%2CARR_DELAY%2CCANCELLED%2CCANCELLATION_CODE%2CDIVERTED%2CDISTANCE&grouplist=&suml=&sumRegion=&filter1=title%3D&filter2=title%3D&geo=All%A0&time=January&timename=Month&GEOGRAPHY=All&XYEAR=2016&FREQUENCY=1&VarDesc=Year&VarType=Num&VarDesc=Quarter&VarType=Num&VarDesc=Month&VarType=Num&VarDesc=DayofMonth&VarType=Num&VarDesc=DayOfWeek&VarType=Num&VarName=FL_DATE&VarDesc=FlightDate&VarType=Char&VarName=OP_UNIQUE_CARRIER&VarDesc=Reporting_Airline&VarType=Char&VarName=OP_CARRIER_AIRLINE_ID&VarDesc=DOT_ID_Reporting_Airline&VarType=Num&VarName=OP_CARRIER&VarDesc=IATA_CODE_Reporting_Airline&VarType=Char&VarDesc=Tail_Number&VarType=Char&VarName=OP_CARRIER_FL_NUM&VarDesc=Flight_Number_Reporting_Airline&VarType=Char&VarName=ORIGIN_AIRPORT_ID&VarDesc=OriginAirportID&VarType=Num&VarName=ORIGIN_AIRPORT_SEQ_ID&VarDesc=OriginAirportSeqID&VarType=Num&VarName=ORIGIN_CITY_MARKET_ID&VarDesc=OriginCityMarketID&VarType=Num&VarName=ORIGIN&VarDesc=Origin&VarType=Char&VarDesc=OriginCityName&VarType=Char&VarDesc=OriginState&VarType=Char&VarDesc=OriginStateFips&VarType=Char&VarDesc=OriginStateName&VarType=Char&VarDesc=OriginWac&VarType=Num&VarName=DEST_AIRPORT_ID&VarDesc=DestAirportID&VarType=Num&VarName=DEST_AIRPORT_SEQ_ID&VarDesc=DestAirportSeqID&VarType=Num&VarName=DEST_CITY_MARKET_ID&VarDesc=DestCityMarketID&VarType=Num&VarName=DEST&VarDesc=Dest&VarType=Char&VarDesc=DestCityName&VarType=Char&VarDesc=DestState&VarType=Char&VarDesc=DestStateFips&VarType=Char&VarDesc=DestStateName&VarType=Char&VarDesc=DestWac&VarType=Num&VarName=CRS_DEP_TIME&VarDesc=CRSDepTime&VarType=Char&VarName=DEP_TIME&VarDesc=DepTime&VarType=Char&VarName=DEP_DELAY&VarDesc=DepDelay&VarType=Num&VarDesc=DepDelayMinutes&VarType=Num&VarDesc=DepDel15&VarType=Num&VarDesc=DepartureDelayGroups&VarType=Num&VarDesc=DepTimeBlk&VarType=Char&VarName=TAXI_OUT&VarDesc=TaxiOut&VarType=Num&VarName=WHEELS_OFF&VarDesc=WheelsOff&VarType=Char&VarName=WHEELS_ON&VarDesc=WheelsOn&VarType=Char&VarName=TAXI_IN&VarDesc=TaxiIn&VarType=Num&VarName=CRS_ARR_TIME&VarDesc=CRSArrTime&VarType=Char&VarName=ARR_TIME&VarDesc=ArrTime&VarType=Char&VarName=ARR_DELAY&VarDesc=ArrDelay&VarType=Num&VarDesc=ArrDelayMinutes&VarType=Num&VarDesc=ArrDel15&VarType=Num&VarDesc=ArrivalDelayGroups&VarType=Num&VarDesc=ArrTimeBlk&VarType=Char&VarName=CANCELLED&VarDesc=Cancelled&VarType=Num&VarName=CANCELLATION_CODE&VarDesc=CancellationCode&VarType=Char&VarName=DIVERTED&VarDesc=Diverted&VarType=Num&VarDesc=CRSElapsedTime&VarType=Num&VarDesc=ActualElapsedTime&VarType=Num&VarDesc=AirTime&VarType=Num&VarDesc=Flights&VarType=Num&VarName=DISTANCE&VarDesc=Distance&VarType=Num&VarDesc=DistanceGroup&VarType=Num&VarDesc=CarrierDelay&VarType=Num&VarDesc=WeatherDelay&VarType=Num&VarDesc=NASDelay&VarType=Num&VarDesc=SecurityDelay&VarType=Num&VarDesc=LateAircraftDelay&VarType=Num&VarDesc=FirstDepTime&VarType=Char&VarDesc=TotalAddGTime&VarType=Num&VarDesc=LongestAddGTime&VarType=Num&VarDesc=DivAirportLandings&VarType=Num&VarDesc=DivReachedDest&VarType=Num&VarDesc=DivActualElapsedTime&VarType=Num&VarDesc=DivArrDelay&VarType=Num&VarDesc=DivDistance&VarType=Num&VarDesc=Div1Airport&VarType=Char&VarDesc=Div1AirportID&VarType=Num&VarDesc=Div1AirportSeqID&VarType=Num&VarDesc=Div1WheelsOn&VarType=Char&VarDesc=Div1TotalGTime&VarType=Num&VarDesc=Div1LongestGTime&VarType=Num&VarDesc=Div1WheelsOff&VarType=Char&VarDesc=Div1TailNum&VarType=Char&VarDesc=Div2Airport&VarType=Char&VarDesc=Div2AirportID&VarType=Num&VarDesc=Div2AirportSeqID&VarType=Num&VarDesc=Div2WheelsOn&VarType=Char&VarDesc=Div2TotalGTime&VarType=Num&VarDesc=Div2LongestGTime&VarType=Num&VarDesc=Div2WheelsOff&VarType=Char&VarDesc=Div2TailNum&VarType=Char&VarDesc=Div3Airport&VarType=Char&VarDesc=Div3AirportID&VarType=Num&VarDesc=Div3AirportSeqID&VarType=Num&VarDesc=Div3WheelsOn&VarType=Char&VarDesc=Div3TotalGTime&VarType=Num&VarDesc=Div3LongestGTime&VarType=Num&VarDesc=Div3WheelsOff&VarType=Char&VarDesc=Div3TailNum&VarType=Char&VarDesc=Div4Airport&VarType=Char&VarDesc=Div4AirportID&VarType=Num&VarDesc=Div4AirportSeqID&VarType=Num&VarDesc=Div4WheelsOn&VarType=Char&VarDesc=Div4TotalGTime&VarType=Num&VarDesc=Div4LongestGTime&VarType=Num&VarDesc=Div4WheelsOff&VarType=Char&VarDesc=Div4TailNum&VarType=Char&VarDesc=Div5Airport&VarType=Char&VarDesc=Div5AirportID&VarType=Num&VarDesc=Div5AirportSeqID&VarType=Num&VarDesc=Div5WheelsOn&VarType=Char&VarDesc=Div5TotalGTime&VarType=Num&VarDesc=Div5LongestGTime&VarType=Num&VarDesc=Div5WheelsOff&VarType=Char&VarDesc=Div5TailNum&VarType=Char"

curl -X POST --data "$PARAMS" https://www.transtats.bts.gov/DownLoad_Table.asp?Table_ID=236&Has_Group=3&Is_Zipped=0

#the $PARAMS has a hardcoded value for year and month  2016 and 1. 
the above curl request redirect to a url of a zip file which is fetch with a next call curl -o

in 02_ingest/download.sh  check how $YEAR and $MONTH are inserted int he $PARAMS variable 

download.sh  downloads 12 months of 2015


check ingest.sh  which calls download.sh and other steps finishing by uploading the csv into the bucket


### shortcuts, getting data the simple way

1. simplest way   copy data from lak  bucket
run ingest_from_crsbucket.sh   

#copies predownloaded files  2015, 2016 into my bucket valak/flights/raw
 $ ingest_from_crsbucket.sh  valak   

II. alternative  ingest from the original source and carry cleanup steps
1. run a bash script ingest.sh  # downloads 12 files from 2015 
2. study and run monthlyupdate/ingest_flights.py

on mac  install google-cloud-storage   on 2 installations

/usr/local/bin/pip install --upgrade google-cloud-storage
~/anaconda3/bin/pip install --upgrade google-cloud-storage

in GCP console  API & Services  -> credentials -> Create credentials
https://console.cloud.google.com/apis/credentials/serviceaccountkey


./ingest_flights.py --bucket valak  --year 2016 --month 01  # ok on gcp console

## on mac needs to create a service account credentials for valak project
GCP -> API& & Services  -> Credentials 

in monhtlyupdate created szi_acc_credentials.sh
$ source szi_acc_credentials.sh
$ ./chkGoogleCredentials.py

created a pycharm project from existing sources in wk/02_ingest
and ran  in a debug mode  monthlyupdate/ingest_flights.py
It is a very useful code to download from the web datasets 

==========================
=   ch3_sqlstudio
==========================

in wk/03_sqlstudio/create_instance.ch
change gce-zone to europe-west1-b

run to provision a sql instnace
cd 03_sqlstudio
./create_instance.ch

go to google console mysql to set root password on flights instance 

./authorize_cloudshell.sh  # to allow to connect to sql from gcp console
  it calls 
$ gcloud sql instances patch flights \
    --authorized-networks `wget -qO - http://ipecho.net/plain`/32

./create_table.sh  #it puts the mysql ip into $MYSQLIP and calls mysql < create_table.sql which contains bts db create + flights table def

./populate_table.sh valak # to put 3 months of csv data into the bts.flights table

NB! if you have 401 serivice exception run source valak/util/szi_acc_credentials.sh


./contingency.sh   # to print a contingency 2x2 table on arr_delay and dep_delay

#creating dashboard
go to datastudio.google.com
->datasources -> select cloud sql
type: basic 
in instance name select instance connection name from UI instance details of your sql instance
valak01227408:europe-west2:fflights

select table flighs, click on create report 

after the first page is created in datastudio
./create_views.sh
It creates 3 views which will be connected as datasources to data studio report
delayed_10
delayed_15
delayed_20

in datstudio create separate datasources for each view
in Valak Flights report Settings -> Select Data Source
  es
  add these 3 datasources


==========================
=   ch4_streaming
==========================

map of global timezones
https://commons.wikimedia.org/wiki/File:Standard_Time_Zones_of_the_World_(October_2015).svg

Cloud DataFlow is built on 
FLume pub page
https://ai.google/research/pubs/pub35650

MillWheel pub page
https://ai.google/research/pubs/pub41378

batch job code is writtent in java or python in 
https://beam.apache.org
can be executed on multiple execution envs  Apache Flin (udemy,pluralsight) Apache Spark (pluralsight)

on gcp dataflow is a serverless execution environment,
executing beam pipelines

## airports data download manually
https://www.transtats.bts.gov/DL_SelectFields.asp?Table_ID=288







$ cd 04_streamain
$ ./ingest_from_crsbucket.sh BUCKET_NAME
## copies files in tzcorr and  airports 

$ cd 04_streaming/simulate
$ ./install_packages.sh  ## on osx  ./install_packages_osx.sh  into /usr/local  installation of python OK, OK

In original dataset FL_DATE is YY-MM-DD DEP_TIME is HHMM  !NB but no info on timezone offset
time zone offsets can be determined based on ORIGIN_AIRPORT_ID  and DEST_AIRPORT_ID
 
original url for airport data bts
https://www.transtats.bts.gov/DL_SelectFields.asp?Table_ID=288

from  https://github.com/GoogleCloudPlatform/data-science-on-gcp/blob/master/04_streaming/simulate/airports.csv.gz
download airports.csv.gz and copy it to 04/streaming/simulate

./df01.py   # extracts airport id, latitude and longitude fields and saves it to 
!NB df01.py is runnable on osx pycharm  if project python interpreter is set to 2.7

./df02.py   #adds timezone to airports (execution takes some time, tail -f on file inside beam-temp-airports.. )
#creates airports_with_tz

 ./df03.py  # joins 1K sample of flights data 201501_part.csv   with airports.csv.gz to convert dates to csv
 head -3 all_flights-00000*

./df04.py  # same as df03.py but also correct a 24 hour potential offset 
$ cat all_flights-00000-of-00001

./df05.py  # add processing of simulated events 

## finally to run the dates conversion job in dataFlow on gcp

# bq mk flights #create a flights dataset in BigQuery

#check setup.py file  (contains python packages that need to be deployed to dataflow nodes)

do
sudo pip install google-cloud-dataflow
#to start the job on dataflow
./df06.py -p valak01227408 -b valak -d flights   
#make sure that dataflow api is enabled for the project_id valak01227408  on google apis gui page

CODE: df06.py  apache beam in python how to save stream in Big Query

run the bquery

 		SELECT
 		  ORIGIN,
 		  DEP_TIME,
 		  DEP_DELAY,
 		  DEST,
 		  ARR_TIME,
 		  ARR_DELAY,
 		  NOTIFY_TIME
 		FROM
 		  flights.simevents
 		WHERE
 		  (DEP_DELAY > 15 and ORIGIN = 'SEA') or
 		  (ARR_DELAY > 15 and DEST = 'SEA')
 		ORDER BY NOTIFY_TIME ASC
 		LIMIT
 		  100


#####  simulating streaming ########


$ gcloud auth application-default login    #this asks to go to a url in the browser and stores a tmp applicatgion_default_credentials.json 
##  To generate an access token for other uses, run: gcloud auth application-default print-access-token
env

CODE: simpulate.py  python program  how to read a table from BigQuery and send events to pubSub

#to start simulation of publishing events: 
 $ python ./simulate.py --startTime '2015-05-01 00:00:00 UTC' --endTime '2015-05-04 00:00:00 UTC' --speedFactor=30 --project $DEVSHELL_PROJECT_ID

#in another shell start cloud data flow job which consumes events from pub/sub  and  writes them in BigQuery
cd ../realtime
./run_cloud.shn valak
goto GUI dataflow to monitor the job

to cancel the job using the 'gcloud' tool, run:
> gcloud dataflow jobs --project=valak01227408 cancel --region=us-central1 2018-12-04_04_47_09-11589368022683513799

CODE: NB: <~> java 8 notationj ? 
CODE: do some basic examples of apache beam sdk doc
CODE: book amzn: Streaming Systems :: streaming data in RealTime


## bq query for agg stats of an airport
select
* 
from `flights.streaming_delays`
where airport = 'SEA'
order by timestamp desc

##  save this query as a view  
 	SELECT
 	  airport,
 	  last[safe_OFFSET(0)].*,
 	  CONCAT(CAST(last[safe_OFFSET(0)].latitude AS STRING), ",", CAST(last[safe_OFFSET(0)].longitude AS STRING)) AS location
 	FROM (
 	  SELECT
 	    airport,
 	    ARRAY_AGG(STRUCT(arr_delay,
 	        dep_delay,
 	        timestamp,
 	        latitude,
 	        longitude,
 	        num_flights)
 	    ORDER BY
 	      timestamp DESC
 	    LIMIT
 	      1) last
 	  FROM
 	    `flights.streaming_delays`
 	  GROUP BY
 	    airport )

## create a view on cmd using the query below in 1 line
$ bq mk --use_legacy_sql=false --expiration 3600 --description "This is m
y view" --label organization:development --view "SELECT airport, last[safe_OFFSET(0)].*, CONCAT(CAST(last[safe_OFFSET(0)].latitude AS STRING), '
,', CAST(last[safe_OFFSET(0)].longitude AS STRING)) AS location FROM (SELECT airport, ARRAY_AGG(STRUCT(arr_delay, dep_delay, timestamp, latitude
, longitude, num_flights) ORDER BY timestamp DESC LIMIT 1) last FROM \`valak01227408:flights.streaming_delays\` GROUP BY airport )" --project_id
  valak01227408 valak01227408:flights.vdelay
  
#next you create a datastudio datasource based on vdelay view

==========================
=   ch5_bqdatalab
==========================
. set crededentials from proj/utils folder
## this step copies data and creates tzcorr table in flights dataset
./load_into_bq.sh valak    

!NB valack bucket shoudl exist from previous chapters 


#test it with the query below 

NB! tzcorr is a table with corrected utc timestamps on departures and arrivales 
# this query shows average depart delay, arrival delay  with number of flights
# for each of 143 airports with a minimum threshold of 3650 flights
SELECT
   *
 FROM (
   SELECT
     ORIGIN,
     AVG(DEP_DELAY) AS dep_delay,
     AVG(ARR_DELAY) AS arr_delay,
     COUNT(ARR_DELAY) AS num_flights
   FROM
     flights.tzcorr
   GROUP BY
     ORIGIN )
 WHERE
   num_flights > 3650
 ORDER BY
   dep_delay DESC
 
 
##save the query below in the table trainday
or run the script 
mk_tbl_trainday.sh

   #standardsql
 SELECT
   FL_DATE,
   IF(MOD(ABS(FARM_FINGERPRINT(CAST(FL_DATE AS STRING))), 100) < 70, 'True', 'False') AS is_train_day
 FROM (
   SELECT
     DISTINCT(FL_DATE) AS FL_DATE
   FROM
     `flights.tzcorr`)
 ORDER BY
   FL_DATE
   
In CloudShell, create a Datalab instance (change the zone to match where zone of valak):
datalab create --zone us-west2-a dsongcp  #
#this creates a vm instance (takes a few minutes)

#Once you get the message that the instance is reachable on localhost:8081, navigate
to the web page using the Web Preview button on the top-right of Cloud Shell.

on the list of vms page, connect by ssh to datalb vm 
git clone the proj repo
~/proj/valak/wk/05_bqdatalab/notebooks $ sudo cp *.ipynb /mnt/disks/datalab-pd/content/datalab/notebooks/
go through the notebooks cells 

in the middle of notebok,  run quries in bq UI  from file queries_in_bq_ui.sql

Once you have worked through all cells, 
copy the notebook back to  repo location and do the git commit 
cp  /mnt/disks/datalab-pd/content/datalab/notebooks/exploration_szi.ipynb  ~/
proj/valak/wk/05_bqdatalab/notebooks/

now delete the datalab instance
when you delete the datalab vm insance, also  check that you delete its disk

if yo do it with a script rm_debian_vm.sh   the disk will be deleted. 

==========================
=   ch6_dataproc
==========================

    cp from ref create_cluster.sh and run it to create a cluster with 2 workers
    the 3 machines are all created with 500 GB disks. 

 create_cluster.sh valak us-west2-a     #takes up to 5 mins

 on your local machine OSX
 change the account to zlava@gmail.com
 change the project to valak01227408  
 1. create a tunnel   
 > local_create_ssh_tunnel.sh  #make sure the zone matches
 2. run a chrome session to the localhost:1080
> local_run_chrome_tunnel_1080.sh 
this opens a new chrome browser.  goto  http://ch6cluster-m:8080   to open a datalab UI
on cluster master vm 
create a new notebooke quantization_szi.ipynb
rework cells of the quantization.ipynb ref 

increase the size of the cluster
increase_cluster.sh

run the notebook quantization_fulldata_szi.ipybnb
( line changed to read all csv fields
inputs = 'gs://{}/flights/tzcorr/all_flights-*'.format(BUCKET)
)
=Bayes Classification using Pig 
modify bayes.pig   to correct the bucket to brd_valak
run the pig job 
gcloud dataproc jobs submit pig --cluster ch6cluster --file bayes.pig
(job on 1 file took 1 min)
to see the output 
gsutil ls gs://brd_valak/flights/pigoutput
gsutil cat gs://brd_valak/flights/pigoutput/part-*

this file shows pairs, delays+ distance 

#to run the final pig job
delete the pigout folder
gsutil -m rm -r  gs://brd_valak/flights/pigoutput/
replace the bucket name to yours 
sed 's/cloud-training-demos-ml/brd_valak/g' bayes_final.pig > bayes_final_szi.pig

gcloud dataproc jobs submit pig --cluster ch6cluster --file bayes_final_szi.pig
gsutil cat gs://brd_valak/flights/pigoutput/part-*

22:15 - 22:17 the  job took 2 mins on cluster with 4 workers + 3 preemptive workers

this file shows pairs on decision boundry

368,15
575,17
838,18
1218,18
9999,19

delete the cluster 
./delete_cluster.sh 

(unfortunately the existing ipynb files quantization_szi* must be reviewed
to make them run )
## model quality verification using the results from pig 

#run in BQ ui 
##sum of all cancels
SELECT
  SUM(IF(DEP_DELAY >= 15 AND DISTANCE < 368, 1, 0)) +
  SUM(IF(DEP_DELAY >= 17 AND DISTANCE >= 368 AND DISTANCE < 575, 1, 0)) +
  SUM(IF(DEP_DELAY >= 18 AND DISTANCE >= 575 AND DISTANCE < 838, 1, 0)) +
  SUM(IF(DEP_DELAY >= 18 AND DISTANCE >= 838 AND DISTANCE < 1218, 1, 0)) +
  SUM(IF(DEP_DELAY >= 19 AND DISTANCE >= 1218, 1, 0))
  AS cancel
FROM (
  SELECT
    DEP_DELAY,
    ARR_DELAY,
    DISTANCE
  FROM
    flights.tzcorr f
  JOIN
    flights.trainday t
  ON
    f.FL_DATE = t.FL_DATE
  WHERE
    t.is_train_day = 'False'





