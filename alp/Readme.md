# Report

To perform all steps in the basic solution on a unix system
goto the project's **bin** folder  and execute **runAll.sh** script . 

Open the script, check the expected prerequisites and see  the sequence of steps.
The script is re-runnable. It outputs a metric of average number of unique users per 1 min period computed in the java code periodically based on a number of processed events in the stream. 

#### Basic Solution
Within a simple kafka consumer written in Java two fields `uid` and `ts` are extracted from json events. A case if a json does not have any of this fields is covered in the code. 

To compute a unique number of uids per  a time unit the approach is to use a **dictionary** data structure with keys storing timestamps of distinct time periods buckets, (distinct minutes in the basic case) and values containing lists of unique user ids.  A new dictionary key is inserted if the current event has a time stamp from a period never  seen by the dictionary. Overwise we need to conditionally insert a current event user id to the list of ids if it is not already in that list  for an existing key. 

A metric of average unique users per period is simply  a total sum of lengths of lists in dictionary values divided by a number of  dictionary elements.

Such data structure and logic do not require a strict timestamp order in the incoming stream of events. 

The metric counting logic in the  ConsumerMetricCounter.updateMetrics() method.

##### Finding appropriate time buckets for event time stamps
The  incoming stream of events contains a timestamp fields in a standard unix epoch format which is a number counted in seconds.
For  1 min period buckets a straight-forward solution would be to convert the epoch time seconds into java Datetime objects and create bucket Datetime objects from year,month,day,hour,minute fields, effectively stripping away seconds.

An approach with a shorter code is to use an integer division in java. 
So for 1 minute period an epoch time in seconds stored as Long type should be divided by 60 and multiplied by 60 producing the same effect of stripping seconds. 


#### Performance consideration
The Java's **HashMap** data structure is selected to be a metric storage structure as it provides **O(1)** time complexity for keys search, insertion and deletion operations. 

The Java's **HashSet** class is used to store values of unique users
in the dictionary. As a set only allows to contain unique elements, we don't need to implement a check of uniqueness of user ids in our counting metric logic. A set also has **O(1)** time complexity for element insertion operations.

#### Birds view on the streamed data. 
To get a bigger picture on the processed data and to have a comparison base on computed metric results, I wrote a second java consumer which sinks the uid and tiestamps into a csv file. 

The csv file is then loaded and processed for the same metric calculations and some visualizations in jupyter notebook in python and can be found in the **nb/explore_users.md** project's subdirectory.  The csv consumer program can be ran with **bin/runJavaCsvSinkApp.sh**. If necessary run the bin/group_topic_reset_offset.sh script. 

Both in the Java program output and in the jupyter notebook we observe comparable averages. 


#### Business considerations on the metrics
While a count of unique users per time period reflects a potential commercial value of a data stream, in my view  we should also store a total count of all users per time period.  The latter metric will reveal an additional information relevant for an appropriate technical infrastructure provisioning to support system's load. 

#### Further related kafka performance improvements
- run the kafka producer, with message compression enabled, batch size increased,
- implement  event payload filtering accepted by the business on a producer side, before sending an event to kafka 
- if stream network traffick is high, run several producer processes on separate hardware. This improves the high availability of the stream processing.
- for mass volume metrics with simple operations likes average, if minor data loss is accepted, run producer with acks=0
- implement a consumer with threads and launch several consumer threads,  or launch several consumer processes on different hosts. The latter improves the high availability of the stream processing. 









