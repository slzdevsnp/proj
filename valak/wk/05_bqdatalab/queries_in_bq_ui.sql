
--- average arrival delays for each departure delays
SELECT
  DEP_DELAY,
  AVG(ARR_DELAY) AS arrival_delay,
  count(1) as n_occur
FROM
  flights.tzcorr
WHERE DEP_DELAY IS NOT NULL
GROUP BY
  DEP_DELAY
ORDER BY
  DEP_DELAY

--- same query but with n_occur > 370
  SELECT
  *
FROM (
  SELECT
    DEP_DELAY,
    AVG(ARR_DELAY) AS arrival_delay,
    STDDEV(ARR_DELAY) AS stddev_arrival_delay,
    COUNT(ARR_DELAY) AS numflights
  FROM
    `flights.tzcorr`
  GROUP BY
    DEP_DELAY )
WHERE
  numflights > 370
ORDER BY
  DEP_DELAY

  ---in bigdata removing outliers = mu +- 3std
  --- uery to check outliers for DEP_DELAY
SELECT
  AVG(DEP_DELAY) - 3*STDDEV(DEP_DELAY) AS filtermin,
  AVG(DEP_DELAY) + 3*STDDEV(DEP_DELAY) AS filtermax
FROM
  `flights.tzcorr`

--same using the quantile
SELECT
  APPROX_QUANTILES(DEP_DELAY, 20)  -- increments of 5% quantiles
FROM
  `flights.tzcorr`
-- we retain values between [-9, 66]



--compute 30% likelihod for each depature delay
SELECT
  DEP_DELAY,
  arrival_delay,
  numflights
FROM (
  SELECT
    DEP_DELAY,
    APPROX_QUANTILES(ARR_DELAY, 101)[OFFSET(70)] AS arrival_delay,
    COUNT(ARR_DELAY) AS numflights
  FROM
    `flights.tzcorr`
  GROUP BY
    DEP_DELAY )
WHERE
  numflights > 370
ORDER BY
  DEP_DELAY
