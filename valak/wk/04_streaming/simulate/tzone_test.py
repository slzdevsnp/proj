#!/usr/bin/env python2


def addtimezone(lat, lon):
   try:
      import timezonefinder
      tf = timezonefinder.TimezoneFinder()
      tz = tf.timezone_at(lng=float(lon), lat=float(lat))
      if tz is None:
         tz = 'UTC'
      return (lat, lon, tz)
   except ValueError:
      return (lat, lon, 'TIMEZONE') # header


if __name__ == '__main__':
    tz1 = addtimezone(58.10944444,-152.90666667)
    tz2 = addtimezone(65.54805556,-161.07166667)
    tz3 = addtimezone(68.08333333,-163.16666667)

    print tz1
    print tz2
    print tz3

