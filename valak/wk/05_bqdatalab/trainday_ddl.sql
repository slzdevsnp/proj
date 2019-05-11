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